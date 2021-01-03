package classes.cuisine;

import classes.Niveau;
import classes.cuisine.materiel.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Commentaire de documentation de la classe
 * 
 * @version 1.0
 * @author Maïa DA SILVA
 */
public class Cuisine {

	// Variables de classe

	/**
	 * liste des assiettes disponibles dans la cuisine
	 */
	private ArrayList assiettes;
	/**
	 * liste des planches à découper disponibles dans la cuisine
	 */
	private ArrayList planchesDecoupe;
	/**
	 * station d'assemblage disponible dans la cuisine
	 */
	private Assemblage assemblage;
	/**
	 * lave vaisselle disponible dans la cuisine
	 */
	private LaveVaisselle laveVaisselle;
	/**
	 * liste des friteuses à découper disponibles dans la cuisine
	 */
	private ArrayList friteuses;
	/**
	 * liste des plaques de cuisson à découper disponibles dans la cuisine
	 */
	private ArrayList plaquesCuisson;
	/**
	 * poubelle disponible dans la cuisine
	 */
	private Poubelle poubelle;
	/**
	 * stock disponible dans la cuisine
	 */
	private Stock stock;
	/**
	 * garde-manger disponible dans la cuisine
	 */
	private GardeManger gardeManger;

	/**
	 * liste des assiettes sales
	 */
	private ArrayList pileAssietteSale ;

	/**
	 * Constructeur
	 * 
	 * @param niveau
	 */
	public Cuisine(Niveau niveau) {
		this.stock = new Stock();
		this.gardeManger = new GardeManger(niveau);

		// Initialisation des listes
		this.assiettes = new ArrayList<Assiette>();
		this.planchesDecoupe = new ArrayList<Decoupe>();
		this.friteuses = new ArrayList<Friteuse>();
		this.plaquesCuisson = new ArrayList<PlaqueCuisson>();
		this.pileAssietteSale = new ArrayList<>();

		// Création des assiettes dans la quantité indiquée par le niveau
		for (int i = 0; i < niveau.getNbAssietteMax(); i++) {
			this.assiettes.add(new Assiette());
		}

		Iterator iterator = niveau.getMateriel().keySet().iterator();
		while (iterator.hasNext()) {
			Materiel materiel = (Materiel) iterator.next();

			// Création des planches à découper dans la quantité indiquée par le niveau
			if (materiel instanceof Decoupe) {
//				for (int i = 0; i < niveau.getMateriel().get(materiel); i++) {
				this.planchesDecoupe.add(new Decoupe());
//				}

				// Création de la station d'assemblage et définition de sa capacité max
				// d'accueil (selon le niveau)
			} else if (materiel instanceof Assemblage) {
				this.assemblage = new Assemblage();
				this.assemblage.setCapaciteMax(niveau.getMateriel().get(materiel));

				// Création du lave vaisselle et définition de sa capacité max d'accueil
				// (selon le niveau)
			} else if (materiel instanceof LaveVaisselle) {
				this.laveVaisselle = new LaveVaisselle();
				this.laveVaisselle.setCapaciteMax(niveau.getMateriel().get(materiel));

				// Création des friteuses dans la quantité indiquée par le niveau
			} else if (materiel instanceof Friteuse) {
//				for (int i = 0; i < niveau.getMateriel().get(materiel); i++) {
				this.friteuses.add(new Friteuse());
//				}

				// Création des plaques de cuisson dans la quantité indiquée par le niveau
			} else if (materiel instanceof PlaqueCuisson) {
//				for (int i = 0; i < niveau.getMateriel().get(materiel); i++) {
				this.plaquesCuisson.add(new PlaqueCuisson());
//				}

				// Création de la poubelle
			} else if (materiel instanceof Poubelle) {
				this.poubelle = new Poubelle();
			}
		}
	}

	// Getteur

	/**
	 * @return
	 */
	public Stock getStock() { return stock; }

	/**
	 * @return
	 */
	public ArrayList getAssiettes() {
		return assiettes;
	}

	// Méthodes

	/**
	 * Permet de ...
	 * @return ...
	 */
	public Assiette retirerAssietteDeLaCuisine() {
		Assiette a = (Assiette) assiettes.get(0);
		assiettes.remove(0);
		return a ;
	}


	public GardeManger getGardeManger() { return this.gardeManger ;
	}
}
