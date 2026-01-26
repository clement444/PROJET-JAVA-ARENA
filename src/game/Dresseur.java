package game;

import java.util.ArrayList;
import models.Pokemon;
import java.util.Random;
import models.PokemonFeu;
import models.PokemonEau;
import models.PokemonPlante;
import java.util.Map;
import java.util.HashMap;
import exceptions.ActionInterditeException;




public class Dresseur {

    private String nomEquipe;
    private ArrayList<Pokemon> equipe;
    private int credits;
    private Map<String, Integer> inventaire;


    // Constructeur
    public Dresseur(String nomEquipe) {
        this.nomEquipe = nomEquipe;
        this.equipe = new ArrayList<>();
        this.credits = 0;
        this.inventaire = new HashMap<>();
    }

    // Getters
    public String getNomEquipe() {
        return nomEquipe;
    }

    public int getCredits() {
        return credits;
    }

    public void ajouterCredits(int montant) {
        credits += montant;
    }

    public void ajouterPokemon(Pokemon pokemon) {
        equipe.add(pokemon);
    }

    public ArrayList<Pokemon> getEquipe() {
        return equipe;
    }

    public void genererEquipeDepart() {
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            int choix = random.nextInt(3);

            switch (choix) {
                case 0:
                    equipe.add(new PokemonFeu("Pokemon Feu"));
                    break;
                case 1:
                    equipe.add(new PokemonEau("Pokemon Eau"));
                    break;
                case 2:
                    equipe.add(new PokemonPlante("Pokemon Plante"));
                    break;
            }
        }
    }

    public void afficherEquipe() {
        System.out.println("Etat de l'equipe :");

        for (int i = 0; i < equipe.size(); i++) {
            Pokemon p = equipe.get(i);
            System.out.println(
                (i + 1) + " - " + p.getNom() +
                " : " + p.getPv() + "/" + p.getPvMax() + " PV"
            );
        }
    }
    
    public void ajouterObjet(String nomObjet, int quantite) {
        int quantiteActuelle = inventaire.getOrDefault(nomObjet, 0);
        inventaire.put(nomObjet, quantiteActuelle + quantite);
    }

    public void afficherInventaire() {
        System.out.println("Inventaire :");

        if (inventaire.isEmpty()) {
            System.out.println("Aucun objet.");
            return;
        }

        for (String nomObjet : inventaire.keySet()) {
            System.out.println("- " + nomObjet + " x" + inventaire.get(nomObjet));
        }
    }

    public void utiliserPotion(int indexPokemon)
        throws ActionInterditeException {

        if (!inventaire.containsKey("Potion") || inventaire.get("Potion") <= 0) {
            throw new ActionInterditeException("Aucune potion disponible.");
        }

        if (indexPokemon < 0 || indexPokemon >= equipe.size()) {
            System.out.println("Pokemon invalide.");
            return;
        }

        Pokemon p = equipe.get(indexPokemon);

        if (p.getPv() == p.getPvMax()) {
            throw new ActionInterditeException(
                p.getNom() + " a déjà tous ses PV."
            );
        }

        p.soigner(20);

        inventaire.put("Potion", inventaire.get("Potion") - 1);

        System.out.println(p.getNom() + " est soigné. PV : "
                + p.getPv() + "/" + p.getPvMax());
    }

    public Map<String, Integer> getInventaire() {
        return inventaire;
    }
}
