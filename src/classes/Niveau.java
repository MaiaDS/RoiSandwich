package classes;

import classes.cuisine.*;
import classes.cuisine.materiel.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Commentaire de documentation de la classe
 * 
 * @version 1.0
 * @author Maïa DA SILVA
 */
public class Niveau {

	// Variables de classe

	/**
	 * Numéro du niveau
	 */
	private int numNiveau;
	/**
	 * Score minimum nécessaire pour débloqué le niveau
	 */
	private int scoreMin;
	/**
	 * Tableau permettant de stocker le score et l'argent obtenus à l'issu du
	 * niveau
	 */
	private int[] tabScoreArgent;
	/**
	 * Liste des clients qui apparaitront au cours de la partie
	 */
	private ArrayList<Client> clients;
	/**
	 * Nombre maximum de clients qui apparaitront au cours de la partie
	 */
	private int nbMaxClients;
	/**
	 * Tableau des clients qui apparaitront au cours de la partie (en secondes)
	 */
	private int tmpsAttente;
	/**
	 * Liste des recettes disponibles dans le niveau
	 */
	private ArrayList<Recette.Noms> listeRecettes;
	/**
	 * Liste du matériel et leur nombre disponible dans le niveau
	 */
	private HashMap<Materiel, Integer> materiel;
	/**
	 * Liste des ingrédients et leur quantité disponibles dans le niveau
	 */
	private int nbIngredient;
	/**
	 * Nombre d'assiettes maximale mise à disposition du joueur dans le niveau
	 */
	private int nbAssietteMax;

	private Comptoir comptoir;
	private Cuisine cuisine;

	/**
	 * Constructeur
	 * 
	 * @param numNiveau
	 */
	public Niveau(int numNiveau) {
		this.numNiveau = numNiveau;
		this.tabScoreArgent = new int[2];

		this.comptoir = new Comptoir();
		
		this.clients = new ArrayList<Client>();
		this.materiel = new HashMap<Materiel, Integer>();

		// initialisation des capacités / quantité des matériels
		int capaciteAssemblage = 1;
		int capaciteLaveVaisselle = 1;
		int quantiteOutilsCuisson = 1;
		int quantiteDecoupe = 1;
		
		// Initialisation des recettes
		this.listeRecettes = new ArrayList<Recette.Noms>();
		this.listeRecettes.add(Recette.Noms.FRITES);
		this.listeRecettes.add(Recette.Noms.SIMPLE);

		// Réglages des niveaux

		switch (this.numNiveau) {
		// niveau 1 (par défaut)
		default:
			this.scoreMin = 0;
			this.nbAssietteMax = 40;
			this.nbMaxClients = 20;

			this.tmpsAttente = 60 ;

			// quantité des ingrédients
			nbIngredient = 9999;

			break;
		// niveau 2
		case 2:
			this.scoreMin = 300;
			this.nbAssietteMax = 25;
			this.nbMaxClients = 25;
			
			this.tmpsAttente = 45;

			nbIngredient = 50;

			// ajout d'une nouvelle recette
			this.listeRecettes.add(Recette.Noms.MAXI);

			break;
		// niveau 3
		case 3:
			this.scoreMin = 600;
			this.nbAssietteMax = 15;
			this.nbMaxClients = 30;
			
			this.tmpsAttente = 30;

			// quantité des ingrédients
			nbIngredient = 50;

			// ajout d'une nouvelle recette
			this.listeRecettes.add(Recette.Noms.MENU);
		}

		// définition d'outils associés à leur quantité
		this.materiel.put(new Decoupe(), quantiteDecoupe);
		this.materiel.put(new Friteuse(), quantiteOutilsCuisson);
		this.materiel.put(new PlaqueCuisson(), quantiteOutilsCuisson);
		this.materiel.put(new Poubelle(), 1);
		// définition de la station d'assemblage associée à sa capacité maximum
		this.materiel.put(new Assemblage(), capaciteAssemblage);

		// définition du lave vaisselle associé à sa capacité maximum
		this.materiel.put(new LaveVaisselle(), capaciteLaveVaisselle);
		
		//créer la liste des clients en fonction des recettes du niveau
		this.creerClients();
		
		this.cuisine = new Cuisine(this);
	}

	// Getteurs

	/**
	 * @return la liste des clients du niveau
	 */
	public ArrayList<Client> getClients() {
		return clients;
	}

	/**
	 * @return le comptoir du niveau
	 */
	public Comptoir getComptoir() {
		return comptoir;
	}

	/**
	 * @return la cuisine du niveau
	 */
	public Cuisine getCuisine() {
		return cuisine;
	}

	/**
	 * @return le nombre max des ingrédients dans le niveau
	 */
	public int getNbIngredient() {
		return nbIngredient;
	}

	/**
	 * @return liste du matériel et leur quantité disponibles dans le niveau
	 */
	public HashMap<Materiel, Integer> getMateriel() {
		return materiel;
	}

	/**
	 * @return nombre d'assiettes max disponbles dans le niveau
	 */
	public int getNbAssietteMax() {
		return nbAssietteMax;
	}

	/**
	 * @return le tableau contenant le score et l'argent obtenus par le joueur
	 */
	public int[] getTabScoreArgent() {
		return tabScoreArgent;
	}
	
	/**
	 * @return le num�ro du niveau
	 */
	public int getNumNiveau() {
		return numNiveau;
	}
	


	// Setteurs


	/**
	 * Permet de stocker le score et l'argent obtenus dans le tableau à la fin de
	 * la partie
	 * 
	 * @param score correspond à la somme des scores au fur et à mesure de la partie
	 * @param argent correspond à la somme de l'argent gagné au fur et à mesure de la partie
	 */
	
	//modif mickael
	public void setScoreArgent(int score, int argent) {
		this.tabScoreArgent[0] = this.tabScoreArgent[0] +score;		
		this.tabScoreArgent[1] = this.tabScoreArgent[1] +argent;
	}

	// Méthodes

	/**
	 * @return true s'il ne reste plus de clients dans la liste
	 */
	public boolean checkerSiListeDesClientsEstVide() {
		if(clients.size()==0) {
		return true;
		}
		return false;
		
	}

	/**
	 * @return true si la liste des clients à bien été initialisée
	 */
	public boolean creerClients() {
		// Pour chaque client
		for (int a = 0; a < this.nbMaxClients; a++) {
			// Pour chaque, je pioche une recette aléatoire
			int recetteAleatoire = (int)(Math.random() * listeRecettes.size());
			// System.out.print(recetteAleatoire);
			// définition de la commande propre au client
			int viandeAleatoire = (int)(Math.random() * Recette.Steaks.values().length);
			// System.out.println(viandeAleatoire);
			Recette recette = new Recette(this.listeRecettes.get(recetteAleatoire), Recette.Steaks.values()[viandeAleatoire]);
			int lower = tmpsAttente;
			int higher = (int) (tmpsAttente*1.50);
			int tmpsAttenteRandom = (int)(Math.random() * (higher-lower)) + lower;
			// System.out.println("random = " + tmpsAttenteRandom);
			// création du client
			this.clients.add(new Client(tmpsAttenteRandom, recette));
		}
		return true;
	}
}
