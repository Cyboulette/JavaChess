package pieces;

import javachess.Case;
import javachess.Plateau;

/**
 *
 * @author ahertel
 */
public class Pion extends Piece{
    private boolean alreadyPlay = false;
    
    @Override
    public void seDeplacer(Case destination) {
        alreadyPlay = true;
        this.setCase(destination);
    }

    @Override
    public void getDeplacements() {
    }
    
    private boolean isPieceBetween(Case actuelle, Case destination, int joueurActuel) {
        //int posX1 = actuelle.getPositionX();
        int posY1 = actuelle.getPositionY();
        
        //int posX2 = destination.getPositionX();
        int posY2 = destination.getPositionY();
        
        int maxY = Math.max(posY1, posY2);
        int minY = Math.min(posY1, posY2);
        int maxDifference = Math.abs(maxY - minY);
        
        if(!actuelle.isEmpty()) {
            Plateau plateau = actuelle.getPlateau();
            
            int currentY = minY;
            boolean estVide = true;
            if(joueurActuel == 2) currentY++;
            for(int i = 0; i<maxDifference; i++) {
                Case c = plateau.getCase(actuelle.getPositionX(), currentY);
                if(!c.isEmpty()) {
                    estVide = false;
                }
                currentY++;
            }
            return !estVide;
        } else {
            return false;
        }
    }

    @Override
    public boolean canPlay(Case destination, int joueurActuel) {
        Case caseActuelle = this.getCase();
        int differencePos = Math.abs(caseActuelle.getPositionY() - destination.getPositionY());
        int maxX = 2;
        if(alreadyPlay) maxX = 1;
        
        if(destination.isEmpty() && destination.getPositionX() != caseActuelle.getPositionX()) {
            return false;
        }

        if(joueurActuel == 1 && destination.getPositionY() > caseActuelle.getPositionY()) {
            return false;
        }

        if(joueurActuel == 2 && destination.getPositionY() < caseActuelle.getPositionY()) {
            return false;
        }
        
        if(!destination.isEmpty() ) {
            if(destination.getUnePiece().getCouleur() == caseActuelle.getUnePiece().getCouleur()) return false;
            int diffX = Math.abs(caseActuelle.getPositionX() - destination.getPositionX());
            if(diffX != 1) {
                return false;
            }
        } else {
            if(isPieceBetween(caseActuelle, destination, joueurActuel)) return false;
        }
        
        return differencePos <= maxX;
    }
}
