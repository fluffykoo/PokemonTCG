package com.example.pokemon_tcg.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Dresseur {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String nom;
    private String prenom;
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "dresseur")
    @JsonManagedReference
    private List<Carte> carteList;

    // Getters and setters

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public List<Carte> getCarteList() {
        return carteList;
    }

    public void setCarteList(List<Carte> carteList) {
        this.carteList = carteList;
    }

    public void ajouterPokemon(Pokemon pokemon) {
        Carte carte = new Carte();
        carte.setPokemon(pokemon);
        carte.setDresseur(this);
        this.carteList.add(carte);
    }
}