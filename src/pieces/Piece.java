/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pieces;

import java.awt.Color;
import javachess.Case;
import javachess.Plateau;

/**
 *
 * @author ahertel
 */
public abstract class Piece {
    private int couleur; // 0 = vide, 1 = Blanc, 2 = Noir
    private Case laCase;
    private Plateau plateau;

    
    public abstract void seDeplacer(Case destination);       
    
    public abstract void getDeplacements();
    
    public abstract boolean canPlay(Case destination, int joueurActuel);     
    

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
    
    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }
    
    public Plateau getPlateau() {
        return this.plateau;
    }
        
    
}
