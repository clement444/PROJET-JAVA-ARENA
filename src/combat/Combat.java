package combat;

import models.Pokemon;

public class Combat {

    public static void attaquer(Pokemon attaquant, Pokemon cible) {

        if (attaquant.estKO()) {
            System.out.println(attaquant.getNom() + " est KO et ne peut pas attaquer.");
            return;
        }

        if (cible.estKO()) {
            System.out.println(cible.getNom() + " est déjà KO.");
            return;
        }

        int degats = attaquant.calculerDegats(cible);
        cible.perdrePv(degats);

        System.out.println(attaquant.getNom() + " attaque " + cible.getNom()
                + " et inflige " + degats + " dégâts.");

        if (cible.estKO()) {
            System.out.println(cible.getNom() + " est KO !");
        }
    }
}
