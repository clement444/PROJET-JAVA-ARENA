package game;

import java.util.ArrayList;
import models.Pokemon;

public class Dresseur {

    private String nomEquipe;
    private ArrayList<Pokemon> equipe;
    private int credits;

    public Dresseur(String nomEquipe) {
        this.nomEquipe = nomEquipe;
        this.equipe = new ArrayList<>();
        this.credits = 0;
    }

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
}
