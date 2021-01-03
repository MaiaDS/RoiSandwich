package classes.cuisine.materiel;

import classes.cuisine.Ingredient;

/**
 * Classe enfant de matériel, station de découpe pour les ingrédients découpables uniquement
 * @version 1.0
 * @author Mickeal PIRRES
 *
 * @version 3.0
 * @author Maïa DA SILVA
 */
public class Decoupe extends Materiel {

    /**
     * Constructeur
     */
    public Decoupe() {
        super(1, 10);
    }

    // Méthodes

    /**
     * @return true si l'ingrédient a bien été découpé
     */
    public boolean decouper () {
        Ingredient ingredient = (Ingredient) this.objetsContenus.get(0);
        // gestion du temps ...
        ingredient.setTransformer(true) ;
        return true ;
    }
}
