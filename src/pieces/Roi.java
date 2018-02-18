package pieces;

import java.util.ArrayList;
import javachess.Case;
import javachess.Plateau;

public class Roi extends Piece{
    private boolean alreadyPlay = false;
    private boolean estEnEchec = false;

    
    // On doit override le seDeplacer pour passer le alreadyPlay à true (nécessaire pour le roque)
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
    
    // Si le roi est est en échec ça veut dire qu'au moins 1 pièce du joueur adverse peut nous manger
    public boolean estEnEchec(int joueurActuel){
        Plateau plateau = this.getCase().getPlateau();
        boolean estEnEchec = false;
        
        for(int i = 0; i<8; i++) {
            for(int j = 0; j<8; j++) {
                Case c = plateau.getCase(i, j);
                if(!c.isEmpty()) {
                    if(c.getUnePiece().getCouleur() != this.getCouleur()) {
                        if(c.getUnePiece().canPlay(this.getCase(), joueurActuel)) {
                            estEnEchec = true;
                        }
                    }
                }
            }
        }
        this.estEnEchec = estEnEchec;
        return estEnEchec;
    }
    
    // Détermine si on peut roquer ou non
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
                differenceX--; // On doit faire -1 à notre différence car elle se base sur un référentiel 1 à 8 et qu'on est sur du 0 à 7
                
                // Si on essaye de faire un petit roque (2 de différence et/ou grand roque (3 de différence)
                if(differenceX <= 3 && differenceX >= 2 ){
                    for(int i = start; i <= end; i++){
                        Case c = plateau.getCase(i, actuelle.getPositionY());
                            if(!c.isEmpty() && !c.equals(actuelle) && !c.equals(destination)) {
                                // Si il y a quelqu'un entre
                                caseWithPieceFound = true;
                            }
                    }
                    // si on trouve une piece, on doit retourner false, sinon true
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
    
    public boolean getEstEnEchec() {
        return this.estEnEchec;
    }
}
