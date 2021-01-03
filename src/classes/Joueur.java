package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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

    // Getteurs

    /**
     * @return argent possédé par le joueur
     */
    public int getArgent() {
        return argent;
    }

    /**
     * @param niveau
     * @return liste des scores obtenus pour un niveau donné
     */
    public ArrayList<Integer> getScore(int niveau) {
        ArrayList scoreRecherche = new ArrayList<Integer>() ;
        Iterator iterator = this.scores.keySet().iterator() ;
        while (iterator.hasNext()) {
            if (iterator.next().equals(niveau)) {
                scoreRecherche.add(this.scores.get(iterator.next())) ;
            }
        }
        return scoreRecherche ;
    }

    // Setteurs

    /**
     * Permet d'ajouter l'argent gagné par le joueur lors d'une partie à sa cagnotte
     * @param montant
     */
    public void setArgent (int montant) {
        this.argent += montant ;
    }

    /**
     * Permet d'ajouter un score à la liste des scores obtenus
     * @param niveau
     * @param score
     */
    public void setScores (int niveau, int score) {
        this.scores.put(niveau,score) ;
    }

    // Méthodes

    public boolean lancerPartie(int niveau) {
        Niveau partie = new Niveau(niveau) ;
        // lancer timer
        return true ;
    }
}
