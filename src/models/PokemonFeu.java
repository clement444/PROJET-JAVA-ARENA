package models;

public class PokemonFeu extends Pokemon {

    public PokemonFeu(String nom) {
        super(nom, 100, 20);
    }

    @Override
    public int calculerDegats(Pokemon cible) {
        if (cible instanceof PokemonPlante) {
            return attaque * 2;
        }
        return attaque;
    }
}
