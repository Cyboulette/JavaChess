package javachess;

import java.util.ArrayList;
import pieces.*;

/**
 *
 * @author Quentin
 */
public class Controller {
    private Model modele;
    
    public Controller(Model modele) {
        this.modele = modele;
    }
    
    public Case getCase(int x, int y) {
        return this.modele.getPlateau().getCase(x, y);
    }
    
    public void play(Piece piece, Case destination) {
        boolean canPlay = true;
        if(!destination.isEmpty() && destination.getUnePiece().getCouleur() == piece.getCouleur()) {
            canPlay = false;
        }
        
        canPlay = piece.canPlay(destination, this.getJoueurActuel());
        
        if(piece instanceof Roi) {
            Roi roi = (Roi) piece;
            if(!destination.isEmpty() && destination.getUnePiece() instanceof Tour) {
                if(piece.getCouleur() == destination.getUnePiece().getCouleur()) {
                    if(roi.canRoque(piece.getCase(), destination)) {
                        this.modele.doRoque(piece.getCase(), destination);
                        canPlay = false;
                    }
                }
            }
        }
        
        if(piece instanceof Pion){
            if(destination.isEmpty()){
                Case caseActuelle = piece.getCase();
                Case caseGauche = null;
                Case caseDroite = null;
                if(caseActuelle.getPositionX() != 0) caseGauche = this.getCase(caseActuelle.getPositionX()-1, caseActuelle.getPositionY());
                if(caseActuelle.getPositionX() != 7) caseDroite = this.getCase(caseActuelle.getPositionX()+1, caseActuelle.getPositionY());
                
                if(caseGauche != null && !caseGauche.isEmpty()) {
                    if(caseGauche.getUnePiece() instanceof Pion) {
                        Pion pion = (Pion) caseGauche.getUnePiece();
                        if(pion.canPrisePassant(caseGauche, destination, this.getJoueurActuel())) {
                            this.modele.prisePassant(caseGauche, destination, piece);
                            canPlay = false;
                        }
                    }
                }
                
                if(caseDroite != null && !caseDroite.isEmpty()) {
                    if(caseDroite.getUnePiece() instanceof Pion) {
                        Pion pion = (Pion) caseDroite.getUnePiece();
                        if(pion.canPrisePassant(caseDroite, destination, this.getJoueurActuel())) {
                            this.modele.prisePassant(caseDroite, destination, piece);
                            canPlay = false;
                        }
                    }
                }
            }
        }
        
        if(canPlay) {
            this.modele.play(piece, destination);
        }
    }
    
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
    
    public String getEtatJeu() {
        String retour = "";
        ArrayList<Piece> piecesBlanc = new ArrayList<Piece>();
        ArrayList<Piece> piecesNoir = new ArrayList<Piece>();
        ArrayList<Case> lesCases = this.modele.getPlateau().getLesCases();
        for(Case c : lesCases) {
            if(!c.isEmpty()) {
                Piece p = c.getUnePiece();
                if(p.getCouleur() == 1 && !piecesBlanc.contains(p)) piecesBlanc.add(p);
                if(p.getCouleur() == 2 && !piecesNoir.contains(p)) piecesNoir.add(p);
            }
        }
        System.out.println(piecesBlanc.size()+" pièces blanches totales");
        System.out.println(piecesNoir.size()+" pièces noires totales");
        int nbPionsBlancs = 0;
        int nbPionsNoirs = 0;
        int nbToursBlancs = 0;
        int nbToursNoirs = 0;
        int nbReineBlanc = 0;
        int nbReineNoir = 0;
        int nbRoiBlanc = 0;
        int nbRoiNoir = 0;
        int nbFousBlancs = 0;
        int nbFousNoirs = 0;
        int nbCavaliersBlancs = 0;
        int nbCavaliersNoirs = 0;
        
        for(Piece p : piecesBlanc) {
            if(p instanceof Pion) nbPionsBlancs++;
            if(p instanceof Cavalier) nbCavaliersBlancs++;
            if(p instanceof Fou) nbFousBlancs++;
            if(p instanceof Roi) nbRoiBlanc++;
            if(p instanceof Reine) nbReineBlanc++;
            if(p instanceof Tour) nbToursBlancs++;
        }
        
        for(Piece p : piecesNoir) {
            if(p instanceof Pion) nbPionsNoirs++;
            if(p instanceof Cavalier) nbCavaliersNoirs++;
            if(p instanceof Fou) nbFousNoirs++;
            if(p instanceof Roi) nbRoiNoir++;
            if(p instanceof Reine) nbReineNoir++;
            if(p instanceof Tour) nbToursNoirs++;
        }
        retour = "Blancs = \nPions : "+nbPionsBlancs+"\nCavaliers : "+nbCavaliersBlancs+"\nFous : "+nbFousBlancs+"\nTours : "+nbToursBlancs+"\nRoi : "+nbRoiBlanc+"\nReine : "+nbReineBlanc;
        retour += "\n\nNoirs = \nPions : "+nbPionsNoirs+"\nCavaliers : "+nbCavaliersNoirs+"\nFous : "+nbFousNoirs+"\nTours : "+nbToursNoirs+"\nRoi : "+nbRoiNoir+"\nReine : "+nbReineNoir;
        
        return retour;
    }
}
