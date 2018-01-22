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
    public boolean canPlay(int x, int y) {
        Case caseActuelle = this.getCase();
        Case caseDeplacement = this.getPlateau().getCase(x, y);
        
        // La case est vide c'est un bon point pour pouvoir jouer dessus
        /*if(caseDeplacement.isEmpty() && caseDeplacement.getUnePiece().getCouleur() != caseActuelle.getUnePiece().getCouleur()) {
            if()
        }*/
    }
    
}
