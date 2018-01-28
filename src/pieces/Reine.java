package pieces;

import java.util.ArrayList;
import javachess.Case;

/**
 *
 * @author ahertel
 */
public class Reine extends Piece{

    @Override
    public void seDeplacer(Case destination) {
    }

    @Override
    public boolean canPlay(Case destination, int joueurActuel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return "Reine";
    }
    
}
