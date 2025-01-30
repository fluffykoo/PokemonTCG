package com.example.pokemon_tcg.dto;

import java.util.List;

public class DresseurDTO {
    private String uuid;
    private String nom;
    private String prenom;
    private List<String> pokemonList;

    public DresseurDTO(String uuid, String nom, String prenom, List<String> pokemonList) {
        this.uuid = uuid;
        this.nom = nom;
        this.prenom = prenom;
        this.pokemonList = pokemonList;
    }

    public String getUuid() {
        return uuid;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public List<String> getPokemonList() {
        return pokemonList;
    }

}