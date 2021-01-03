package classes;

import java.util.HashMap;

/**
 * Commentaire de documentation de la classe Joueur
 * @version 2.0
 * @author Maïa DA SILVA
 */
public class Joueur {

    // Variables de classe

    /**
     * argent gagné par le joueur au total (évolue au fur et à mesure des parties)
     */
    private int argent ;

    /**
     *  liste associant le niveau (key) et le score obtenu (value)
     */
    private HashMap<Integer,Integer> scores ;

    /**
     * Constructeur
     */
    public Joueur () {
        this.argent = 0 ;
        this.scores = new HashMap<Integer,Integer>() ;
    }
    
    public void ajouterArgent(int argent) {
    	this.argent += argent;
    }
    
    public String afficherArgent() {
    	return String.valueOf(argent);
    }
}
