package com.example.pokemon_tcg.controllers;

import com.example.pokemon_tcg.dto.BattleRequestDTO;
import com.example.pokemon_tcg.models.Carte;
import com.example.pokemon_tcg.models.Dresseur;
import com.example.pokemon_tcg.models.Pokemon;
import com.example.pokemon_tcg.services.ICarteService;
import com.example.pokemon_tcg.services.IDresseurService;
import com.example.pokemon_tcg.services.IPokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.pokemon_tcg.services.ICombatService;

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
    private final ICombatService combatService;

    public DresseurController(IDresseurService dresseurService, IPokemonService pokemonService, ICarteService carteService, ICombatService combatService) {
        this.dresseurService = dresseurService;
        this.pokemonService = pokemonService;
        this.carteService = carteService;
        this.combatService = combatService;
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
    public ResponseEntity<String> simulateBattle(@RequestBody BattleRequestDTO battleRequest) {
        System.out.println("🔥 Combat lancé entre Dresseurs : " + battleRequest);


        if (battleRequest.getDresseur1() == null || battleRequest.getDresseur2() == null ||
                battleRequest.getCarte1() == null || battleRequest.getCarte2() == null) {
            return new ResponseEntity<>("❌ Vous devez fournir les UUIDs des deux Dresseurs et des deux Cartes.", HttpStatus.BAD_REQUEST);
        }


        Dresseur dresseur1 = dresseurService.getDresseurById(battleRequest.getDresseur1());
        Dresseur dresseur2 = dresseurService.getDresseurById(battleRequest.getDresseur2());

        if (dresseur1 == null || dresseur2 == null) {
            return new ResponseEntity<>("❌ L'un des Dresseurs (ou les deux) est introuvable.", HttpStatus.NOT_FOUND);
        }

        
        Carte carte1 = carteService.getCarteById(battleRequest.getCarte1());
        Carte carte2 = carteService.getCarteById(battleRequest.getCarte2());

        if (carte1 == null || carte2 == null) {
            return new ResponseEntity<>("❌ L'une des Cartes (ou les deux) est introuvable.", HttpStatus.NOT_FOUND);
        }

        
        if (!carte1.getDresseur().getUuid().equals(dresseur1.getUuid()) ||
                !carte2.getDresseur().getUuid().equals(dresseur2.getUuid())) {
            return new ResponseEntity<>("❌ Une carte ne correspond pas à son Dresseur.", HttpStatus.BAD_REQUEST);
        }

        
        String resultat = battle(carte1.getPokemon(), carte2.getPokemon(), dresseur1.getPrenom(), dresseur2.getPrenom());
        return new ResponseEntity<>(resultat, HttpStatus.OK);
    }

    private String battle(Pokemon pokemon1, Pokemon pokemon2, String dresseur1, String dresseur2) {
        int score1 = calculateScore(pokemon1);
        int score2 = calculateScore(pokemon2);

        System.out.println("⚔️ Combat entre " + dresseur1 + " (" + pokemon1.getNom() + ") et " + dresseur2 + " (" + pokemon2.getNom() + ")");
        System.out.println(pokemon1.getNom() + " : Score = " + score1);
        System.out.println(pokemon2.getNom() + " : Score = " + score2);

        if (score1 > score2) {
            return "🏆 " + dresseur1 + " gagne avec " + pokemon1.getNom() + " ! (Score: " + score1 + " vs " + score2 + ")";
        } else if (score2 > score1) {
            return "🏆 " + dresseur2 + " gagne avec " + pokemon2.getNom() + " ! (Score: " + score1 + " vs " + score2 + ")";
        } else {
            return "⚖️ Match nul entre " + dresseur1 + " et " + dresseur2 + " ! (Score: " + score1 + " vs " + score2 + ")";
        }
    }

    private int calculateScore(Pokemon pokemon) {
        int rarete = (pokemon.getRarete() != null) ? pokemon.getRarete() : 1;
        return pokemon.getNiveau() + pokemon.getPv() + (rarete * 10);
    }

    @PostMapping("/battle-terminal")
    public ResponseEntity<String> simulateBattleInTerminal(@RequestBody BattleRequestDTO battleRequest) {
        System.out.println("🎮 Lancement du combat en mode terminal !");

        Dresseur dresseur1 = dresseurService.getDresseurById(battleRequest.getDresseur1());
        Dresseur dresseur2 = dresseurService.getDresseurById(battleRequest.getDresseur2());

        if (dresseur1 == null || dresseur2 == null) {
            return new ResponseEntity<>("❌ L'un des Dresseurs est introuvable.", HttpStatus.NOT_FOUND);
        }

        combatService.lancerCombatTerminal(dresseur1, dresseur2);

        return new ResponseEntity<>("✅ Combat effectué avec succès !", HttpStatus.OK);
    }
}