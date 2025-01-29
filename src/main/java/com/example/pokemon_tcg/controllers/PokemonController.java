package com.example.pokemon_tcg.controllers;

import com.example.pokemon_tcg.models.Pokemon;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pokemons")
public class PokemonController {
    private final IPokemonService pokemonService;
    private final DresseurRepository dresseurRepository;

    public PokemonController(PokemonServiceImpl pokemonService, DresseurRepository dresseurRepository) {
        this.pokemonService = pokemonService;
        this.dresseurRepository = dresseurRepository;
    }

    @GetMapping
    public ResponseEntity<List<Pokemon>> getAll(@RequestParam(required = false) TypePokemon type) {
        return new ResponseEntity<>(pokemonService.findAll(type), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createPokemon(@Valid @RequestBody CreatePokemon pokemon) {
        pokemonService.create(pokemon);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deletePokemon(@PathVariable String uuid) {
        boolean isSupprimer = pokemonService.delete(uuid);
        if (!isSupprimer) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Pokemon> getById(@PathVariable String uuid) {
        Pokemon pokemon = pokemonService.findByID(uuid);
        if (pokemon == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pokemonService.findByID(uuid), HttpStatus.OK);
    }

}