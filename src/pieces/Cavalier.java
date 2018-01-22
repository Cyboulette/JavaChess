package pieces;

import javachess.Case;

/**
 *
 * @author ahertel
 */
public class Cavalier extends Piece{
    
    @Override
    public void seDeplacer(Case destination) {
    }

    @Override
    public void getDeplacements() {
    }

    @Override
    public boolean canPlay(Case destination, int joueurActuel) {
        Case caseActuelle = this.getCase();
        
        System.out.println(destination.getPositionX() - caseActuelle.getPositionX());
        System.out.println(destination.getPositionY() - caseActuelle.getPositionY());
        
        return false;
    }
    
}
