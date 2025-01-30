package com.example.pokemon_tcg.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class HistoriqueEchange {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "dresseur_from_uuid")
    private Dresseur dresseurFrom;

    @ManyToOne
    @JoinColumn(name = "dresseur_to_uuid")
    private Dresseur dresseurTo;

    @ManyToOne
    @JoinColumn(name = "carte_uuid")
    private Carte carte;

    private LocalDateTime exchangeDate;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Dresseur getDresseurFrom() {
        return dresseurFrom;
    }

    public void setDresseurFrom(Dresseur dresseurFrom) {
        this.dresseurFrom = dresseurFrom;
    }

    public Dresseur getDresseurTo() {
        return dresseurTo;
    }

    public void setDresseurTo(Dresseur dresseurTo) {
        this.dresseurTo = dresseurTo;
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public LocalDateTime getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(LocalDateTime exchangeDate) {
        this.exchangeDate = exchangeDate;
    }
}