package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import exceptions.ActionInterditeException;
import models.Pokemon;
import models.PokemonData;
import models.PokemonEau;
import models.PokemonFeu;
import models.PokemonPlante;




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
            int type = random.nextInt(3);

            switch (type) {
                case 0:
                    String nomFeu = PokemonData.POKEMON_FEU[
                        random.nextInt(PokemonData.POKEMON_FEU.length)
                    ];
                    equipe.add(new PokemonFeu(nomFeu));
                    break;

                case 1:
                    String nomEau = PokemonData.POKEMON_EAU[
                        random.nextInt(PokemonData.POKEMON_EAU.length)
                    ];
                    equipe.add(new PokemonEau(nomEau));
                    break;

                case 2:
                    String nomPlante = PokemonData.POKEMON_PLANTE[
                        random.nextInt(PokemonData.POKEMON_PLANTE.length)
                    ];
                    equipe.add(new PokemonPlante(nomPlante));
                    break;
            }
        }
    }

    public void afficherEquipe() {

        System.out.println();
        System.out.println("=== EQUIPE ===");

        for (int i = 0; i < equipe.size(); i++) {
            Pokemon p = equipe.get(i);

             String type = p.getClass().getSimpleName()
                .replace("Pokemon", "");

            System.out.println(
            (i + 1) + " - " + p.getNom() +
            " / " + type +
            " : " + p.getPv() + "/" + p.getPvMax() + " PV"
            );
        }
    }
    
    public void ajouterObjet(String nomObjet, int quantite) {
        int quantiteActuelle = inventaire.getOrDefault(nomObjet, 0);
        inventaire.put(nomObjet, quantiteActuelle + quantite);
    }

    public void afficherInventaire() {

        System.out.println();
        System.out.println("=== INVENTAIRE ===");

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
            throw new ActionInterditeException("Pokemon invalide.");
        }

        Pokemon p = equipe.get(indexPokemon);

        if (p.getPv() == p.getPvMax()) {
            throw new ActionInterditeException(
                p.getNom() + " a déjà tous ses PV."
            );
        }

        p.soigner(20);
        inventaire.put("Potion", inventaire.get("Potion") - 1);

        System.out.println(
            p.getNom() + " est soigné. PV : "
            + p.getPv() + "/" + p.getPvMax()
        );
    }

    public Map<String, Integer> getInventaire() {
        return inventaire;
    }

    public void utiliserRappel(int indexPokemon)
        throws ActionInterditeException {

        if (!inventaire.containsKey("Rappel") || inventaire.get("Rappel") <= 0) {
            throw new ActionInterditeException("Aucun rappel disponible.");
        }

        if (indexPokemon < 0 || indexPokemon >= equipe.size()) {
            throw new ActionInterditeException("Pokemon invalide.");
        }

        Pokemon p = equipe.get(indexPokemon);

        if (!p.estKO()) {
            throw new ActionInterditeException(
                p.getNom() + " n'est pas KO."
            );
        }

        int pvRestaures = p.getPvMax() / 2;
        p.soigner(pvRestaures);

        inventaire.put("Rappel", inventaire.get("Rappel") - 1);

        System.out.println(
            p.getNom() + " est ressuscité avec "
            + p.getPv() + "/" + p.getPvMax() + " PV."
        );
    }

    public void capturerPokemon(Pokemon pokemon)
        throws ActionInterditeException {

        if (!inventaire.containsKey("Pokeball") || inventaire.get("Pokeball") <= 0) {
            throw new ActionInterditeException("Aucune Pokéball disponible.");
        }

        double pourcentagePv = (double) pokemon.getPv() / pokemon.getPvMax();

        if (pourcentagePv > 0.3) {
            throw new ActionInterditeException(
                "Le Pokémon a plus de 30% de PV, capture impossible."
            );
        }

        inventaire.put("Pokeball", inventaire.get("Pokeball") - 1);
        equipe.add(pokemon);

        System.out.println(pokemon.getNom() + " a été capturé !");
    }
    
    public boolean equipeKO() {
        for (Pokemon p : equipe) {
            if (!p.estKO()) {
                return false;
            }
        }
        return true;
    }
}
