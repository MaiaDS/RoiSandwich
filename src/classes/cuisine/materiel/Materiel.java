package classes.cuisine.materiel;

import java.util.ArrayList;

/**
 * Classe parent des outils
 * 
 * @version 1.0
 * @author Mickeal PIRRES
 */
public class Materiel {

	// Variables de classe

	/**
	 * représente le nombre d'objet maximum que peut contenir l'outil
	 */
	private int capaciteMax;

	/**
	 * représente le temps d'exécution nécessaire à la réalisation de la tâche
	 * que doit effectuer l'outil
	 */
	private int tempsExecution;

	/**
	 * liste des objets contenus dans l'outil, sa taille ne doit pas dépasser la
	 * capacité maximum
	 */
	public ArrayList objetsContenus;

	/**
	 * Constructeur
	 * 
	 * @param capaciteMax
	 * @param tempsExecution
	 */
	public Materiel(int capaciteMax, int tempsExecution) {
		this.capaciteMax = capaciteMax;
		this.tempsExecution = tempsExecution;
		objetsContenus = new ArrayList();
	}

	// Getteur

	/**
	 * @return true si l'emplacement est vide, false sinon
	 */
	public boolean getEmplacementVide() {
		if (objetsContenus.size() >= 1) {
			return false;
		} else {
			return true;
		}
	}

	// Setteur

	/**
	 * PErmet de modifier la capacité maximale d'un matériel
	 * @param capaciteMax
	 */
	public void setCapaciteMax(int capaciteMax) {
		this.capaciteMax = capaciteMax;
	}

	// Méthodes

	/**
	 * Permet d'ajouter un objet "dans" un matériel
	 * 
	 * @param objet
	 * @return true si la l'objet a bien été ajouté
	 * @throws IllegalAccessException
	 *
	 * @version 1.0
	 * @author Mickeal PIRRES
	 *
	 * @version 2.0
	 * @author Maia DA SILVA
	 */
	public boolean ajouterObjet(Object objet) {
		return this.objetsContenus.add(objet);
	}

	/**
	 * Permet de retirer un objet d'un matériel
	 * 
	 * @param objet
	 * @return true si la l'objet a bien été retiré
	 *
	 * @version 1.0
	 * @author Maia DA SILVA
	 */
	public boolean retirerObjet(Object objet) {
		return this.objetsContenus.remove(objet);
	}
	
	
	public void afficherLaListeDesObjetsContenus() {
//		System.out.println("Classe Materiel : liste des objets contenus");
		for(int i=0; i<objetsContenus.size();i++) {
//			System.out.print(objetsContenus.get(i) + " ; ");
		}
	}
}