package javachess;

import pieces.Piece;

/**
 *
 * @author Quentin
 */
public class Controller {
    private Model modele;
    
    public Controller(Model modele) {
        this.modele = modele;
    }
    
    public Case getCase(int x, int y) {
        return this.modele.getPlateau().getCase(x, y);
    }
    
    public void play(Piece piece, Case destination) {
        boolean canPlay = true;
        if(!destination.isEmpty() && destination.getUnePiece().getCouleur() == piece.getCouleur()) {
            canPlay = false;
        }
        
        canPlay = piece.canPlay(destination, this.getJoueurActuel());
        
        if(canPlay) {
            this.modele.play(piece, destination);
            //this.modele.avertirAllObservateurs(piece, destination); C'est au modèle d'avertir la vue
        } else {
            System.out.println("Vous ne pouvez pas jouer ici");
        }
    }
    
    public void nouvellePartie() {
        this.modele.nouvellePartie();
    }
    
    public int getJoueurActuel() {
        return this.modele.getJoueurActuel();
    }
    
    public int getJoueurSuivant() {
        return this.modele.getJoueurSuivant();
    }
}
