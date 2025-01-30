package com.example.pokemon_tcg.models;

import com.example.pokemon_tcg.constants.TypePokemon;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String nom;
    private Integer niveau;
    private Integer pv;

    @Enumerated(EnumType.STRING)
    private TypePokemon type;

    private String faiblesse;
    private String attaque1;
    private String attaque2;

    private LocalDateTime dateAjout;

    @ManyToOne
    @JoinColumn(name = "dresseur_uuid")
    @JsonIgnore
    private Dresseur dresseur;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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

    public LocalDateTime getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDateTime dateAjout) {
        this.dateAjout = dateAjout;
    }

    public Dresseur getDresseur() {
        return dresseur;
    }

    public void setDresseur(Dresseur dresseur) {
        this.dresseur = dresseur;
    }
}