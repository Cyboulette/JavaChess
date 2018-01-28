/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pieces;

import java.util.ArrayList;
import javachess.Case;

/**
 *
 * @author ahertel
 */
public class Roi extends Piece{

    @Override
    public void seDeplacer(Case destination) {
    }

    @Override
    public boolean canPlay(Case destination, int joueurActuel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return "Roi";
    }
}
