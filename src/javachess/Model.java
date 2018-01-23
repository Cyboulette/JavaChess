package javachess;

import java.util.ArrayList;
import pieces.Piece;

/**
 *
 * @author qdesbin
 */
public class Model {
    
    private Plateau plateau;
    private int joueurActuel = 1; // 0 = vide | 1 = Blanc | 2 = Noir
    private ArrayList<Observateur> observateurs;
    
    public Model() {
        this.observateurs = new ArrayList<Observateur>();
        this.plateau = new Plateau();
        this.plateau.initBoarder();
        
        // Initialiser une partie automatiquement ?
    }
    
    /** GETTERS **/
    // Retourne le joueur Actuel
    protected int getJoueurActuel() {
        return this.joueurActuel;
    }
    
    // Retourne le joueur suivant
    protected int getJoueurSuivant() {
        if(this.joueurActuel == 1) {
            return 2;
        } else if(this.joueurActuel == 2) {
            return 1;
        } else {
            return 0;
        }
    }
    
    /** METHODES **/
    private void changerJoueur() {
        this.joueurActuel = this.getJoueurSuivant();
    }
    
    public void play(Piece piece, Case destination) {
        // On sait que la pièce peut se déplacer
        if(destination != null) {
            Case source = piece.getCase(); // Case de source/origine de la pièce
            Boolean aMange = false;
            
            if(!destination.isEmpty()) { // Si la case n'est libre mais qu'on peut jouer, on mange !
                aMange = true;
            }
            
            //System.out.println(destination.getPositionX()+";"+destination.getPositionY());
            
            destination.setUnePiece(piece); // On indique à la case quelle est la nouvelle pièce
            piece.seDeplacer(destination); // On indique à la pièce ou se déplacer
            this.changerJoueur();
            this.avertirAllObservateurs(piece, source, destination, aMange); // On avertit tout le monde
            source.setUnePiece(null);
        }
    }
    
    public void nouvellePartie() {
        this.plateau = new Plateau();
        this.plateau.initBoarder();
        this.joueurActuel = 1;
        this.avertirNewGameAllObservateurs();
    }
    
    /** Observateurs **/
    
    // Ajouter l'observateur
    public void register(Observateur o) {
        this.observateurs.add(o);
    }
    
    // Retirer l'observateur
    public void unRegister(Observateur o) {
        this.observateurs.remove(o);
    }
    
    // Avertir tous les observateurs d'un coup à jouer
    public void avertirAllObservateurs(Piece piece, Case source, Case destination, Boolean aMange) {
        for(Observateur o : this.observateurs) {
            o.avertir(piece, source, destination, aMange);
        }
    }
    
    // Avertir tous les observateurs d'une fin de partie (échec et math)
    public void avertirFinPartieAllObservateurs(int gagnant) {
        for(Observateur o : this.observateurs) {
            o.avertirFinPartie(gagnant);
        }
    }
    
    // Avertir TOUS les observateurs d'une nouvelle partie
    public void avertirNewGameAllObservateurs() {
        for(Observateur o : this.observateurs) {
            o.avertirNouvellePartie();
        }
    }
    
    public Plateau getPlateau() {
        return this.plateau;
    }
}
