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

	private Recette.Noms nomPlat;

//    Mickael ajout de l etat de l'assiette. 
	private EtatAssiette etatAssiette;

	public EtatAssiette getEtatAssiette() {
		return etatAssiette;
	}

	public void setEtatAssiette(EtatAssiette etatAssiette) {
		this.etatAssiette = etatAssiette;
	}

	public enum EtatAssiette {
		PROPRE, SALE, PLAT
	}

	public String getImgAssiette() {
		String s = null;
		switch (etatAssiette) {
		case PROPRE:
			s = "../image/assiette.png";
			break;
		case SALE:
			s = "../image/assiettesSales.png";
			break;
		case PLAT:
			s = "../image/plat-simple.png";
			break;
		}
		return s;

	}

	/**
	 * Constructeur
	 */
	public Assiette() {
		super(10, 10);
		this.nomPlat = null;
		this.etatAssiette = EtatAssiette.PROPRE;
	}

	// Getteur

	/**
	 * @return le nom du plat contenu dans l'assiette
	 * @return null si aucun plat n'a été créé par le joueur
	 */
	public Recette.Noms getPlat() {
		return this.nomPlat;
	}

	// Setteur

	/**
	 * Permet au joueur d'indiqquer quel plat il a créé
	 * 
	 * @param nomRecette
	 */
	public void setPlat(Recette.Noms nomRecette) {
		this.nomPlat = nomRecette;
	}

	// Méthodes

	/**
	 * @Override méthode ajouterObjet(Objet objet) de la classe Matériel Permet
	 *           d'ajouter un ingrédient "dans" l'assiette
	 * @param ingredient
	 * @return true si l'ingrédient a bien été ajouté
	 * @throws IllegalAccessException
	 */
	public boolean ajouterObjet(Ingredient ingredient) {
		setEtatAssiette(EtatAssiette.PLAT);
		return super.ajouterObjet(ingredient);
	}

	/**
	 * @Override méthode retirerObjet(Objet objet) de la classe Matériel Permet de
	 *           retirer un ingredient de l'assiette
	 * @param ingredient
	 * @return true si la l'ingredient a bien été retiré
	 *
	 * @version 1.0
	 * @author Maia DA SILVA
	 */
	public boolean retirerObjet(Ingredient ingredient) {
		return this.objetsContenus.remove(ingredient);
	}
	

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
