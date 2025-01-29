package com.example.pokemon_tcg.repositories;

import com.example.pokemon_tcg.constants.TypePokemon;
import com.example.pokemon_tcg.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, String> {

    List<Pokemon> findAllByType(TypePokemon type);
}