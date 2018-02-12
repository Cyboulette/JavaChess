package pieces;

import java.util.ArrayList;
import javachess.Case;
import javachess.Plateau;

/**
 *
 * @author ahertel
 */
public class Reine extends Piece{
    
    private boolean isPieceBetween(Case actuelle, Case destination, int joueurActuel) {
        int startX = Math.min(actuelle.getPositionX(), destination.getPositionX());
        int startY = Math.min(actuelle.getPositionY(), destination.getPositionY());
        int endX = Math.max(actuelle.getPositionX(), destination.getPositionX());
        int endY = Math.max(actuelle.getPositionY(), destination.getPositionY());
        
        int currentX = actuelle.getPositionX();
        int currentY = actuelle.getPositionY();
        int destinationX = destination.getPositionX();
        int destinationY = destination.getPositionY();
        
        Plateau plateau = this.getCase().getPlateau();
        
        if(!actuelle.isEmpty()) {
            boolean caseWithPieceFound = false;
            for(int i = startX; i<=endX; i++) {
                for(int j = startY; j<=endY; j++) {
                    int differenceX = Math.abs(actuelle.getPositionX() - i);
                    int differenceY = Math.abs(actuelle.getPositionY() - j);
                    // Si la différence en X est la même en Y on vérifie les diagonales
                    // Si on change de X mais pas de Y ou qu'on change de Y mais pas de X on est sur une ligne
                    // On vérifie alors que la case est disponible
                    if((differenceX == differenceY) || ((currentX == destinationX && destinationY != currentY) || (currentX != destinationX && destinationY == currentY))) {
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
        Case caseActuelle = this.getCase();
        int differenceX = Math.abs(caseActuelle.getPositionX() - destination.getPositionX());
        int differenceY = Math.abs(caseActuelle.getPositionY() - destination.getPositionY());
        
        // Si on essaye de se déplacer sur nous même on bloque le déplacement
        if(destination.equals(caseActuelle)) return false;
        
        if(((caseActuelle.getPositionX() == destination.getPositionX() && caseActuelle.getPositionY() != destination.getPositionY())
        || (caseActuelle.getPositionY() == destination.getPositionY() && caseActuelle.getPositionX() != destination.getPositionX()))||(differenceX == differenceY)) {
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
        return "Reine";
    }
    
}
