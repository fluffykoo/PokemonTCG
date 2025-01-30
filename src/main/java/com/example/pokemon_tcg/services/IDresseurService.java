package com.example.pokemon_tcg.services;

import com.example.pokemon_tcg.models.Dresseur;

import java.util.List;

public interface IDresseurService {
    List<Dresseur> getAllDresseurs();

    Dresseur getDresseurById(String uuid);

    Dresseur createDresseur(Dresseur dresseur);

    boolean deleteDresseur(String uuid);

    Dresseur updateDresseur(String uuid, Dresseur dresseur);

    Dresseur getDresseurByUsername(String username);

    boolean authenticateDresseur(String username, String password);
}