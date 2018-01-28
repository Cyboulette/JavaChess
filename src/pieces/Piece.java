/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pieces;

import java.awt.Color;
import java.util.ArrayList;
import javachess.Case;
import javachess.Plateau;

/**
 *
 * @author ahertel
 */
public abstract class Piece {
    private int couleur; // 0 = vide, 1 = Blanc, 2 = Noir
    private Case laCase;
    
    public abstract boolean canPlay(Case destination, int joueurActuel);
    
    public abstract String toString();
    
    public ArrayList<Case> getDeplacements(int joueurActuel) {
        ArrayList<Case> deplacements = new ArrayList<Case>();
        Plateau plateau = this.getCase().getPlateau();
        for(int i = 0; i<8; i++) {
            for(int j = 0; j<8; j++) {
                Case c = plateau.getCase(i, j);
                if(canPlay(c, joueurActuel)) {
                    if(!deplacements.contains(c)) {
                        deplacements.add(c);
                    }
                }
            }
        }
        
        return deplacements;
    }
    
    public void seDeplacer(Case destination) {
        this.setCase(destination);
    }

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
