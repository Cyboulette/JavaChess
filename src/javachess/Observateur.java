package javachess;

/**
 *
 * @author qdesbin
 */
public interface Observateur {
    
    //void avertir(Piece piece, int i, int j);
    void avertirNouvellePartie();
    void avertirFinPartie(int gagnant);
}
