package main;

import java.util.Scanner;
import exceptions.ActionInterditeException;
import game.Dresseur;
import save.SaveManager;
import java.io.IOException;



public class Main {
   
    public static void main(String[] args) { 
        Scanner scanner = new Scanner(System.in);

        afficherLogoPokemon();
        System.out.println("=== Bienvenue dans le jeu Pokemon ===");
        System.out.println("Chargement du jeu...");
        System.out.print("Nom de votre équipe : ");
        String nomEquipe = scanner.nextLine();

        Dresseur dresseur = new Dresseur(nomEquipe);
        dresseur.genererEquipeDepart();
        dresseur.ajouterObjet("Potion", 2);
        dresseur.ajouterObjet("Pokeball", 1);

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
                    try {
                    dresseur.utiliserPotion(0);
                        } catch (ActionInterditeException e) {
                            System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        dresseur = SaveManager.charger();
                        System.out.println("Partie chargée !");
                    } catch (IOException e) {
                        System.out.println("Aucune sauvegarde trouvée.");
                    }
                    break;

                case 3:
                    try {
                        SaveManager.sauvegarder(dresseur);
                        System.out.println("Partie sauvegardée.");
                    } catch (IOException e) {
                        System.out.println("Erreur lors de la sauvegarde.");
                    }
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private static void afficherLogoPokemon() {
        System.out.println(
            "██████╗  ██████╗ ██╗  ██╗███████╗███╗   ███╗ ██████╗ ███╗   ██╗\n" +
            "██╔══██╗██╔═══██╗██║ ██╔╝██╔════╝████╗ ████║██╔═══██╗████╗  ██║\n" +
            "██████╔╝██║   ██║█████╔╝ █████╗  ██╔████╔██║██║   ██║██╔██╗ ██║\n" +
            "██╔═══╝ ██║   ██║██╔═██╗ ██╔══╝  ██║╚██╔╝██║██║   ██║██║╚██╗██║\n" +
            "██║     ╚██████╔╝██║  ██╗███████╗██║ ╚═╝ ██║╚██████╔╝██║ ╚████║\n" +
            "╚═╝      ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝     ╚═╝ ╚═════╝ ╚═╝  ╚═══╝\n"
        );
    }
}