package javachess;

import pieces.Piece;

/**
 *
 * @author qdesbin
 */
public interface Observateur {
    
    void avertir(Piece piece, Case source, Case destination, Boolean aMange);
    void avertirNouvellePartie();
    void avertirFinPartie(int gagnant);
}
