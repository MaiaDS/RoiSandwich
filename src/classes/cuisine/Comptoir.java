package classes.cuisine;

import classes.cuisine.materiel.Assiette;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Comptoir qui accueille jusqu'à 3 clients et leur commande
 * @version 2.0
 * @author Maïa DA SILVA
 */
public class Comptoir {

    // Variables de classes

    /**
     * liste représentant le comptoir
     */
//    private HashMap<Client, Assiette> comptoir ;
	
	Client[]emplacementClientDansComptoire;
	Assiette[]emplacementAssietteDansComptoire;
    
//    ArrayList<Client> comptoir = new ArrayList<Client>();
    

//    public HashMap<Client, Assiette> getComptoir() {
//		return comptoir;
//	}

	/**
     * Constructeur
     */
    public Comptoir () {
        this.emplacementClientDansComptoire = new Client[3] ;
        this.emplacementAssietteDansComptoire = new Assiette[3];
    }

    // Méthodes

    public Assiette[] getEmplacementAssietteDansComptoire() {
		return emplacementAssietteDansComptoire;
	}

	/**
     * Ajouter un client au comptoir, par définition il n'est pas servit lorsqu'il arrive
     * @param client
     * @return true si le client a bien été ajouté
     */
    public boolean ajouterClient(Client client, int emplacement) {
//        if (this.comptoir.size() >= 3) {
//            //exception
//            return false ;
//        }
////        this.comptoir.put(client,null) ;
//        this.comptoir.add(client);
    	this.emplacementClientDansComptoire[emplacement]=client;
        client.debutTimerClient();
        return true ;
    }

    /**
     * Permet de servir une assiette à un client du comptoir
     * @param client
     * @param assiette
     * @return true si le client a été servit
     */
//    public boolean servirClient (Client client, Assiette assiette) {
//        if (this.comptoir.get(client) != null ) {
//            // client déjà servit
//            return false ;
//        }
//        this.comptoir.replace(client, null, assiette) ;
//        return true ;
//    }

    /**
     * Permet de débarrasser un client du comptoir
     * @param client
     * @return true si le client a été débarrassé
     */
//    public boolean debarrasserClient (Client client) {
//        if (this.comptoir.get(client) == null ) {
//            // client pas servit
//            return false ;
//        }
//        this.comptoir.replace(client,null) ;
//        return true ;
//    }


    /**
     * Retirer un client du comptoir
     * @param client
     * @return true si le client a bien été retirer
     */
//    public boolean retirerClient (Client client) {
//        this.comptoir.remove(client);
//        return true ;
//    }
    
  public boolean retirerClient (int emplacement) {
  this.emplacementClientDansComptoire[emplacement]=null;
  return true ;
}

	public Client[] getEmplacementClientDansComptoire() {
		return emplacementClientDansComptoire;
	}
	
	public boolean checker_Si_Une_Assiette_Est_Presente_Dans_Emplacement_Du_Client(int i) {
		if(emplacementAssietteDansComptoire[i]==null) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	public boolean checker_Si_Un_Client_Est_Assis_Dans_Un_Emplacement(int i) {
		if(emplacementClientDansComptoire[i]==null) {
			return false;
		}
		else {
			return true;
		}
	}

	

}
