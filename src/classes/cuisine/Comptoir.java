package classes.cuisine;

import classes.cuisine.materiel.Assiette;

/**
 * Comptoir qui accueille jusqu'à 3 clients et leur commande
 * @version 2.0
 * @author Maïa DA SILVA
 *
 * @version 3.0
 * @author ???
 */
public class Comptoir {

	// Constante de classes

	private static final int TAILLE_COMPTOIR = 3 ;

    // Variables de classes

	/**
	 * tableau des clients présents au comptoir
	 */
	private Client[]emplacementClientDansComptoire;

	/**
	 * tableau des emplacements du comptoir correspondant à chaque client, reçoit des assiettes
	 */
	private Assiette[]emplacementAssietteDansComptoire;


	/**
     * Constructeur
     */
    public Comptoir () {
        this.emplacementClientDansComptoire = new Client[TAILLE_COMPTOIR] ;
        this.emplacementAssietteDansComptoire = new Assiette[TAILLE_COMPTOIR];
    }

    //Getteur

	/**
	 * @return le tableau de clients au comptoir
	 */
	public Client[] getEmplacementClientDansComptoir() {
		return emplacementClientDansComptoire;
	}

	/**
	 * @return le tableau des emplacements du comptoir
	 */
    public Assiette[] getEmplacementAssietteDansComptoir() {
		return emplacementAssietteDansComptoire;
	}

	// Méthodes

	/**
     * Ajouter un client au comptoir, par définition il n'est pas servit lorsqu'il arrive
     * @param client
     * @return true si le client a bien été ajouté
     */
    public boolean ajouterClient(Client client, int emplacement) {
    	this.emplacementClientDansComptoire[emplacement]=client;
        client.debutTimerClient();
        return true ;
    }

	/**
	 * Permet de retirer un client du comptoir
	 * @param emplacement
	 * @return true si le client a bien été retiré
	 */
  	public boolean retirerClient (int emplacement) {
  		this.emplacementClientDansComptoire[emplacement]=null;
  		return true ;
	}

	/**
	 * Permet de vérifier si une assiette est presente dans emplacement donné
	 * @param i qui correspond à l'emplecement à vérifier
	 * @return true si l'emplacement est occupé par une assiette
	 */
	public boolean checkerSiUneAssietteEstPresenteDansEmplacementDuClient(int i) {
		if(emplacementAssietteDansComptoire[i]==null) {
			return false;
		}
		else {
			return true;
		}
		
	}

	/**
	 * Permet de vérifier si un client est présent à une place du comptoir
	 * @param i qui correspond à l'emplacement à vérifier
	 * @return true si l'emplacement est occupé par un client
	 */
	public boolean checkerSiUnClientEstAssisDansUnEmplacement(int i) {
		if(emplacementClientDansComptoire[i]==null) {
			return false;
		}
		else {
			return true;
		}
	}
}
