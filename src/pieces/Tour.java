/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pieces;

import javachess.Case;

/**
 *
 * @author ahertel
 */
public class Tour extends Piece {

    @Override
    public void seDeplacer(Case destination) {
    }

    @Override
    public void getDeplacements() {
    }
   
    
    @Override
    public boolean canPlay(Case destination, int joueurActuel) {
        return false;
    }
    
}
