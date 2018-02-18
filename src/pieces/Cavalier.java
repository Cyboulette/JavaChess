package pieces;

import java.util.ArrayList;
import javachess.Case;

public class Cavalier extends Piece{

    @Override
    public boolean canPlay(Case destination, int joueurActuel) {
        Case caseActuelle = this.getCase();  // On récupère la case actuelle
        int differenceX = Math.abs(caseActuelle.getPositionX() - destination.getPositionX());
        int differenceY = Math.abs(caseActuelle.getPositionY() - destination.getPositionY());
        
        // Le cavalier se déplace soit à 2x et 1y de différence soit à 1x et 2y de différence
        if((differenceX == 2 && differenceY == 1) || (differenceX == 1 && differenceY == 2)) {
            // Si là ou on veut aller c'est à nous, on emêche de se déplacer
            if(!destination.isEmpty() && destination.getUnePiece().getCouleur() == caseActuelle.getUnePiece().getCouleur()) return false;
            
            // Par défaut tout va bien
            return true;
        } else {
            // Si ce n'est pas à la bonne distance, on empêche le déplacement
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "Cavalier";
    }
}
