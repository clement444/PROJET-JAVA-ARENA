package models;

public abstract class Pokemon {

    protected String nom;
    protected int pv;
    protected int pvMax;
    protected int attaque;

    public Pokemon(String nom, int pvMax, int attaque) {
        this.nom = nom;
        this.pvMax = pvMax;
        this.pv = pvMax;
        this.attaque = attaque;
    }

    public String getNom() {
        return nom;
    }

    public int getPv() {
        return pv;
    }

    public int getPvMax() {
        return pvMax;
    }

    public boolean estKO() {
        return pv <= 0;
    }

    public abstract int calculerDegats(Pokemon cible);

}
