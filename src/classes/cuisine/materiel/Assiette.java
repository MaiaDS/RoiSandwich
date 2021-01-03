package classes.cuisine.materiel;

import classes.Recette;
import classes.cuisine.Ingredient;
import classes.cuisine.Ingredient.Nom;

/**
 * Classe enfant de matériel, assiette contenant des ingrédients / un plat
 * 
 * @version 1.0
 * @author Mickeal PIRRES
 */
public class Assiette extends Materiel {

	/**
	 * énumération des etats possibles
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

	// Getteur

	/**
	 * @return l'état de l'assiette
	 */
	public EtatAssiette getEtatAssiette() {
		return etatAssiette;
	}

	/**
	 * @return l'adresse de l'image selon l etat de l'assiette
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
	 * Peremet de changer l'état de l'assiette
	 * @param etatAssiette correspond au nouvel etat
	 */
	public void setEtatAssiette(EtatAssiette etatAssiette) {
		this.etatAssiette = etatAssiette;
	}

	// Méthodes

	/**
	 * Verifie si un ingredient est présent dans la liste des objets contenus du materiel
	 * 
	 * @param ingredient
	 * @return true si contenu
	 */
	public boolean verifierSiIngredientPresentDansAssiette(Ingredient ingredient) {
		boolean estContenu = false;
		for (int i = 0; i < this.objetsContenus.size(); i++) {
			Ingredient ingDansAssiette = (Ingredient) this.objetsContenus.get(i);
			if (ingredient.getNom().equals(ingDansAssiette.getNom())) {
				estContenu = true;
			}
		}
		return estContenu;
	}
}
