package com.example.pokemon_tcg.dto;

public class EchangeDTO {
    private String dresseurFromId;
    private String dresseurToId;
    private String carteId;
    
    public String getDresseurFromId() {
        return dresseurFromId;
    }

    public void setDresseurFromId(String dresseurFromId) {
        this.dresseurFromId = dresseurFromId;
    }

    public String getDresseurToId() {
        return dresseurToId;
    }

    public void setDresseurToId(String dresseurToId) {
        this.dresseurToId = dresseurToId;
    }

    public String getCarteId() {
        return carteId;
    }

    public void setCarteId(String carteId) {
        this.carteId = carteId;
    }
}