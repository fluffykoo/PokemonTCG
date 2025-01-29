package com.example.pokemon_tcg.constants;

public enum TypePokemon {
    FEU,
    EAU,
    PLANTE,
    ELECTRIQUE,
    VOL,
    NORMAL;

    public double getEfficacite(TypePokemon againstTypePokemon) {
        switch (this) {
            case FEU:
                if (againstTypePokemon == PLANTE) return 2.0;
                if (againstTypePokemon == EAU) return 0.5;
                break;
            case EAU:
                if (againstTypePokemon == FEU) return 2.0;
                if (againstTypePokemon == PLANTE) return 0.5;
                break;
            case PLANTE:
                if (againstTypePokemon == EAU) return 2.0;
                if (againstTypePokemon == FEU) return 0.5;
                break;
            case ELECTRIQUE:
                if (againstTypePokemon == EAU) return 2.0;
                if (againstTypePokemon == PLANTE) return 0.5;
                if (againstTypePokemon == VOL) return 2.0;
                break;
            case VOL:
                if (againstTypePokemon == ELECTRIQUE) return 0.5;
                if (againstTypePokemon == PLANTE) return 2.0;
                break;
            default:
                return 1.0;
        }
        return 1.0;
    }
}