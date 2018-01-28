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
public class Tour extends Piece {
    
    private boolean isPieceBetween(Case actuelle, Case destination, int joueurActuel) {
        int start = 0, end = 0;
        boolean isMovementX = true;
        if(actuelle.getPositionX() != destination.getPositionX()) {
            start = Math.min(actuelle.getPositionX(), destination.getPositionX());
            end = Math.max(actuelle.getPositionX(), destination.getPositionX());
            isMovementX = true;
        } else if(actuelle.getPositionY() != destination.getPositionY()) {
            start = Math.min(actuelle.getPositionY(), destination.getPositionY());
            end = Math.max(actuelle.getPositionY(), destination.getPositionY());
            isMovementX = false;
        }
        
        if(!actuelle.isEmpty()) {
            Plateau plateau = actuelle.getPlateau(); // On récupère le plateau pour get d'autres cases
            boolean caseWithPieceFound = false;
            // On va parcourir l'ensemble du "carré" représenté par les 4 points cardinaux ci-dessus
            for(int i = start; i<=end; i++) {
                Case c = null;
                if(isMovementX) c = plateau.getCase(i, actuelle.getPositionY());
                if(!isMovementX) c = plateau.getCase(actuelle.getPositionX(), i);
                
                if(!c.isEmpty() && !c.equals(actuelle) && !c.equals(destination)) {
                    caseWithPieceFound = true;
                }
            }
            return caseWithPieceFound;
        } else {
            return false;
        }
    }
    
    @Override
    public boolean canPlay(Case destination, int joueurActuel) {
        Case caseActuelle = this.getCase();  // On récupère la case actuelle
        
        // Si on essaye de se déplacer sur nous même, on empêche le déplacement
        if(caseActuelle.equals(destination)) return false;
        
        // Si la différence en X est la même qu'en Y c'est qu'on est en diagonale
        if((caseActuelle.getPositionX() == destination.getPositionX() && caseActuelle.getPositionY() != destination.getPositionY())
        || (caseActuelle.getPositionY() == destination.getPositionY() && caseActuelle.getPositionX() != destination.getPositionX())) {
            // Si on essaye d'aller sur une pièce (et donc de manger)
            if(!destination.isEmpty()) {
                // Si la case de destination n'est pas nous même, on peut manger
                if(destination.getUnePiece().getCouleur() != caseActuelle.getUnePiece().getCouleur()) {
                    // On ne peut pas manger et donc se déplacer s'il y a une pièce au milieu
                    return !isPieceBetween(caseActuelle, destination, joueurActuel);
                } else {
                    // Sinon, si on essayait de manger une pièce à nous, emêche le déplacement
                    return false;
                }
            } else {
                // Si la case est vide, on vérifie juste qu'il n'y a rien au milieu
                return !isPieceBetween(caseActuelle, destination, joueurActuel);
            }
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "Tour";
    }
}
