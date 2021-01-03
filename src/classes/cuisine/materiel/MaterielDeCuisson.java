package classes.cuisine.materiel;
import classes.cuisine.Ingredient;
import classes.cuisine.IngredientCuit;

/**
 * Classe enfant de matériel et classe parent des outils de cuisson
 * @version 1.0
 * @author Maïa DA SILVA
 */
public class MaterielDeCuisson extends Materiel {

    /**
     * Constructeur
     */
    // Constructeur
    public MaterielDeCuisson() {
        super(1, 10);
    }

    // Méthodes

    /**
     * Permet de démarrer la cuisson des ingrédients contenus dans l'outil
     * @return true si la cuisson a bien été démarrée
     */
    public boolean startCuisson() {
        // start timer
        return true ;
    }

    /**
     * Permet de stoper la cuisson des ingrédients contenus dans l'outil
     * @param tmpsCuisson
     * @return true si la cuisson a bien été arrêtée
     */
    public boolean stopCuisson(double tmpsCuisson) {
        IngredientCuit ingredient ;
        // Pour tous les ingrédients contenus dans l'outil
        for (int i = 0 ; i > this.objetsContenus.size() ; i++) {
            ingredient = (IngredientCuit) this.objetsContenus.get(i);
            // si le temps de cuisson est inferieur à la cuisson de l'ingrédient alors il "reste" cru
            if (tmpsCuisson < ingredient.getTempsCuisson()) {
                ingredient.setEtat(Ingredient.Etat.CRU);
            // si le temps de cuisson correspond à la cuisson de l'ingrédient alors il est cuit
            } else if (tmpsCuisson == ingredient.getTempsCuisson()) {
                ingredient.setEtat(Ingredient.Etat.CUIT);
            // si le temps de cuisson est supérieur à la cuisson de l'ingrédient alors il est brulé
            } else if (tmpsCuisson > ingredient.getTempsCuisson()) {
                ingredient.setEtat(Ingredient.Etat.BRULE);
            } else {
                return false ;
            }
        }
        return true ;
    }
}