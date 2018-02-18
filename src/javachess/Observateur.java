package javachess;

import pieces.Piece;

public interface Observateur {
    
    void avertir(Piece piece, Case source, Case destination, Boolean aMange, Piece pieceMangee);
    void avertirDisparition(Piece piece, Case source, Case destination);
    void avertirNouvellePartie();
    void avertirFinPartie(int gagnant);
    void avertirEchec(int couleur, boolean estEnEchec);
}
