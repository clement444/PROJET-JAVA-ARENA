package models;

public class PokemonEau extends Pokemon {

    public PokemonEau(String nom) {
        super(nom, 100, 20);
    }

    @Override
    public int calculerDegats(Pokemon cible) {
        if (cible instanceof PokemonFeu) {
            return attaque * 2;
        }
        return attaque;
    }
}
