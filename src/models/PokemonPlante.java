package models;

public class PokemonPlante extends Pokemon {

    public PokemonPlante(String nom) {
        super(nom, 100, 20);
    }

    @Override
    public int calculerDegats(Pokemon cible) {
        if (cible instanceof PokemonEau) {
            return attaque * 2;
        }
        return attaque;
    }
}
