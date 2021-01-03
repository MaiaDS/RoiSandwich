package classes.cuisine.materiel;

import classes.Recette;
import classes.cuisine.Ingredient;
import classes.cuisine.Ingredient.Nom;

/**
 * Classe enfant de matériel, assiette contenant des ingrédients / un plat
 * 
 * @version 1.0 & 3.0
 * @author Mickeal PIRRES
 *
 * @version 2.0
 * @author Maïa DA SILVA
 */
public class Assiette extends Materiel {

	/**
	 * Etats que peut prendre l'assiette lors d'une partie
	 */
	public enum EtatAssiette {
		PROPRE, SALE, PLAT
	}

	/**
	 * etat de l'assiette
	 */
	private EtatAssiette etatAssiette;

	/**
	 * Constructeur
	 */
	public Assiette() {
		super(10, 10);
		this.etatAssiette = EtatAssiette.PROPRE;
	}

	// Getteurs

	/**
	 * @return l'état de l'assiette
	 */
	public EtatAssiette getEtatAssiette() {
		return etatAssiette;
	}

	/**
	 * @return l'image correspondant à l'état de l'assiette
	 */
	public String getImgAssiette() {
		String s = null;
		switch (etatAssiette) {
			case PROPRE:
				s = "../image/assiette.png";
				break;
			case SALE:
				s = "../image/assiette_sale.png";
				break;
			case PLAT:
				s = "../image/plat-simple.png";
				break;
		}
		return s;

	}

	// Setteur

	/**
	 * Permet de modifier l'état de l'assiette
	 * @param etatAssiette
	 */
	public void setEtatAssiette(EtatAssiette etatAssiette) {
		this.etatAssiette = etatAssiette;
	}

	// Méthodes

	/**
	 * @Override méthode ajouterObjet(Objet objet) de la classe Matériel
	 * Permet d'ajouter un ingrédient "dans" l'assiette
	 * @param ingredient
	 * @return true si l'ingrédient a bien été ajouté
	 * @throws IllegalAccessException
	 */
	public boolean ajouterObjet(Ingredient ingredient) {
		setEtatAssiette(EtatAssiette.PLAT);
		return super.ajouterObjet(ingredient);
	}

	/**
	 * @Override méthode retirerObjet(Objet objet) de la classe Matériel
	 * Permet de retirer un ingredient de l'assiette
	 * @param ingredient
	 * @return true si la l'ingredient a bien été retiré
	 */
	public boolean retirerObjet(Ingredient ingredient) {
		return this.objetsContenus.remove(ingredient);
	}


	/**
	 * Permet de verifier si un infrédient est présent dans l'assiette
	 * @param ingredient
	 * @return true s'il est dans l'assiette
	 */
	public boolean verifierSiIngredientPresentDansAssiette(Ingredient ingredient) {
		boolean estContenu = false;
		for (int i = 0; i < this.objetsContenus.size(); i++) {
			Ingredient ingDansAssiette = (Ingredient) this.objetsContenus.get(i);
			if (ingredient.getNom().equals(ingDansAssiette.getNom())) {
				estContenu = true;
			}
			else if(ingredient.isSteak() && ingDansAssiette.isSteak()) {
				estContenu = true;
			}
		}
		return estContenu;
	}
}
