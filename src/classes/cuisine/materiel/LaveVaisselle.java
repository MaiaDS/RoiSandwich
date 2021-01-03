package classes.cuisine.materiel;

import classes.Recette;

/**
 * Classe enfant de matériel, contient des assiettes sale
 * @version 1.0
 * @author Mickeal PIRRES
 *
 * @version 2.0
 * @author Maïa DA SILVA
 */
public class LaveVaisselle extends Materiel {

    /**
     * Constructeur
     */
    public LaveVaisselle() {
        super(1, 10);
    }

    // Méthodes

    /**
     * @Override méthode ajouterObjet(Objet objet) de la classe Matériel
     * Permet d'ajouter une assiette dans l'evier
     * @param assiette
     * @return true si l'assiette a bien été ajouté
     * @throws IllegalAccessException
     */
    public boolean ajouterObjet (Assiette assiette) throws IllegalAccessException {
        return super.ajouterObjet(assiette);
    }

    /**
     * @Override méthode retirerObjet(Objet objet) de la classe Matériel
     * Permet de retirer une assiette du lave vaisselle
     * @param assiette
     * @return true si la l'assiette a bien été retirée
     *
     * @version 1.0
     * @author Maia DA SILVA
     */
    public boolean retirerObjet(Assiette assiette) {
        return this.objetsContenus.remove(assiette) ;
    }

    /**
     * Permet de nettoyer l'ensemble des objets placés dans le lave vaisselle
     * @return true si la méthode a bien été effectuée
     */
    public boolean nettoyer() {
        Assiette assiette ;
        // Pour chaque assiette
        for (int i = 0 ; i < this.objetsContenus.size() ; i++) {
            assiette = (Assiette) this.objetsContenus.get(i) ;
            // on vide le contenu
            assiette.objetsContenus.clear() ;
        }
        return true ;
    }
}