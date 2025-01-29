package com.example.pokemon_tcg.repositories;

import com.example.pokemon_tcg.constants.TypePokemon;
import com.example.pokemon_tcg.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, String> {
    List<Pokemon> findAllByType(TypePokemon type);

}