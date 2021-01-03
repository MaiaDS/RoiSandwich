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
	 * Nom de l'ingrÃ©dient
	 */
	private Nom nom;
	/**
	 * BoolÃ©en permettant de savoir si l'ingrÃ©dient est dÃ©coupÃ© ou non
	 */
	private boolean transformer;
	/**
	 * Etat de cuisson de l'ingrÃ©dient
	 */
	private Etat etat;
	/**
	 * EnumÃ©ration des diffÃ©rents etats de cuisson possibles
	 */
	public enum Etat {
		CRU, CUIT, BRULE
	}

	/**
	 * EnumÃ©ration des noms des ingrÃ©dients disponibles
	 */
    public enum Nom{PATATE, SALADE, TOMATE, OIGNON, PAIN, FROMAGE, STEAK_DE_SOJA, STEAK_DE_POULET, STEAK_DE_BOEUF}


	/**
	 * Constructeur
	 * @param nom
	 */
	public Ingredient(Nom nom) {
		this.nom = nom;
		this.etat = Etat.CRU;
    	this.transformer = false ;
	}

	/**
	 * @param nom
	 * @param etat
	 * @param transformé
	 */
	//constructeur pour recette
	public Ingredient(Nom nom,Etat etat, boolean transformé) {
		this.nom = nom;
		this.etat = etat;
    	this.transformer = transformé ;
	}




	// Getteurs

	/**
	 * @return l'Ã©tat de cuisson d'un ingrÃ©dient
	 */
	public Etat getEtat() {
		return this.etat;
	}

	/**
	 * Retourne l'Ã©tat de transformation d'un ingrÃ©dient
	 * @return true si l'ingrÃ©dient est dÃ©coupÃ©, false sinon
	 */
	public boolean getTransformer() {
		return transformer;
	}

	/**
	 * @return le nom de l'ingrÃ©dient
	 */
	public Nom getNom() {
		return nom;
	}

	// Setteurs

	/**
	 * Permet de faire passer l'ingrÃ©dient d'un Ã©tat entier Ã  dÃ©coupÃ©
	 * @param etat
	 */
	public void setTransformer(boolean etat) {
		this.transformer = etat ;
	}

	/**
	 * Permet de changer l'Ã©tat de cuisson d'un ingrÃ©dient
	 * @param etat
	 */
	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	// MÃ©thodes

	/**
	 * @return true si l'ingrÃ©dient est un steak
	 */
	public boolean isSteak () {
		return (this.getNom() == Nom.STEAK_DE_BOEUF
				|| this.getNom() == Nom.STEAK_DE_POULET
				|| this.getNom() == Nom.STEAK_DE_SOJA) ;
	}

	/**
	 * @return true si l'ingrÃ©dient est dÃ©coupable
	 */
	public boolean isDecoupable () {
		return (this.getNom() == Nom.PATATE
				|| this.getNom() == Nom.TOMATE
				|| this.getNom() == Nom.OIGNON
				|| this.getNom() == Nom.SALADE) ;
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
	
}
