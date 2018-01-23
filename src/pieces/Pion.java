package pieces;

import javachess.Case;

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
    
    private boolean isPieceBetween(Case actuelle, Case destination) {
        int posX1 = actuelle.getPositionX();
        int posY1 = actuelle.getPositionY();
        
        int posX2 = destination.getPositionX();
        int posY2 = destination.getPositionY();
        
        int maxY = Math.max(posY2, posY2);
        System.out.println(maxY);
        
        return false;
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
        }
        
        if(isPieceBetween(caseActuelle, destination)) return false;
        
        return differencePos <= maxX;
    }
}
