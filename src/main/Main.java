package main;

import java.util.Scanner;
import java.util.Random;
import java.io.IOException;

import game.Dresseur;
import models.Pokemon;
import models.PokemonFeu;
import models.PokemonEau;
import models.PokemonPlante;
import combat.Combat;
import save.SaveManager;
import exceptions.ActionInterditeException;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        afficherLogoPokemon();

        Dresseur dresseur = null;
        int choix = -1;

        System.out.println("=== Bienvenue dans le jeu Pokemon ===");

        while (choix != 3) {
            System.out.println();
            System.out.println("=== MENU PRINCIPAL ===");
            System.out.println("1 - Nouvelle partie");
            System.out.println("2 - Charger une partie");
            System.out.println("3 - Quitter");
            System.out.print("Votre choix : ");

            if (scanner.hasNextInt()) {
                choix = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Choix invalide, veuillez entrer un nombre.");
                scanner.nextLine();
                continue;
            }

            switch (choix) {

                case 1:
                    System.out.print("Nom de votre équipe : ");
                    String nomEquipe = scanner.nextLine();

                    dresseur = new Dresseur(nomEquipe);
                    dresseur.genererEquipeDepart();

                    dresseur.ajouterCredits(20);

                    System.out.println("Equipe créée avec succès !");
                    System.out.println("Vous commencez avec 20 crédits.");
                    lancerPartie(scanner, dresseur);
                    break;

                case 2:
                    try {
                        dresseur = SaveManager.charger();

                        if (dresseur.equipeKO() && dresseur.getCredits() < 20) {
                        System.out.println("Impossible de charger la partie.");
                        System.out.println("Toute l'équipe est KO et les crédits sont insuffisants.");
                        break;
                    }
                        System.out.println("Partie chargée !");
                        System.out.println("Equipe chargée : " + dresseur.getNomEquipe());
                        lancerPartie(scanner, dresseur);
                    } catch (IOException e) {
                        System.out.println("Aucune sauvegarde trouvée.");
                    }
                    break;

                case 3:
                    if (dresseur != null) {
                        try {
                            SaveManager.sauvegarder(dresseur);
                            System.out.println("Partie sauvegardée.");
                        } catch (IOException e) {
                            System.out.println("Erreur lors de la sauvegarde.");
                        }
                    }
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    /* ===================== MENU PARTIE ===================== */

    private static void lancerPartie(Scanner scanner, Dresseur dresseur) {

        int choix = -1;

        while (choix != 5) {
            System.out.println();
            System.out.println("=== MENU PARTIE ===");
            System.out.println("1 - Afficher l'équipe");
            System.out.println("2 - Lancer un combat");
            System.out.println("3 - Boutique");
            System.out.println("4 - Afficher l'inventaire");
            System.out.println("5 - Retour menu principal");
            System.out.print("Votre choix : ");

            if (scanner.hasNextInt()) {
                choix = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Choix invalide, veuillez entrer un nombre.");
                scanner.nextLine();
                continue;
            }

            switch (choix) {
                case 1:
                    dresseur.afficherEquipe();
                    break;

                case 2:
                    lancerCombat(scanner, dresseur);
                    break;

                case 3:
                    menuBoutique(scanner, dresseur);
                    break;

                case 4:
                    dresseur.afficherInventaire();
                    break;

                case 5:
                    try {
                        SaveManager.sauvegarder(dresseur);
                        System.out.println("Partie sauvegardée.");
                    } catch (IOException e) {
                        System.out.println("Erreur lors de la sauvegarde.");
                    }
                    return;

                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    /* ===================== COMBAT ===================== */

    private static void lancerCombat(Scanner scanner, Dresseur dresseur) {

        Pokemon sauvage = genererPokemonSauvage();
        Pokemon joueur = choisirPokemon(scanner, dresseur);

        System.out.println("Un " + sauvage.getNom() + " sauvage apparaît !");

        while (!sauvage.estKO()) {

            if (joueur.estKO()) {
            int choixKO = -1;

            while (choixKO != 1 && choixKO != 2) {
                System.out.println();
                System.out.println("Votre Pokémon est KO !");
                System.out.println("1 - Utiliser un objet (Rappel)");
                System.out.println("2 - Changer de Pokémon");
                System.out.print("Votre choix : ");

                if (scanner.hasNextInt()) {
                    choixKO = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    scanner.nextLine();
                    System.out.println("Choix invalide, veuillez entrer un nombre.");
                    continue;
                }

                switch (choixKO) {
                    case 1:
                        menuObjetCombat(scanner, dresseur, sauvage);
                        if (joueur.estKO()) {
                            System.out.println("Vous devez choisir un Pokémon valide.");
                            choixKO = -1; // on force à reposer la question
                        }

                        break;

                    case 2:
                        joueur = choisirPokemon(scanner, dresseur);
                        break;

                    default:
                        System.out.println("Choix invalide.");
                }
            }
        }


            System.out.println();
            System.out.println("=== MENU COMBAT ===");
            System.out.println("1 - Attaquer");
            System.out.println("2 - Inventaire");
            System.out.println("3 - Fuir");
            System.out.print("Votre choix : ");

            int choix;

            if (scanner.hasNextInt()) {
                choix = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Choix invalide, veuillez entrer un nombre.");
                scanner.nextLine();
                continue;
            }

            try {
                switch (choix) {

                    case 1:
                        Combat.attaquer(joueur, sauvage);
                        break;

                    case 2:
                        menuObjetCombat(scanner, dresseur, sauvage);
                        if (dresseur.getEquipe().contains(sauvage)) {
                            return; // capture réussie
                        }
                        break;

                    case 3:
                        System.out.println("Vous fuyez le combat.");
                        return;

                    default:
                        System.out.println("Choix invalide.");
                        continue;
                }

                if (!sauvage.estKO()) {
                    Combat.attaquer(sauvage, joueur);
                }

                if (dresseur.equipeKO()) {
                    System.out.println();
                    System.out.println("Tous vos Pokémon sont KO...");
                    System.out.println("Vous avez perdu le combat.");
                    System.out.println("Retentez votre chance dans une nouvelle partie");
                    return;
                }

                if (sauvage.estKO()) {
                    dresseur.ajouterCredits(10);
                    System.out.println("Pokemon sauvage vaincu !");
                    System.out.println("Vous gagnez 10 crédits.");
                }

            } catch (ActionInterditeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /* ===================== OBJETS (COMBAT) ===================== */

    private static void menuObjetCombat(
            Scanner scanner,
            Dresseur dresseur,
            Pokemon sauvage) {

        int nbPotions = dresseur.getInventaire().getOrDefault("Potion", 0);
        int nbRappels = dresseur.getInventaire().getOrDefault("Rappel", 0);
        int nbPokeballs = dresseur.getInventaire().getOrDefault("Pokeball", 0);

        System.out.println();
        System.out.println("=== INVENTAIRE ===");
        System.out.println("1 - Potion x " + nbPotions);
        System.out.println("2 - Rappel x " + nbRappels);
        System.out.println("3 - Pokéball (capture) x " + nbPokeballs);
        System.out.println("4 - Retour");
        System.out.print("Votre choix : ");

        int choix;

        if (scanner.hasNextInt()) {
            choix = scanner.nextInt();
            scanner.nextLine();
        } else {
            System.out.println("Choix invalide, veuillez entrer un nombre.");
            scanner.nextLine();
            return;
        }

        try {
            switch (choix) {
                case 1:
                    dresseur.afficherEquipe();
                    System.out.print("Choisissez le Pokémon : ");
                    dresseur.utiliserPotion(scanner.nextInt() - 1);
                    break;

                case 2:
                    dresseur.afficherEquipe();
                    System.out.print("Choisissez le Pokémon : ");
                    dresseur.utiliserRappel(scanner.nextInt() - 1);
                    break;

                case 3:
                    dresseur.capturerPokemon(sauvage);
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Choix invalide.");
            }
        } catch (ActionInterditeException e) {
            System.out.println(e.getMessage());
        }
    }

    /* ===================== BOUTIQUE ===================== */

    private static void menuBoutique(Scanner scanner, Dresseur dresseur) {

        int choix = -1;

        while (choix != 4) {
            System.out.println();
            System.out.println("=== BOUTIQUE ===");
            System.out.println("Crédits disponibles : " + dresseur.getCredits());
            System.out.println("1 - Potion (5 crédits)");
            System.out.println("2 - Rappel (20 crédits)");
            System.out.println("3 - Pokéball (10 crédits)");
            System.out.println("4 - Retour");
            System.out.print("Votre choix : ");

            if (scanner.hasNextInt()) {
                choix = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Choix invalide, veuillez entrer un nombre.");
                scanner.nextLine();
                continue;
            }

            switch (choix) {
                case 1:
                    acheterObjet(dresseur, "Potion", 5);
                    break;
                case 2:
                    acheterObjet(dresseur, "Rappel", 20);
                    break;
                case 3:
                    acheterObjet(dresseur, "Pokeball", 10);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private static void acheterObjet(Dresseur dresseur, String nom, int prix) {
        if (dresseur.getCredits() < prix) {
            System.out.println("Crédits insuffisants.");
            return;
        }
        dresseur.ajouterCredits(-prix);
        dresseur.ajouterObjet(nom, 1);
        System.out.println(nom + " acheté !");
    }

    /* ===================== OUTILS ===================== */

    private static Pokemon genererPokemonSauvage() {
        Random random = new Random();
        int type = random.nextInt(3);

        switch (type) {
            case 0:
                return new PokemonFeu("Pokemon Feu sauvage");
            case 1:
                return new PokemonEau("Pokemon Eau sauvage");
            default:
                return new PokemonPlante("Pokemon Plante sauvage");
        }
    }

    private static Pokemon choisirPokemon(Scanner scanner, Dresseur dresseur) {
        int choix;

        while (true) {
            dresseur.afficherEquipe();
            System.out.print("Choisissez un Pokémon : ");

            if (scanner.hasNextInt()) {
                choix = scanner.nextInt() - 1;
                scanner.nextLine();
            } else {
                scanner.nextLine();
                continue;
            }

            if (choix >= 0 && choix < dresseur.getEquipe().size()) {
                Pokemon p = dresseur.getEquipe().get(choix);
                if (!p.estKO()) {
                    return p;
                } else {
                    System.out.println("Ce Pokémon est KO.");
                }
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
