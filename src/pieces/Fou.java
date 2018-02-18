package pieces;

import java.util.ArrayList;
import javachess.Case;
import javachess.Plateau;

public class Fou extends Piece {
   
    // Permet de vérifier qu'il n'y a aucune pièce entre l à ou on est et là ou on veut aller
    // Doit tester les diagonales
    private boolean isPieceBetween(Case actuelle, Case destination, int joueurActuel) {
        int startX = Math.min(actuelle.getPositionX(), destination.getPositionX());
        int startY = Math.min(actuelle.getPositionY(), destination.getPositionY());
        int endX = Math.max(actuelle.getPositionX(), destination.getPositionX());
        int endY = Math.max(actuelle.getPositionY(), destination.getPositionY());
        
        if(!actuelle.isEmpty()) {
            Plateau plateau = actuelle.getPlateau(); // On récupère le plateau pour get d'autres cases
            boolean caseWithPieceFound = false;
            // On va parcourir l'ensemble du "carré" représenté par les 4 points cardinaux ci-dessus
            for(int i = startX; i<=endX; i++) {
                for(int j = startY; j<=endY; j++) {
                    int differenceX = Math.abs(actuelle.getPositionX() - i);
                    int differenceY = Math.abs(actuelle.getPositionY() - j);
                    // Mais on ne parcoure que les diagonales
                    if(differenceX == differenceY) {
                        Case c = plateau.getCase(i, j);
                        // Si la case n'est pas vide, que ce n'est pas nous et que ce n'est pas celle qu'on veut manger
                        // ça veut dire qu'on a trouvé une pièce qui nous empêche de bouger
                        if(!c.isEmpty() && !c.equals(actuelle) && !c.equals(destination)) {
                            caseWithPieceFound = true;
                        }
                    }
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
        int differenceX = Math.abs(caseActuelle.getPositionX() - destination.getPositionX());
        int differenceY = Math.abs(caseActuelle.getPositionY() - destination.getPositionY());
        
        // Si on essaye de se déplacer sur nous même, on empêche le déplacement
        if(caseActuelle.equals(destination)) return false;
        
        // Si la différence en X est la même qu'en Y c'est qu'on est en diagonale
        if(differenceX == differenceY) {
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
        return "Fou";
    }
}
