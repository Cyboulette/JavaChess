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
    
    // Est-ce qu'on peut jouer ou non
    public abstract boolean canPlay(Case destination, int joueurActuel);
    
    // Chaque pièce doit retourner son nom
    public abstract String toString();
    
    // Pour chacune des pièces la liste des déplacements est la même, il faut vérifier qu'on peut canPlay ou non.
    public ArrayList<Case> getDeplacements(int joueurActuel) {
        ArrayList<Case> deplacements = new ArrayList<Case>();
        Plateau plateau = this.getCase().getPlateau();
        try {
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
        } catch(NullPointerException e) {
            System.err.println("On essaye d'accéder à une case qui n'existe pas");
        }
        
        return deplacements;
    }
    
    // La méthode se déplacer est la même pour la majorité des pièces, sinon on peut l'override
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
