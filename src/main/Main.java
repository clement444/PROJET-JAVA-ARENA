package main;

import java.util.Scanner;
import models.PokemonFeu;
import models.PokemonEau;
import combat.Combat;


public class Main {
   
    public static void main(String[] args) { 
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Bienvenue dans le jeu Pokemon ===");
        System.out.println("Chargement du jeu...");
        System.out.print("Nom de votre équipe : ");
        String nomEquipe = scanner.nextLine();

        int choix = -1;

        System.out.println("Equipe \"" + nomEquipe + "\" enregistrée.");

        while (choix != 3) {

            System.out.println();
            System.out.println("=== MENU PRINCIPAL ===");
            System.out.println("1 - Nouvelle partie");
            System.out.println("2 - Charger une partie");
            System.out.println("3 - Quitter");
            System.out.print("Votre choix : ");
            
            if (scanner.hasNextInt()) {
                choix = scanner.nextInt();
            } else {
                scanner.next();
                choix = -1;
            }
            
            switch (choix) {
                case 1:
                    System.out.println("Nouvelle partie (prochain truc à ajouté)");
                    break;
                case 2:
                    System.out.println("Chargement de la partie (prochain truc à ajouté)");
                    break;
                case 3:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }

        PokemonFeu feu = new PokemonFeu("Salameche");
        PokemonEau eau = new PokemonEau("Carapuce");

        Combat.attaquer(feu, eau);
        Combat.attaquer(eau, feu);
    }
}