package classes.cuisine;

/**
 * Commentaire de documentation de la classe IngrÃ©dient
 * @version 1.0
 * @author Thomas MOSCONI
 *
 * @version 2.0
 * @author Maia DA SILVA
 */
public class Ingredient {

	/**
	 * Nom de l'ingrédient
	 */
	private Nom nom;
	/**
	 * Booléen permettant de savoir si l'ingrédient est découpé ou non
	 */
	private boolean transformer;
	/**
	 * Etat de cuisson de l'ingrédient
	 */
	private Etat etat;

	/**
	 * Enumération des différents états de cuisson possibles
	 */
	public enum Etat {
		CRU, CUIT, BRULE
	}

	/**
	 * Enumération des noms des ingrédients disponibles
	 */
    public enum Nom{PATATE, SALADE, TOMATE, OIGNON, PAIN, FROMAGE, STEAK_DE_SOJA, STEAK_DE_POULET, STEAK_DE_BOEUF}

	/**
	 * Constructeur
	 * @param nom correspond au nom de l'ingrédient
	 */
	public Ingredient(Nom nom) {
		this.nom = nom;
		this.etat = Etat.CRU;
    	this.transformer = false ;
	}

	/**
	 * Constructeur
	 * @param nom correspond au nom de l'ingrédient
	 * @param etat correspond à l'état de cuisson de l'ingrédient
	 * @param transforme correspond à l'état de découpe de l'ingrédient
	 */
	public Ingredient(Nom nom,Etat etat, boolean transforme) {
		this.nom = nom;
		this.etat = etat;
    	this.transformer = transforme ;
	}

	// Getteurs

	/**
	 * @return l'état de cuisson d'un ingrédient
	 */
	public Etat getEtat() {
		return this.etat;
	}

	/**
	 * Retourne l'état de transformation d'un ingrédient
	 * @return true si l'ingrédient est découpé, false sinon
	 */
	public boolean getTransformer() {
		return transformer;
	}

	/**
	 * @return le nom de l'ingrédient
	 */
	public Nom getNom() {
		return nom;
	}

	/**
	 * @return le lien vers l'image correspondant à l'état de l'ingrédient
	 */
	public String getImgIngredient() {

		switch(this.getNom()) {
			case PATATE :
				if(this.getEtat() == Etat.CRU) {
					if(this.getTransformer()) {
						return "../image/patate_decoupee.png";
					}
					else {
						return "../image/patate.png";
					}}
				else { return "../image/frites.png"; }
			case SALADE :
				if(this.getTransformer()) {
					return "../image/salade_decoupee.png";
				}
				else {
					return "../image/salade_entiere.png";
				}
			case TOMATE :
				if(this.getTransformer()) {
					return "../image/tomate-decoupee.png";
				}
				else {
					return "../image/tomate_entiere.png";
				}
			case OIGNON :
				if(this.getTransformer()) {
					return "../image/oignon_decoupe.png";
				}
				else {
					return "../image/oignon_entier.png";
				}
			case PAIN :
				return "../image/pains.png";
			case FROMAGE :
				return "../image/fromage.png";
			case STEAK_DE_SOJA :
				return "../image/soja.png";
			case STEAK_DE_POULET :
				return "../image/poulet.png";
			case STEAK_DE_BOEUF:
				return "../image/boeuf.png";
		}
		return "../image/patate_decoupee.png";
	}

	// Setteurs

	/**
	 * Permet de faire passer l'ingrédient d'un état entier à découpé
	 * @param etat correspond au nouvel état de découpage de l'ingrédient
	 */
	public void setTransformer(boolean etat) {
		this.transformer = etat ;
	}

	/**
	 * Permet de changer l'état de cuisson d'un ingrédient
	 * @param etat correspond au nouvel état de cuisson de l'ingrédient
	 */
	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	// Méthodes

	/**
	 * @return true si l'ingrédient est un steak
	 */
	public boolean isSteak () {
		return (this.getNom() == Nom.STEAK_DE_BOEUF
				|| this.getNom() == Nom.STEAK_DE_POULET
				|| this.getNom() == Nom.STEAK_DE_SOJA) ;
	}

	/**
	 * @return true si l'ingrédient est découpable
	 */
	public boolean isDecoupable () {
		return (this.getNom() == Nom.PATATE
				|| this.getNom() == Nom.TOMATE
				|| this.getNom() == Nom.OIGNON
				|| this.getNom() == Nom.SALADE) ;
	}
}
