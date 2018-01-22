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
public class Cavalier extends Piece{

    @Override
    public void seDeplacer() {
    }

    @Override
    public void getDeplacement() {
    }

    @Override
    public boolean canPlay() {
        Case nextCase;
        
        if(this.getCouleur()==0){
            nextCase = new Case(this.getCase().getPositionX(), this.getCase().getPositionY()-2);
        }
    }
    
}
