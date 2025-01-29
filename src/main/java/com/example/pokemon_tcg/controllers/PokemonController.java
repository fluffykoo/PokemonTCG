package com.example.pokemon_tcg.controllers;

import com.example.pokemon_tcg.constants.TypePokemon;
import com.example.pokemon_tcg.models.Pokemon;
import com.example.pokemon_tcg.repositories.DresseurRepository;
import com.example.pokemon_tcg.services.IPokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pokemons")
public class PokemonController {

    private final IPokemonService pokemonService;
    private final DresseurRepository dresseurRepository;

    public PokemonController(IPokemonService pokemonService, DresseurRepository dresseurRepository) {
        this.pokemonService = pokemonService;
        this.dresseurRepository = dresseurRepository;
    }

    @GetMapping
    public ResponseEntity<List<Pokemon>> getAll(@RequestParam(required = false) TypePokemon type) {
        return new ResponseEntity<>(pokemonService.getAllPokemon(type), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Pokemon> createPokemon(@RequestBody Pokemon pokemon) {
        Pokemon createdPokemon = pokemonService.createPokemon(pokemon);
        return new ResponseEntity<>(createdPokemon, HttpStatus.CREATED);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletePokemon(@PathVariable String uuid) {
        boolean isDeleted = pokemonService.deletePokemon(uuid);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Pokemon> getById(@PathVariable String uuid) {
        try {
            Pokemon pokemon = pokemonService.getPokemonById(uuid);
            return new ResponseEntity<>(pokemon, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}