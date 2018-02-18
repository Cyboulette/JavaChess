package pieces;

import java.util.ArrayList;
import javachess.Case;
import javachess.Plateau;

public class Pion extends Piece{
    private boolean alreadyPlay = false;
    private boolean firstDeplacementIsTwo = false;
    
    @Override
    public void seDeplacer(Case destination) {
        alreadyPlay = true;
        this.setCase(destination);
    }
    
    // Permet de vérifier qu'il n'y a aucune pièce/pion entre là ou on veut aller et là ou on est
    private boolean isPieceBetween(Case actuelle, Case destination, int joueurActuel) {
        int startY = Math.min(actuelle.getPositionY(), destination.getPositionY());
        int endY = Math.max(actuelle.getPositionY(), destination.getPositionY());
        
        if(!actuelle.isEmpty()) {
            Plateau plateau = actuelle.getPlateau(); // On récupère le plateau pour get d'autres cases
            boolean caseWithPieceFound = false;
            for(int j = startY; j<=endY; j++) {
                if(j != actuelle.getPositionY()) {
                    Case c = plateau.getCase(actuelle.getPositionX(), j);
                    if(!c.isEmpty()) {
                        caseWithPieceFound = true;
                    }
                }
            }
            return caseWithPieceFound;
        } else {
            // Si la case où on est est vide, il y a un problème
            return false;
        }
    }
    
    // Détermine si on peut prendre en passant ou non
    public boolean canPrisePassant(Case caseGD, Case destination, int joueurActuel) {
        // Si on est blanc et qu'on descend en diagonale ou que si on est noir et qu'on descend en diagonale
        if(((joueurActuel == 1 && destination.getPositionY() == caseGD.getPositionY()-1) ||
            (joueurActuel == 2 && destination.getPositionY() == caseGD.getPositionY()+1)) && 
            destination.getPositionX() == caseGD.getPositionX()) {
            Pion pion = (Pion) caseGD.getUnePiece();
            
            // Pour les pions blancs la prise en passant ne peut se faire que sur Y = 4
            // Il faut aussi vérifier que le 1er déplacement du pion était de 2 !
            if(pion.getCouleur() == 1 && caseGD.getPositionY() == 4 && pion.firstDeplacementIsTwo) {
                return true;
            }
            
            // Pour les pions noirs la prise en passant ne peut se faire que sur Y = 3
            // Il faut aussi vérifier que le 1er déplacement du pion était de 2 !
            if(pion.getCouleur() == 2 && caseGD.getPositionY() == 3 && pion.firstDeplacementIsTwo) {
                return true;
            }
            
            // Par défaut on retourne faux
            return false;
        } else {
            return false;
        }
    }
    
    @Override
    public boolean canPlay(Case destination, int joueurActuel) {
        Case caseActuelle = this.getCase(); // On récupère la case actuelle
        int differencePos = Math.abs(caseActuelle.getPositionY() - destination.getPositionY()); // On récupère la distance peu importe le sens
        int maxX = 2; // Si on a jamais joué on peut faire 1 ou 2
        if(alreadyPlay) maxX = 1; // Si on a déjà joué, le maximum est de 1
        
        // Si la destination est vide (case vide) et qu'on change de colonne (diagonale ou autre), on empèche de jouer
        if(destination.isEmpty() && destination.getPositionX() != caseActuelle.getPositionX()) {
            return false;
        }

        // Si le joueur est 1 (blanc) et qu'on augmente son Y alors qu'il est sensé diminuer, on empêche de jouer (retour arrière)
        if(joueurActuel == 1 && destination.getPositionY() > caseActuelle.getPositionY()) {
            return false;
        }
        
        // Si le joueur est 2 (noir) et qu'on diminue son Y alors qu'il est sensé augmenter, on empêche de jouer (retour arrière)
        if(joueurActuel == 2 && destination.getPositionY() < caseActuelle.getPositionY()) {
            return false;
        }
        
        // Si jusque là on peut jouer, on peut tenter d'aller en diagonale, uniquement pour manger !!
        if(!destination.isEmpty()) { // Si il y a quelqu'un sur la case
            if(destination.getUnePiece().getCouleur() == caseActuelle.getUnePiece().getCouleur()) return false; // Si c'est un pion de notre couleur, on empêche de jouer
            int diffX = Math.abs(caseActuelle.getPositionX() - destination.getPositionX());
            if(diffX != 1) {
                return false; // Si on va sur une diagonale supérieure à 1 (donc n'importe quel x), on emêche de joueur
            }
            if(differencePos == 0) {
                return false; // Si on avance pas en Y, on empêche de jouer
            }
            if(differencePos == 2 && diffX == 1) {
                return false;
            }
        } else {
            // Si on va sur une case vide, on doit vérifier qu'il n'y a personne
            if(isPieceBetween(caseActuelle, destination, joueurActuel)) return false;
        }
        
        // On retourne "true" quand on peut jouer et se déplacer de 2 ou de 1 ou qu'on a mangé.
        return differencePos <= maxX;
    }
    
    @Override
    public String toString() {
        return "Pion";
    }
    
    public boolean getFirstDeplacementIsTwo() {
        return this.firstDeplacementIsTwo;
    }
    
    public void setFirstDeplacementIsTwo(boolean bool) {
        this.firstDeplacementIsTwo = bool;
    }
    
    public boolean getAlreadyPlay() {
        return this.alreadyPlay;
    }
}
