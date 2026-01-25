package main;

import java.util.Scanner;

public class Main {
   
    public static void main(String[] args) { 
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Bienvenue dans le jeu Pokemon ===");
        System.out.println("Chargement du jeu...");
        System.out.print("Nom de votre équipe : ");
        String nomEquipe = scanner.nextLine();

        System.out.println("Equipe \"" + nomEquipe + "\" enregistrée.");
    }
}