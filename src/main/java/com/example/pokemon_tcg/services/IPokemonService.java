package com.example.pokemon_tcg.services;
import com.example.pokemon_tcg.models.Pokemon;

import java.util.List;

    public interface IPokemonService {

        List<Pokemon> getAllPokemon();

        Pokemon getPokemonById(String uuid);

        List<Pokemon> getPokemonByType(String type);

        Pokemon createPokemon(Pokemon pokemon);

        void deletePokemon(String uuid);
    }