/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pieces;

import java.awt.Color;
import javachess.Case;

/**
 *
 * @author ahertel
 */
public abstract class Piece {
    private int couleur;
    private Case laCase;

    
    public abstract void seDeplacer();       
    
    public abstract void getDeplacement();
    
    public abstract boolean canPlay();     
    

    /**
     * @param couleur the couleur to set
     */
    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }
    
    public int getCouleur() {
        return this.couleur;
    }
    
    public void setCase(Case laCase) {
        this.laCase = laCase;
    }
    
    public Case getCase() {
        return this.laCase;
    }
        
    
}
