package com.example.pokemon_tcg.services;

import com.example.pokemon_tcg.models.Carte;
import com.example.pokemon_tcg.models.Dresseur;
import com.example.pokemon_tcg.models.Pokemon;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.Scanner;

@Service
public class ICombatService {

    private final Random random = new Random();

    public void lancerCombatTerminal(Dresseur dresseur1, Dresseur dresseur2) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("🔥 Combat entre " + dresseur1.getPrenom() + " et " + dresseur2.getPrenom() + " !");

        afficherDeck(dresseur1);
        afficherDeck(dresseur2);

        Carte carte1 = choisirCarte(scanner, dresseur1);
        Carte carte2 = choisirCarte(scanner, dresseur2);

        String attaque1 = choisirAttaque(scanner, carte1);
        String attaque2 = choisirAttaque(scanner, carte2);

        System.out.println("\n⚔️ Début du combat !\n");
        String resultat = battle(carte1.getPokemon(), carte2.getPokemon(), dresseur1.getPrenom(), dresseur2.getPrenom(), attaque1, attaque2);
        System.out.println(resultat);
    }

    private void afficherDeck(Dresseur dresseur) {
        System.out.println("\n🔹 Deck de " + dresseur.getPrenom() + " :");
        int i = 1;
        for (Carte carte : dresseur.getCarteList()) {
            System.out.println(i + ". " + carte.getPokemon().getNom() + " (Niveau: " + carte.getPokemon().getNiveau() + ")");
            i++;
        }
    }

    private Carte choisirCarte(Scanner scanner, Dresseur dresseur) {
        System.out.print("\n🎴 " + dresseur.getPrenom() + ", choisis une carte (1-" + dresseur.getCarteList().size() + "): ");
        int choix = scanner.nextInt() - 1;
        return dresseur.getCarteList().get(choix);
    }

    private String choisirAttaque(Scanner scanner, Carte carte) {
        System.out.println("\n💥 Attaques de " + carte.getPokemon().getNom() + " :");
        System.out.println("1. " + carte.getPokemon().getAttaque1());
        System.out.println("2. " + carte.getPokemon().getAttaque2());
        System.out.print("⚡ Choisis ton attaque (1-2): ");
        int choix = scanner.nextInt();
        return (choix == 1) ? carte.getPokemon().getAttaque1() : carte.getPokemon().getAttaque2();
    }

    private String battle(Pokemon pokemon1, Pokemon pokemon2, String dresseur1, String dresseur2, String attaque1, String attaque2) {
        int score1 = calculateScore(pokemon1);
        int score2 = calculateScore(pokemon2);

        int bonus1 = random.nextInt(16) + 5;
        int bonus2 = random.nextInt(16) + 5;

        score1 += bonus1;
        score2 += bonus2;

        System.out.println("\n⚔️ Combat entre " + dresseur1 + " et " + dresseur2 + " !");
        System.out.println(dresseur1 + " utilise " + attaque1 + " : Score = " + score1 + " (+ Bonus " + bonus1 + ")");
        System.out.println(dresseur2 + " utilise " + attaque2 + " : Score = " + score2 + " (+ Bonus " + bonus2 + ")");

        if (score1 > score2) {
            return "🏆 " + dresseur1 + " gagne avec " + pokemon1.getNom() + " en utilisant " + attaque1 + " ! (Score: " + score1 + " vs " + score2 + ")";
        } else if (score2 > score1) {
            return "🏆 " + dresseur2 + " gagne avec " + pokemon2.getNom() + " en utilisant " + attaque2 + " ! (Score: " + score1 + " vs " + score2 + ")";
        } else {
            return "⚖️ Match nul entre " + dresseur1 + " et " + dresseur2 + " ! (Score: " + score1 + " vs " + score2 + ")";
        }
    }

    private int calculateScore(Pokemon pokemon) {
        int rarete = (pokemon.getRarete() != null) ? pokemon.getRarete() : 1;
        return pokemon.getNiveau() + pokemon.getPv() + (rarete * 10);
    }
}