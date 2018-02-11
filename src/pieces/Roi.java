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
    public boolean canPlay(Case destination, int joueurActuel) {
        Case caseActuelle = this.getCase();  // On récupère la case actuelle
        int differencePosY = Math.abs(caseActuelle.getPositionY() - destination.getPositionY()); // On récupère la distance peu importe le sens
        int differencePosX = Math.abs(caseActuelle.getPositionX() - destination.getPositionX()); // On récupère la distance peu importe le sens
                
        if((differencePosX <= 1)&&(differencePosY <= 1))
            {
            if(destination.isEmpty()){
                return true;
            }
            else{
                if(destination.getUnePiece().getCouleur() != joueurActuel){
                    return true;
                }
                else
                    return false;
                }            
            }
            else{
                return false;
                }            
    }
    
    public boolean isThreatenedCase(ArrayList cases, int joueurActuel){
        return true;
    }
    @Override
    public String toString() {
        return "Roi";
    }
}
