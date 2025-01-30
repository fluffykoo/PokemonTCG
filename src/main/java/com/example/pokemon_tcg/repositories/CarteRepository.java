package com.example.pokemon_tcg.repositories;

import com.example.pokemon_tcg.models.Carte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteRepository extends JpaRepository<Carte, String> {
    Carte findByPokemon_Uuid(String pokemonUuid); // Corrected method
}