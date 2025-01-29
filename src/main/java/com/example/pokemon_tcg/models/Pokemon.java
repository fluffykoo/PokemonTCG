package com.example.pokemon_tcg.models;

import com.example.pokemon_tcg.constants.TypePokemon;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String nom;

    private Integer niveau;

    private Integer pv; // Points de vie du Pokémon

    @Enumerated(EnumType.STRING)
    private TypePokemon type;

    private String faiblesse; // Faiblesse du Pokémon

    private String attaque1;

    private String attaque2;

    @ManyToOne
    private Dresseur dresseur;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getNiveau() {
        return niveau;
    }

    public void setNiveau(Integer niveau) {
        this.niveau = niveau;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public TypePokemon getType() {
        return type;
    }

    public void setType(TypePokemon type) {
        this.type = type;
    }

    public String getFaiblesse() {
        return faiblesse;
    }

    public void setFaiblesse(String faiblesse) {
        this.faiblesse = faiblesse;
    }

    public String getAttaque1() {
        return attaque1;
    }

    public void setAttaque1(String attaque1) {
        this.attaque1 = attaque1;
    }

    public String getAttaque2() {
        return attaque2;
    }

    public void setAttaque2(String attaque2) {
        this.attaque2 = attaque2;
    }

    public String getUuid() {
        return uuid;
    }

    public Dresseur getDresseur() {
        return dresseur;
    }

    public void setDresseur(Dresseur dresseur) {
        this.dresseur = dresseur;
    }
}