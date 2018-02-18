package javachess;

import java.util.ArrayList;
import pieces.*;

public class Controller {
    private Model modele;
    
    public Controller(Model modele) {
        this.modele = modele;
    }
    
    // Retourne true si la pièce doit être promue, false sinon
    public boolean needToPromote(Piece piece, Case destination, int joueur) {
        // Si la pièce est un Pion
        if(piece instanceof Pion) {
            // Si le joueur est blanc et qu'on arrive sur la ligne du haut (0) OU
            // Si le joueur est noir et qu'on arrive sur la ligne du bas (7)
            if((joueur == 1 && destination.getPositionY() == 0) || (joueur == 2 && destination.getPositionY() == 7)) {
                return true; // On doit promettre
            } else {
                return false; // Sinon on ne promet rien
            }
        } else {
            // On ne promet pas si ce n'était pas un pion
            return false;
        }
    }
    
    public boolean play(Piece piece, Case destination) {
        boolean canPlay = true; // Par défaut on peut jouer
        boolean havePlayed = false; // Par défaut on a pas encore joué
        
        // On demande à la pièce si elle peut jouer sur la destination ou on clique
        canPlay = piece.canPlay(destination, this.getJoueurActuel());
        
        // Si on bouge notre Roi
        if(piece instanceof Roi) {
            Roi roi = (Roi) piece;
            // Et que la destination est non vide et qu'on veut se déplacer sur notre Tour
            if(!destination.isEmpty() && destination.getUnePiece() instanceof Tour) {
                // Qui est donc de la mêm ecouleur que nous
                if(piece.getCouleur() == destination.getUnePiece().getCouleur()) {
                    // ça veut dire qu'on essaye de Roquer, on demande au modèle si c'est vraiment possible
                    if(roi.canRoque(piece.getCase(), destination)) {
                        // Si ça l'est, on effectue le roque
                        this.modele.doRoque(piece.getCase(), destination);
                        havePlayed = true; // On a joué
                        canPlay = false; // Mais on ne peut pas réellement "jouer"
                    }
                }
            }
        }
        
        // Si on bouge un de nos Pions
        if(piece instanceof Pion){
            // Que notre destination est vide (c'est une case vide)
            if(destination.isEmpty()){
                Case caseActuelle = piece.getCase();
                Case caseGauche = null;
                Case caseDroite = null;
                // On récupère alors la case à gauche de nous et à droite de nous en retirant les bords du plateau.
                if(caseActuelle.getPositionX() != 0) caseGauche = this.getCase(caseActuelle.getPositionX()-1, caseActuelle.getPositionY());
                if(caseActuelle.getPositionX() != 7) caseDroite = this.getCase(caseActuelle.getPositionX()+1, caseActuelle.getPositionY());
                
                // Si la case de gauche existe et qu'elle n'est pas vide
                if(caseGauche != null && !caseGauche.isEmpty()) {
                    // Que c'est aussi un pion et qu'il est d'une couleur différente de nous
                    if(caseGauche.getUnePiece() instanceof Pion && caseGauche.getUnePiece().getCouleur() != piece.getCouleur()) {
                        Pion pion = (Pion) caseGauche.getUnePiece();
                        // ça veut dire qu'on essaye de prendre en passant, on vérifie si c'est réllement possible
                        if(pion.canPrisePassant(caseGauche, destination, this.getJoueurActuel())) {
                            // Si c'est possible on le fait au modèle
                            this.modele.prisePassant(caseGauche, destination, piece);
                            havePlayed = true; // On a joué
                            canPlay = false; // Mais on ne peut pas réellement "jouer"
                        }
                    }
                }
                
                // Pareil qu'au dessus mais pour la case à droite
                if(caseDroite != null && !caseDroite.isEmpty()) {
                    if(caseDroite.getUnePiece() instanceof Pion && caseDroite.getUnePiece().getCouleur() != piece.getCouleur()) {
                        Pion pion = (Pion) caseDroite.getUnePiece();
                        if(pion.canPrisePassant(caseDroite, destination, this.getJoueurActuel())) {
                            this.modele.prisePassant(caseDroite, destination, piece);
                            havePlayed = true;
                            canPlay = false;
                        }
                    }
                }
            }
        }
        
        // Si on peut "jouer" un mouvement "classique" (pas de Roque, pas de prisePassant)
        if(canPlay) {
            havePlayed = true; // On indique qu'on a joué
            // Si la pièce qu'on déplace est un pion
            if(piece instanceof Pion) {
                int difference = Math.abs(piece.getCase().getPositionY() - destination.getPositionY());
                // Si elle n'a jamais jouée, et qu'elle se déplace de 2, on indique que son premier déplacement est de 2
                if(!((Pion)piece).getAlreadyPlay() && difference == 2) ((Pion) piece).setFirstDeplacementIsTwo(true);
            }
            
            // On demande au modèle de jouer notre pièce sur la destination
            this.modele.play(piece, destination);
            
            // On vérifie alors si un des rois est en échec poucr nous ou pour un autre joueur
            // avertirEchecAllOBservateurs prend la couleur du roi potentiel en échec et s'il est en échec ou non.
            if(this.verifyEchecNoir(this.getJoueurActuel()) || this.verifyEchecNoir(this.getJoueurSuivant())) {
                this.modele.avertirEchecAllOservateurs(2, true);
            } else {
                this.modele.avertirEchecAllOservateurs(2, false);
            }
            if(this.verifyEchecBlanc(this.getJoueurActuel()) || this.verifyEchecBlanc(this.getJoueurSuivant())) {
                this.modele.avertirEchecAllOservateurs(1, true);
            } else {
                this.modele.avertirEchecAllOservateurs(1, false);
            }
        }
        
        // On a besoin de retourner si on a joué ou non (pour la promotion)
        return havePlayed;
    }
    
    // Permet de récupérer le roiBlanc est de vérifier s'il est en échec
    public boolean verifyEchecBlanc(int joueur) {
        Roi roiBlanc = (Roi) this.modele.getPlateau().getRoiBlanc();
        if(roiBlanc.estEnEchec(joueur)) {
            return true;
        } else {
            return false;
        }
    }
    
    // Permet de récupérer le roiNoir est de vérifier s'il est en échec
    public boolean verifyEchecNoir(int joueur) {
        Roi roiNoir = (Roi) this.modele.getPlateau().getRoiNoir();
        if(roiNoir.estEnEchec(joueur)) {
            return true;
        } else {
            return false;
        }
    }
    
    // La promotion consiste simplement à remplacer une case par une nouvelle pièce
    public void promote(Case destination, Piece piece) {
        destination.setUnePiece(piece);
    }
    
    /** GETTERS **/
    public ArrayList<Case> getDeplacements(Piece piece) {
        return piece.getDeplacements(this.getJoueurActuel());
    }
    
    public void nouvellePartie() {
        this.modele.nouvellePartie();
    }
    
    public int getJoueurActuel() {
        return this.modele.getJoueurActuel();
    }
    
    public int getJoueurSuivant() {
        return this.modele.getJoueurSuivant();
    }
    
    public Piece getRoiBlanc() {
        return this.modele.getPlateau().getRoiBlanc();
    }
    
    public Piece getRoiNoir() {
        return this.modele.getPlateau().getRoiNoir();
    }
    
    // Permet de retourner la case du plateau aux coordonnées X & Y
    public Case getCase(int x, int y) {
        return this.modele.getPlateau().getCase(x, y);
    }
}
