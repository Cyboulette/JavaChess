package javachess;

import java.util.ArrayList;
import pieces.Piece;

public class Model {
    
    private Plateau plateau; // Contient notre plateau de jeu, c'est en réalité toutes les données
    private int joueurActuel = 1; // 0 = vide | 1 = Blanc | 2 = Noir
    private ArrayList<Observateur> observateurs; // Liste de nos observateurs de partie
    
    public Model() {
        this.observateurs = new ArrayList<Observateur>();
        this.plateau = new Plateau();
        // Quand on instancie un Model() on crée une nouvelle partie directement !
        this.plateau.initBoarder();
    }
    
    /** GETTERS **/
    // Retourne le joueur Actuel
    protected int getJoueurActuel() {
        return this.joueurActuel;
    }
    
    // Retourne le joueur suivant
    protected int getJoueurSuivant() {
        if(this.joueurActuel == 1) {
            return 2;
        } else if(this.joueurActuel == 2) {
            return 1;
        } else {
            return 0;
        }
    }
    
    /** METHODES **/
    private void changerJoueur() {
        this.joueurActuel = this.getJoueurSuivant();
    }
    
    // Méthode invoquée quand on sait qu'on peut peut jouer (on ne refait pas les vérifications de toutes les pièces ici)
    public void play(Piece piece, Case destination) {
        // On sait que la pièce peut se déplacer
        if(destination != null) {
            Piece pieceMangee = null;
            Case source = piece.getCase(); // Case de source/origine de la pièce
            Boolean aMange = false;
            
            if(!destination.isEmpty()) { // Si la case n'est libre mais qu'on peut jouer, on mange !
                aMange = true;
            }
            
            if(aMange) {
                pieceMangee = destination.getUnePiece();
            }
            
            destination.setUnePiece(piece); // On indique à la case quelle est la nouvelle pièce
            piece.seDeplacer(destination); // On indique à la pièce ou se déplacer
            this.changerJoueur();
            this.avertirAllObservateurs(piece, source, destination, aMange, pieceMangee); // On avertit tout le monde
            source.setUnePiece(null);
        }
    }
    
    // Méthode invoquée quand on sait qu'on peut faire le roque (on ne refait pas les vérifications de toutes les pièces ici)
    public void doRoque(Case caseRoi, Case caseTour) {
        Piece roi = caseRoi.getUnePiece();
        Piece tour = caseTour.getUnePiece();
        // On récupère le roi et la tour, pour déterminer si c'est grandRoque (3) et petitRoque (2) on calcule la différence
        int differenceX = Math.abs(caseRoi.getPositionX() - caseTour.getPositionX())-1;
        Case arriveeRoi = null, arriveeTour = null;
        
        if(differenceX == 2) {
            // Si la différence est de 2  = petit roque
            // Le roi se déplace à droite de 2
            // La tour se déplace à gauche de 2
            arriveeRoi = this.plateau.getCase(caseRoi.getPositionX()+2, caseRoi.getPositionY());
            arriveeTour = this.plateau.getCase(caseTour.getPositionX()-2, caseRoi.getPositionY());
        } else if(differenceX == 3) {
            // Si la différence est de 3  = grand roque
            // Le roi se déplace à gauche de 3
            // La tour se déplace à droite de 2
            arriveeRoi = this.plateau.getCase(caseRoi.getPositionX()-3, caseRoi.getPositionY());
            arriveeTour = this.plateau.getCase(caseTour.getPositionX()+2, caseRoi.getPositionY());
        }
        
        // On met le roi sur sa nouvelle case
        arriveeRoi.setUnePiece(roi);
        // On met la tour sur sa nouvelle case
        arriveeTour.setUnePiece(tour);
        
        // On les déplace
        roi.seDeplacer(arriveeRoi);
        tour.seDeplacer(arriveeTour);
        
        // On averti les observateurs 2 fois (car 2 déplacements)
        this.avertirAllObservateurs(roi, caseRoi, arriveeRoi, Boolean.FALSE, null);
        this.avertirAllObservateurs(tour, caseTour, arriveeTour, Boolean.FALSE, null);
        
        // La source se vide car on on change les positions
        caseRoi.setUnePiece(null);
        caseTour.setUnePiece(null);
        
        // C'est à l'autre joueur de jouer
        this.changerJoueur();
    }
    
    // Méthode invoquée quand on sait qu'on peut prendre en passant (on ne refait pas les vérifications de toutes les pièces ici)
    public void prisePassant(Case caseToEat, Case destination, Piece piece) {
        // On met notre piece sur notre destination, et on la déplace
        Case source = piece.getCase();
        destination.setUnePiece(piece);
        piece.seDeplacer(destination);
        // On déplace le pion "normalement"
        this.avertirAllObservateurs(piece, source, destination, Boolean.FALSE, null);
        
        // Mais on averti nos observateurs qu'on a disparu la case à "manger" par la prise en passant
        this.avertirAllObservateursDisparition(caseToEat.getUnePiece(), caseToEat, destination);
        caseToEat.setUnePiece(null);
        source.setUnePiece(null);
        this.changerJoueur();
    }
    
    public void nouvellePartie() {
        this.plateau = new Plateau();
        this.plateau.initBoarder();
        this.joueurActuel = 1;
        this.avertirNewGameAllObservateurs();
    }
    
    /** Observateurs **/
    
    // Ajouter l'observateur
    public void register(Observateur o) {
        this.observateurs.add(o);
    }
    
    // Retirer l'observateur
    public void unRegister(Observateur o) {
        this.observateurs.remove(o);
    }
    
    // Avertir tous les observateurs d'un coup à jouer
    public void avertirAllObservateurs(Piece piece, Case source, Case destination, Boolean aMange, Piece pieceMangee) {
        for(Observateur o : this.observateurs) {
            o.avertir(piece, source, destination, aMange, pieceMangee);
        }
    }
    
    // Avertir tous les observateurs qu'une pièce a disparue
    public void avertirAllObservateursDisparition(Piece piece, Case source, Case destination) {
        for(Observateur o : this.observateurs) {
            o.avertirDisparition(piece, source, destination);
        }
    }
    
    // Avertir tous les observateurs d'une fin de partie (échec et math)
    public void avertirFinPartieAllObservateurs(int gagnant) {
        for(Observateur o : this.observateurs) {
            o.avertirFinPartie(gagnant);
        }
    }
    
    // Avertir TOUS les observateurs d'une nouvelle partie
    public void avertirNewGameAllObservateurs() {
        for(Observateur o : this.observateurs) {
            o.avertirNouvellePartie();
        }
    }
    
    public void avertirEchecAllOservateurs(int couleur, boolean estEnEchec) {
        for(Observateur o : this.observateurs) {
            o.avertirEchec(couleur, estEnEchec);
        }
    }
    
    public Plateau getPlateau() {
        return this.plateau;
    }
}
