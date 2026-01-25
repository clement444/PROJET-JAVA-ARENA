package game;

import java.util.ArrayList;
import models.Pokemon;
import java.util.Random;
import models.PokemonFeu;
import models.PokemonEau;
import models.PokemonPlante;


public class Dresseur {

    private String nomEquipe;
    private ArrayList<Pokemon> equipe;
    private int credits;

    // Constructeur
    public Dresseur(String nomEquipe) {
        this.nomEquipe = nomEquipe;
        this.equipe = new ArrayList<>();
        this.credits = 0;
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
}
