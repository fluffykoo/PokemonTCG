package com.example.pokemon_tcg.dto;


public class BattleRequestDTO {
    private String dresseur1;
    private String dresseur2;
    private String carte1;
    private String carte2;

    // Getters et Setters
    public String getDresseur1() { return dresseur1; }
    public void setDresseur1(String dresseur1) { this.dresseur1 = dresseur1; }

    public String getDresseur2() { return dresseur2; }
    public void setDresseur2(String dresseur2) { this.dresseur2 = dresseur2; }

    public String getCarte1() { return carte1; }
    public void setCarte1(String carte1) { this.carte1 = carte1; }

    public String getCarte2() { return carte2; }
    public void setCarte2(String carte2) { this.carte2 = carte2; }
}