/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pieces;

import java.util.ArrayList;
import javachess.Case;
import javachess.Plateau;

/**
 *
 * @author ahertel
 */
public class Roi extends Piece{
    private boolean alreadyPlay = false;    

    
    @Override
    public void seDeplacer(Case destination) {
        alreadyPlay = true;
        this.setCase(destination);
    }

    @Override
    public boolean canPlay(Case destination, int joueurActuel) {
        Case caseActuelle = this.getCase();  // On récupère la case actuelle
        int differencePosY = Math.abs(caseActuelle.getPositionY() - destination.getPositionY()); // On récupère la distance peu importe le sens
        int differencePosX = Math.abs(caseActuelle.getPositionX() - destination.getPositionX()); // On récupère la distance peu importe le sens
                
        if((differencePosX <= 1)&&(differencePosY <= 1)) {
            if(destination.isEmpty()){
                return true;
            } else {
                if(destination.getUnePiece().getCouleur() != joueurActuel) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }
    
    public boolean isThreatenedCase(ArrayList cases, int joueurActuel){
        return true;
    }
    
    public boolean canRoque(Case actuelle, Case destination) {
        int start = 0, end = 0;
        Plateau plateau = this.getCase().getPlateau();
        boolean isMovementX = true, caseWithPieceFound = false;
        
        if(actuelle.getPositionX() != destination.getPositionX()) {
            start = Math.min(actuelle.getPositionX(), destination.getPositionX());
            end = Math.max(actuelle.getPositionX(), destination.getPositionX());
            isMovementX = true;
        } else if(actuelle.getPositionY() != destination.getPositionY()) {
            isMovementX = false;
        }
        
        if(!alreadyPlay){
            if(isMovementX){
                int differenceX = Math.abs(actuelle.getPositionX() - destination.getPositionX());
                differenceX--;
                
                if(differenceX <= 3 && differenceX >= 2 ){
                    for(int i = start; i <= end; i++){
                        Case c = plateau.getCase(i, actuelle.getPositionY());
                            if(!c.isEmpty() && !c.equals(actuelle) && !c.equals(destination)) {
                                caseWithPieceFound = true;
                            }
                    }
                    return !caseWithPieceFound;                    
                }
                else return false;
            }
            else return false;
        }
        else return false;
    }

    @Override
    public String toString() {
        return "Roi";
    }
}
