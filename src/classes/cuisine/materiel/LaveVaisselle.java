package classes.cuisine.materiel;

import classes.Recette;

/**
 * Classe enfant de matériel, contient des assiettes sale
 * 
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
	 * Permet de nettoyer l'ensemble des objets placés dans le lave vaisselle
	 * 
	 * @return true si la méthode a bien été effectuée
	 */
	public boolean nettoyer() {
		Assiette assiette;
		// Pour chaque assiette
		for (int i = 0; i < this.objetsContenus.size(); i++) {
			assiette = (Assiette) this.objetsContenus.get(i);
			// on vide le contenu
			assiette.objetsContenus.clear();
		}
		return true;
	}
}