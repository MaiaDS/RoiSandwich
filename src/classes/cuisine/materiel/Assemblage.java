package classes.cuisine.materiel;

/**
 * Classe enfant de matériel, station recevant les assiettes afin d'assembler les commandes
 * @version 1.0
 * @author Maïa DA SILVA
 */
public class Assemblage extends Materiel {

    /**
     * Constructeur
     * @param capaciteMax
     */
    public Assemblage(int capaciteMax) {
        super(capaciteMax, 10);
    }

    // Méthodes

    /**
     * @Override méthode ajouterObjet(Objet objet) de la classe Matériel
     * Permet d'ajouter une assiette sur la station d'assemblage
     * @param assiette
     * @return true si l'assiette a bien été ajouté
     * @throws IllegalAccessException
     */
    public boolean ajouterObjet (Assiette assiette) throws IllegalAccessException {
        return super.ajouterObjet(assiette) ;
    }

    /**
     * @Override méthode retirerObjet(Objet objet) de la classe Matériel
     * Permet de retirer une assiette de la station d'assemblage
     * @param assiette
     * @return true si la l'assiette a bien été retiré
     *
     * @version 1.0
     * @author Maia DA SILVA
     */
    public boolean retirerObjet(Assiette assiette) {
        return this.objetsContenus.remove(assiette) ;
    }

}