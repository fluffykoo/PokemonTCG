package com.example.pokemon_tcg.services;

import com.example.pokemon_tcg.constants.TypePokemon;

import com.example.pokemon_tcg.models.Pokemon;

import java.util.List;

public interface IPokemonService {

    List<Pokemon> getAllPokemon();

    List<Pokemon> getAllPokemon(TypePokemon type);

    Pokemon getPokemonById(String uuid);

    Pokemon createPokemon(Pokemon pokemon);

    boolean deletePokemon(String uuid);

}
