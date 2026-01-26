package save;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import game.Dresseur;
import models.*;

public class SaveManager {

    private static final String FILE_NAME = "save.csv";

    public static void sauvegarder(Dresseur dresseur) throws IOException {
        FileWriter writer = new FileWriter(FILE_NAME);

        // Sauvegarde équipe
        writer.write("EQUIPE\n");
        for (Pokemon p : dresseur.getEquipe()) {
            writer.write(
                p.getClass().getSimpleName() + ";" +
                p.getNom() + ";" +
                p.getPv() + ";" +
                p.getPvMax() + "\n"
            );
        }

        // Sauvegarde inventaire
        writer.write("INVENTAIRE\n");
        dresseur.getInventaire().forEach((nom, quantite) -> {
            try {
                writer.write(nom + ";" + quantite + "\n");
            } catch (IOException e) {
                // ignoré volontairement
            }
        });

        writer.close();
    }

    public static Dresseur charger() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));

        Dresseur dresseur = null;
        String ligne;
        boolean lectureEquipe = false;
        boolean lectureInventaire = false;

        while ((ligne = reader.readLine()) != null) {

            if (ligne.equals("EQUIPE")) {
                lectureEquipe = true;
                lectureInventaire = false;
                continue;
            }

            if (ligne.equals("INVENTAIRE")) {
                lectureEquipe = false;
                lectureInventaire = true;
                continue;
            }

            String[] parts = ligne.split(";");

            if (lectureEquipe) {
                Pokemon p = null;

                switch (parts[0]) {
                    case "PokemonFeu":
                        p = new PokemonFeu(parts[1]);
                        break;
                    case "PokemonEau":
                        p = new PokemonEau(parts[1]);
                        break;
                    case "PokemonPlante":
                        p = new PokemonPlante(parts[1]);
                        break;
                }

                if (dresseur == null) {
                    dresseur = new Dresseur("Equipe chargée");
                }

                p.perdrePv(p.getPvMax() - Integer.parseInt(parts[2]));
                dresseur.ajouterPokemon(p);
            }

            if (lectureInventaire && dresseur != null) {
                dresseur.ajouterObjet(parts[0], Integer.parseInt(parts[1]));
            }
        }

        reader.close();
        return dresseur;
    }
}
