package combat;

import models.Pokemon;
import exceptions.ActionInterditeException;

public class Combat {

    public static void attaquer(Pokemon attaquant, Pokemon cible) 
        throws ActionInterditeException {

        if (attaquant.estKO()) {
            throw new ActionInterditeException(
                attaquant.getNom() + " est KO et ne peut pas attaquer."
            );
        }

        if (cible.estKO()) {
            throw new ActionInterditeException(
                cible.getNom() + " est déjà KO."
            );
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
