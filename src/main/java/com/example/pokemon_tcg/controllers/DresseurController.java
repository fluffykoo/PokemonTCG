package com.example.pokemon_tcg.controllers;

import com.example.pokemon_tcg.models.Dresseur;
import com.example.pokemon_tcg.models.Pokemon;
import com.example.pokemon_tcg.services.IDresseurService;
import com.example.pokemon_tcg.services.IPokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/dresseurs")
public class DresseurController {
    private final IDresseurService dresseurService;
    private final IPokemonService pokemonService;

    public DresseurController(IDresseurService dresseurService, IPokemonService pokemonService) {
        this.dresseurService = dresseurService;
        this.pokemonService = pokemonService;
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
    public ResponseEntity<Dresseur> claimPokemons(@PathVariable String uuid) {
        Dresseur dresseur = dresseurService.getDresseurById(uuid);
        if (dresseur.getPokemonList().size() >= 5) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Pokemon> availablePokemons = pokemonService.getAllPokemon();
        Random random = new Random();
        List<Pokemon> newPokemons = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Pokemon pokemon = availablePokemons.get(random.nextInt(availablePokemons.size()));
            pokemon.setNiveau(generateRandomNiveau(random));
            pokemon.setPv(generateRandomPv(random));
            newPokemons.add(pokemonService.createPokemon(pokemon));
        }
        dresseur.getPokemonList().addAll(newPokemons);
        Dresseur updatedDresseur = dresseurService.updateDresseur(uuid, dresseur);
        return new ResponseEntity<>(updatedDresseur, HttpStatus.OK);
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
}