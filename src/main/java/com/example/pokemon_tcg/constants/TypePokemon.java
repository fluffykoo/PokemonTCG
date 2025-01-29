package com.example.pokemon_tcg.constants;

public enum TypePokemon {
    FEU,
    EAU,
    PLANTE,
    ELECTRIQUE,
    VOL,
    NORMAL,
    PSY,
    SOL,
    ROCHE,
    ACIER,
    SPECTRE,
    TENEBRES,
    COMBAT,
    POISON,
    GLACE,
    DRAGON;


    public double getEfficacite(TypePokemon againstTypePokemon) {
        switch (this) {
            case FEU:
                if (againstTypePokemon == PLANTE || againstTypePokemon == ACIER) return 2.0;
                if (againstTypePokemon == EAU || againstTypePokemon == ROCHE) return 0.5;
                break;
            case EAU:
                if (againstTypePokemon == FEU || againstTypePokemon == ROCHE) return 2.0;
                if (againstTypePokemon == PLANTE || againstTypePokemon == ELECTRIQUE) return 0.5;
                break;
            case PLANTE:
                if (againstTypePokemon == EAU || againstTypePokemon == SOL) return 2.0;
                if (againstTypePokemon == FEU || againstTypePokemon == VOL || againstTypePokemon == PLANTE) return 0.5;
                break;
            case ELECTRIQUE:
                if (againstTypePokemon == EAU || againstTypePokemon == VOL) return 2.0;
                if (againstTypePokemon == PLANTE || againstTypePokemon == ELECTRIQUE) return 0.5;
                if (againstTypePokemon == SOL) return 0.0;
                break;
            case VOL:
                if (againstTypePokemon == PLANTE) return 2.0;
                if (againstTypePokemon == ELECTRIQUE || againstTypePokemon == ROCHE) return 0.5;
                break;
            case NORMAL:
                if (againstTypePokemon == SPECTRE) return 0.0;
                if (againstTypePokemon == ROCHE) return 0.5;
                break;
            case PSY:
                if (againstTypePokemon == COMBAT || againstTypePokemon == POISON) return 2.0;
                if (againstTypePokemon == TENEBRES) return 0.0;
                if (againstTypePokemon == ACIER || againstTypePokemon == PSY) return 0.5;
                break;
            case SOL:
                if (againstTypePokemon == ELECTRIQUE || againstTypePokemon == ACIER || againstTypePokemon == ROCHE || againstTypePokemon == FEU) return 2.0;
            if (againstTypePokemon == VOL) return 0.0;
            if (againstTypePokemon == PLANTE) return 0.5;
            break;
            case ROCHE:
                if (againstTypePokemon == FEU || againstTypePokemon == VOL) return 2.0;
                if (againstTypePokemon == SOL || againstTypePokemon == ACIER) return 0.5;
                break;
            case ACIER:
                if (againstTypePokemon == GLACE || againstTypePokemon == ROCHE) return 2.0;
                if (againstTypePokemon == EAU || againstTypePokemon == FEU || againstTypePokemon == ELECTRIQUE) return 0.5;
                break;
            case SPECTRE:
                if (againstTypePokemon == SPECTRE || againstTypePokemon == PSY) return 2.0;
                if (againstTypePokemon == TENEBRES) return 0.5;
                if (againstTypePokemon == NORMAL) return 0.0;
                break;
            case TENEBRES:
                if (againstTypePokemon == SPECTRE || againstTypePokemon == PSY) return 2.0;
                if (againstTypePokemon == COMBAT) return 0.5;
                break;
            case DRAGON:
                if (againstTypePokemon == DRAGON) return 2.0;
                if (againstTypePokemon == ACIER) return 0.5;
                break;
            default:
                return 1.0;
        }
        return 1.0;
    }
}