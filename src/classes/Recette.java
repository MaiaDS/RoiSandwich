package classes;
import classes.cuisine.Ingredient;
import classes.cuisine.Ingredient.Etat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Commentaire de documentation de la classe
 * @version 2.0
 * @author Maïa DA SILVA
 */
public class Recette {

    // Variables de classe

    /**
     * Enumération des noms de recettes disponibles
     */
    public enum Noms {
        SIMPLE, MENU, MAXI, FRITES,
    }

    /**
     * Enumération des différents types de steaks disponibles
     */
    public enum Steaks {
        BOEUF, POULET, VEGE
    }

    /**
     * Nom de la recette
     */
    private Noms nom;

    /**
     * Steak choisi pour la recette, null si frite
     */
    private Steaks viande;

    /**
     * Liste des ingrédients et de leur quantité nécéssaire à la recette
     */
    public HashMap<Ingredient, Integer> ingredients;

    /**
     * Constructeur avec précision de la viande
     * @param nom
     * @param viande
     */
    public Recette(Noms nom, Steaks viande) {
        this.nom = nom;
        this.viande = viande;
        this.ingredients = new HashMap<>();
        // initialisation des ingrédients
        switch (this.nom) {
            case FRITES:
                this.ingredients.put(new Ingredient(Ingredient.Nom.PATATE,Etat.CUIT,true), 1);
                break;
            case MAXI:
                recetteBurger(2, 2, this.viande);
                break;
            case MENU:
                this.ingredients.put(new Ingredient(Ingredient.Nom.PATATE,Etat.CUIT,true), 1);
            case SIMPLE:
                recetteBurger(1, 1, this.viande);
        }
    }

    /**
     * Constructeur sans précision de viande : frites
     * @param nom correspondant à la recette
     */
    public Recette(Noms nom) {
        this.nom = nom;
        this.viande = null;
        this.ingredients = new HashMap<>();
        this.ingredients.put(new Ingredient(Ingredient.Nom.PATATE,Etat.CUIT,true), 1);
    }

    // Getteur

    /**
     * @return le nom de la recette
     */
    public Noms getNom() {
        return nom;
    }

    /**
     * Permet d'obtenir la liste des ingrédients pour la réalisation d'un burger
     * @param nbSteak
     * @param nbFromage
     * @param viande
     */
    private void recetteBurger(int nbSteak, int nbFromage, Steaks viande) {
        // ingrédients de base
        this.ingredients.put(new Ingredient(Ingredient.Nom.SALADE,Etat.CRU,true), 1);
        this.ingredients.put(new Ingredient(Ingredient.Nom.TOMATE,Etat.CRU,true), 1);
        this.ingredients.put(new Ingredient(Ingredient.Nom.OIGNON,Etat.CRU, true), 1);
        this.ingredients.put(new Ingredient(Ingredient.Nom.PAIN, Etat.CRU, false), 1);
        this.ingredients.put(new Ingredient(Ingredient.Nom.FROMAGE, Etat.CRU, false), nbFromage);
        // précision du steak
        Ingredient.Nom typeSteak;
        if (viande == Steaks.BOEUF) {
            typeSteak = Ingredient.Nom.STEAK_DE_BOEUF;
        } else if (this.viande == Steaks.POULET) {
            typeSteak = Ingredient.Nom.STEAK_DE_POULET;
        } else {
            typeSteak = Ingredient.Nom.STEAK_DE_SOJA;
        }
        this.ingredients.put(new Ingredient(typeSteak,Etat.CUIT,false), nbSteak);
    }
}