package com.example.pokemon_tcg.services;

import com.example.pokemon_tcg.models.Carte;
import java.util.List;

public interface ICarteService {
    Carte createCarte(Carte carte);
    List<Carte> getAllCartes();
    Carte getCarteById(String uuid);
    Carte updateCarte(Carte carte);
    boolean deleteCarte(String uuid);
    Carte findByPokemonId(String pokemonId);
}