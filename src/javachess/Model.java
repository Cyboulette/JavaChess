package javachess;

import java.util.ArrayList;

/**
 *
 * @author qdesbin
 */
public class Model {
    
    private Plateau plateau;
    private int joueurActuel; // 0 = vide | 1 = Blanc | 2 = Noir
    private ArrayList<Observateur> observateurs;
    
    public Model() {
        this.observateurs = new ArrayList<Observateur>();
        
        // Initialiser une partie automatiquement ?
    }
    
    /** GETTERS **/
    // Retourne le joueur Actuel
    public int getJoueurActuel() {
        return this.joueurActuel;
    }
    
    // Retourne le joueur suivant
    public int getJoueurSuivant() {
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
    
    /*public void play(Piece piece, int i, int j) {
        if(this.plateau.getCase(i, j).isEmpty()) { // Si la case est libre
            System.out.println("On peut jouer sur la case");
            
            // Si la pièce peut se déplacer sur ses coordonnées car son mouvement de jeu est possible
            if(piece.canPlay(i, j)) {
                this.plateau.getCase(i, j).setPiece(piece); // On joue
                avertirAllObservateurs(piece, i, j);
            } else {
                System.out.println("Can't Play here");
            }
        }
    }*/
    
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
    /*public void avertirAllObservateurs(Piece piece, int i, int j) {
        for(Observateur o : this.observateurs) {
            o.avertir(piece, i, j);
        }
    }*/
    
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
}
