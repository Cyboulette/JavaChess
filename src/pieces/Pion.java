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

    @Override
    public boolean canPlay(Case destination, int joueurActuel) {
        Case caseActuelle = this.getCase();
        int differencePos = Math.abs(caseActuelle.getPositionY() - destination.getPositionY());
        int maxX = 2;
        if(alreadyPlay) maxX = 1;
        
        if(destination.getPositionX() != caseActuelle.getPositionX()) {
            return false;
        }
        
        if(joueurActuel == 1 && destination.getPositionY() > caseActuelle.getPositionY()) {
            return false;
        }
        
        if(joueurActuel == 2 && destination.getPositionY() < caseActuelle.getPositionY()) {
            return false;
        }
        
        return differencePos <= maxX;
    }
}
