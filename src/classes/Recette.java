package classes;
import classes.cuisine.Ingredient;
import java.util.HashMap;

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
                this.ingredients.put(new Ingredient(Ingredient.Nom.PATATE), 1);
                break;
            case MAXI:
                recetteBurger(2, this.viande);
                break;
            case MENU:
                this.ingredients.put(new Ingredient(Ingredient.Nom.PATATE), 1);
            case SIMPLE:
                recetteBurger(1, this.viande);
        }
    }

    /**
     * Constructeur sans précision de viande > frites
     * @param nom
     */
    public Recette(Noms nom) {
        this.nom = nom;
        this.viande = null;
        this.ingredients = new HashMap<>();
        this.ingredients.put(new Ingredient(Ingredient.Nom.PATATE), 1);
    }

    // Getteur

    /**
     * @return le nom de la recette
     */
    public Noms getNom() {
        return nom;
    }

    /**
     * @return la viande choisie pour la recette
     * @return null s'il s'agit de frites
     */
    public Steaks getViande() {
        return viande;
    }

    /**
     * Permet d'obtenir la liste des ingrédients pour la réalisation d'un burger
     * @param nbSteak
     * @param viande
     */
    private void recetteBurger(int nbSteak, Steaks viande) {
        // ingrédients de base
        this.ingredients.put(new Ingredient(Ingredient.Nom.SALADE), 1);
        this.ingredients.put(new Ingredient(Ingredient.Nom.TOMATE), 1);
        this.ingredients.put(new Ingredient(Ingredient.Nom.OIGNON), 1);
        this.ingredients.put(new Ingredient(Ingredient.Nom.PAIN), 1);
        this.ingredients.put(new Ingredient(Ingredient.Nom.FROMAGE), 1);
        // précision du steak
        Ingredient.Nom typeSteak;
        if (viande == Steaks.BOEUF) {
            typeSteak = Ingredient.Nom.STEAK_DE_BOEUF;
        } else if (this.viande == Steaks.POULET) {
            typeSteak = Ingredient.Nom.STEAK_DE_POULET;
        } else {
            typeSteak = Ingredient.Nom.STEAK_DE_SOJA;
        }
        this.ingredients.put(new Ingredient(typeSteak), nbSteak);
    }
}