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
public class Pion extends Piece{
    private boolean alreadyPlay = false;
    
    

    @Override
    public void seDeplacer() {
        alreadyPlay = true;
        
    }

    @Override
    public void getDeplacement() {
    }

    @Override
    public boolean canPlay() {
        Case nextCase;
        Case nextCase1 = null;
        if(this.getCouleur()==0){
            if(alreadyPlay ==false ){
                nextCase = new Case(this.getCase().getPositionX(), this.getCase().getPositionY()-2);
                nextCase1 = new Case(this.getCase().getPositionX(), this.getCase().getPositionY()-1);
            }
            else{
                nextCase = new Case(this.getCase().getPositionX(), this.getCase().getPositionY()-1);
            }    
            if(nextCase.isEmpty()||( nextCase1 != null && (nextCase1.isEmpty()))){
                return true;
            }
            else
                return false;
        }
        else{
            if(alreadyPlay ==false ){
                nextCase = new Case(this.getCase().getPositionX(), this.getCase().getPositionY()+2);
            }
            else{
                nextCase = new Case(this.getCase().getPositionX(), this.getCase().getPositionY()+1);
            }    
            if(nextCase.isEmpty()||( nextCase1 != null && (nextCase1.isEmpty()))){
                return true;
            }
            else
                return false;
        }
    }
}
