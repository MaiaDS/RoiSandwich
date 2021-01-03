package classes.cuisine.materiel;

import classes.Recette;
import classes.cuisine.Ingredient;
import classes.cuisine.IngredientCuit;

/**
 * Classe enfant de matériel, contient des ingrédients cuisables
 * @version 1.0
 * @author Mickeal PIRRES
 *
 * @version 2.0
 * @author Maïa DA SILVA
 */
public class Poubelle extends Materiel {

    /**
     * Constructeur
     */
    public Poubelle() {
        super(10, 0);
    }

    // Méthodes

    /**
     * @Override méthode ajouterObjet(Objet objet) de la classe Matériel
     * Permet d'ajouter le contenu d'une assiette dans la poubelle
     * @param assiette
     * @return true si le contenu d'une assiette a bien été ajouté
     * @throws IllegalAccessException
     */
    public boolean ajouterObjet (Assiette assiette) {
        // pop up de confirmation
        super.ajouterObjet(assiette.objetsContenus);
        // envoyer assiette dans laveVaisselle
        return true ;
    }

    /**
     * Permet de "détruire" les aliments jetés dans la poubelle
     * @return true si la poubelle a été vidée
     */
    public boolean detruire() {
        // pop up de confirmation
        // ...
        this.objetsContenus.clear();
        return true ;
    }

}
