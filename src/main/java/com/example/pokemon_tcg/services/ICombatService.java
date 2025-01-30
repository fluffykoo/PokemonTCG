package com.example.pokemon_tcg.services;

import com.example.pokemon_tcg.models.Carte;
import com.example.pokemon_tcg.models.Dresseur;
import com.example.pokemon_tcg.models.Pokemon;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

@Service
public class ICombatService {

    private final Random random = new Random();

    public void lancerCombatTerminal(Dresseur dresseur1, Dresseur dresseur2) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n🔥 Combat entre " + dresseur1.getPrenom() + " et " + dresseur2.getPrenom() + " !");

        afficherDeck(dresseur1);
        afficherDeck(dresseur2);

        Carte carte1 = choisirCarte(scanner, dresseur1);
        Carte carte2 = choisirCarte(scanner, dresseur2);

        System.out.println("\n⚔️ Début du combat entre " + carte1.getPokemon().getNom() + " et " + carte2.getPokemon().getNom() + " !\n");

        boolean tourDresseur1 = random.nextBoolean();

        while (carte1.getPokemon().getPv() > 0 && carte2.getPokemon().getPv() > 0) {
            if (tourDresseur1) {
                executerAttaque(scanner, carte1, carte2, dresseur1.getPrenom(), dresseur2.getPrenom());
                if (carte2.getPokemon().getPv() <= 0) break;
            } else {
                executerAttaque(scanner, carte2, carte1, dresseur2.getPrenom(), dresseur1.getPrenom());
                if (carte1.getPokemon().getPv() <= 0) break;
            }
            tourDresseur1 = !tourDresseur1;
        }

        if (carte1.getPokemon().getPv() == 0) {
            transfererMeilleureCarte(dresseur1, dresseur2);
        } else {
            transfererMeilleureCarte(dresseur2, dresseur1);
        }
    }

    private void afficherDeck(Dresseur dresseur) {
        System.out.println("\n🔹 Deck de " + dresseur.getPrenom() + " :");
        int i = 1;
        for (Carte carte : dresseur.getCarteList()) {
            System.out.println(i + ". " + carte.getPokemon().getNom() +
                    " (Niveau: " + carte.getPokemon().getNiveau() +
                    ", PV: " + carte.getPokemon().getPv() + ")");
            i++;
        }
    }

    private Carte choisirCarte(Scanner scanner, Dresseur dresseur) {
        Carte carteChoisie = null;
        while (carteChoisie == null) {
            System.out.print("\n🎴 " + dresseur.getPrenom() + ", choisis une carte (1-" + dresseur.getCarteList().size() + "): ");
            int choix = scanner.nextInt();
            scanner.nextLine();
            if (choix >= 1 && choix <= dresseur.getCarteList().size()) {
                carteChoisie = dresseur.getCarteList().get(choix - 1);
            } else {
                System.out.println("❌ Choix invalide. Réessaie !");
            }
        }
        return carteChoisie;
    }

    private String choisirAttaque(Scanner scanner, Carte carte) {
        String attaqueChoisie = null;
        while (attaqueChoisie == null) {
            System.out.println("\n💥 Attaques de " + carte.getPokemon().getNom() + " :");
            System.out.println("1. " + carte.getPokemon().getAttaque1());
            System.out.println("2. " + carte.getPokemon().getAttaque2());
            System.out.print("⚡ Choisis ton attaque (1-2): ");
            int choix = scanner.nextInt();
            scanner.nextLine();
            if (choix == 1 || choix == 2) {
                attaqueChoisie = (choix == 1) ? carte.getPokemon().getAttaque1() : carte.getPokemon().getAttaque2();
            } else {
                System.out.println("❌ Choix invalide. Réessaie !");
            }
        }
        return attaqueChoisie;
    }

    private void executerAttaque(Scanner scanner, Carte attaquant, Carte defenseur, String dresseurAttaquant, String dresseurDefenseur) {
        String attaque = choisirAttaque(scanner, attaquant);
        double efficacite = attaquant.getPokemon().getType().getEfficacite(defenseur.getPokemon().getType());
        int degats = calculerDegats(attaquant.getPokemon(), efficacite);

        String messageEfficacite = "";
        if (efficacite > 1) {
            messageEfficacite = "🔥 C'est super efficace !";
        } else if (efficacite < 1) {
            messageEfficacite = "😕 Ce n'est pas très efficace...";
        }

        defenseur.getPokemon().setPv(Math.max(0, defenseur.getPokemon().getPv() - degats));

        System.out.println("\n⚔️ " + dresseurAttaquant + " utilise " + attaque + " avec " + attaquant.getPokemon().getNom() +
                " ! (Dégâts: " + degats + ")\n" +
                messageEfficacite + "\n" +
                "💥 " + dresseurDefenseur + " (" + defenseur.getPokemon().getNom() + ") PV restants : " + defenseur.getPokemon().getPv());

        if (defenseur.getPokemon().getPv() == 0) {
            System.out.println("💀 " + defenseur.getPokemon().getNom() + " est KO !");
            System.out.println("🏆 " + dresseurAttaquant + " remporte la victoire avec " + attaquant.getPokemon().getNom() + " !");
        }
    }

    private void transfererMeilleureCarte(Dresseur perdant, Dresseur gagnant) {
        if (perdant.getCarteList().isEmpty()) {
            System.out.println("❌ " + perdant.getPrenom() + " n'a plus de cartes à donner.");
            return;
        }


        Carte meilleureCarte = perdant.getCarteList().stream()
                .max(Comparator.comparingInt(c -> c.getPokemon().getNiveau() + c.getPokemon().getPv()))
                .orElse(null);

        if (meilleureCarte != null) {

            perdant.getCarteList().remove(meilleureCarte);
            gagnant.getCarteList().add(meilleureCarte);
            meilleureCarte.setDresseur(gagnant);

            System.out.println("\n📜 " + perdant.getPrenom() + " donne sa meilleure carte (" + meilleureCarte.getPokemon().getNom() + ") à " + gagnant.getPrenom() + " !");
        }
    }

    private int calculerDegats(Pokemon attaquant, double efficacite) {
        int baseDamage = random.nextInt(20) + 10;
        return (int) (baseDamage * efficacite);
    }
}