package com.example.pokemon_tcg.controllers;

import com.example.pokemon_tcg.models.Carte;
import com.example.pokemon_tcg.models.Dresseur;
import com.example.pokemon_tcg.models.Pokemon;
import com.example.pokemon_tcg.services.ICarteService;
import com.example.pokemon_tcg.services.IDresseurService;
import com.example.pokemon_tcg.services.IPokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/dresseurs")
public class DresseurController {
    private final IDresseurService dresseurService;
    private final IPokemonService pokemonService;
    private final ICarteService carteService;

    public DresseurController(IDresseurService dresseurService, IPokemonService pokemonService, ICarteService carteService) {
        this.dresseurService = dresseurService;
        this.pokemonService = pokemonService;
        this.carteService = carteService;
    }

    @GetMapping
    public ResponseEntity<List<Dresseur>> getAll() {
        return new ResponseEntity<>(dresseurService.getAllDresseurs(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Dresseur> createDresseur(@RequestBody Dresseur dresseur) {
        Dresseur createdDresseur = dresseurService.createDresseur(dresseur);
        return new ResponseEntity<>(createdDresseur, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Dresseur> getById(@PathVariable String uuid) {
        try {
            Dresseur dresseur = dresseurService.getDresseurById(uuid);
            return new ResponseEntity<>(dresseur, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteDresseur(@PathVariable String uuid) {
        boolean isDeleted = dresseurService.deleteDresseur(uuid);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{uuid}/claim")
    public ResponseEntity<Dresseur> claimCartes(@PathVariable String uuid) {
        Dresseur dresseur = dresseurService.getDresseurById(uuid);
        if (dresseur.getCarteList().size() >= 5) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Pokemon> availablePokemons = pokemonService.getAllPokemon();
        Random random = new Random();
        List<Carte> newCartes = new ArrayList<>();
        while (dresseur.getCarteList().size() + newCartes.size() < 5) {
            Pokemon pokemon = availablePokemons.get(random.nextInt(availablePokemons.size()));
            if (carteService.findByPokemonId(pokemon.getUuid()) != null) {
                continue;
            }
            pokemon.setNiveau(generateRandomNiveau(random));
            pokemon.setPv(generateRandomPv(random));
            pokemon.setRarete(generateRandomRarete(random));
            pokemonService.updatePokemon(pokemon);
            Carte carte = new Carte();
            carte.setPokemon(pokemon);
            carte.setDresseur(dresseur);
            carte.setDateAjout(LocalDateTime.now());
            newCartes.add(carteService.createCarte(carte));
        }
        dresseur.getCarteList().addAll(newCartes);
        Dresseur updatedDresseur = dresseurService.updateDresseur(uuid, dresseur);
        return new ResponseEntity<>(updatedDresseur, HttpStatus.OK);
    }

    private int generateRandomRarete(Random random) {
        int roll = random.nextInt(100) + 1;
        if (roll <= 50) return 1;
        if (roll <= 80) return 2;
        if (roll <= 95) return 3;
        if (roll <= 99) return 4;
        return 5;
    }

    private int generateRandomNiveau(Random random) {
        int niveau = random.nextInt(100) + 1;
        if (niveau > 30) {
            niveau = random.nextInt(30) + 1;
        }
        return niveau;
    }

    private int generateRandomPv(Random random) {
        int pv = random.nextInt(200 - 30 + 1) + 30;
        if (pv > 90) {
            pv = random.nextInt(60) + 30;
        }
        return pv;
    }
    @PostMapping("/battle")
    public ResponseEntity<String> simulateBattle(@RequestBody List<String> carteUuids) {
        System.out.println("🔥 Combat lancé avec Cartes UUIDs : " + carteUuids);

        if (carteUuids.size() != 2) {
            return new ResponseEntity<>("❌ Exactement deux Cartes UUIDs sont requises.", HttpStatus.BAD_REQUEST);
        }


        Carte carte1 = carteService.getCarteById(carteUuids.get(0));
        Carte carte2 = carteService.getCarteById(carteUuids.get(1));

        if (carte1 == null || carte2 == null) {
            return new ResponseEntity<>("❌ L'une des cartes (ou les deux) est introuvable.", HttpStatus.NOT_FOUND);
        }


        Pokemon pokemon1 = carte1.getPokemon();
        Pokemon pokemon2 = carte2.getPokemon();

        if (pokemon1 == null || pokemon2 == null) {
            return new ResponseEntity<>("❌ Une des cartes ne contient pas de Pokémon valide.", HttpStatus.BAD_REQUEST);
        }

        String result = battle(carte1, carte2);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    private String battle(Carte carte1, Carte carte2) {
        Pokemon pokemon1 = carte1.getPokemon();
        Pokemon pokemon2 = carte2.getPokemon();

        int score1 = calculateScore(pokemon1);
        int score2 = calculateScore(pokemon2);

        System.out.println("⚔️ Combat entre la carte de " + pokemon1.getNom() + " et celle de " + pokemon2.getNom());
        System.out.println(pokemon1.getNom() + " (Carte " + carte1.getUuid() + ") : Score = " + score1);
        System.out.println(pokemon2.getNom() + " (Carte " + carte2.getUuid() + ") : Score = " + score2);

        if (score1 > score2) {
            return "🏆 La carte de " + pokemon1.getNom() + " remporte la victoire ! (Score: " + score1 + " vs " + score2 + ")";
        } else if (score2 > score1) {
            return "🏆 La carte de " + pokemon2.getNom() + " remporte la victoire ! (Score: " + score1 + " vs " + score2 + ")";
        } else {
            return "⚖️ Match nul ! (Score: " + score1 + " vs " + score2 + ")";
        }
    }
    private int calculateScore(Pokemon pokemon) {
        int rarete = (pokemon.getRarete() != null) ? pokemon.getRarete() : 1; // Évite erreur si rareté = null
        return pokemon.getNiveau() + pokemon.getPv() + (rarete * 10);
    }
}

