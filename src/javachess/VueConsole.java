package javachess;

import pieces.Piece;

public class VueConsole implements Observateur {

    @Override
    public void avertir(Piece piece, Case source, Case destination, Boolean aMange, Piece pieceMangee) {
        String joueur = (piece.getCouleur() == 1 ? "blanc":"noir");
        System.out.print(piece+" du joueur "+joueur+" en ("+source+") se déplace en ("+destination+")");
        if(aMange) System.out.print(" et a mangé un(e) "+pieceMangee);
        System.out.print("\r\n");
    }

    @Override
    public void avertirDisparition(Piece piece, Case source, Case destination) {
        String joueur = (piece.getCouleur() == 1 ? "blanc":"noir");
        System.out.println("Le pion du joueur "+joueur+" a disparu en ("+piece.getCase()+") a cause de la prise en passant");
    }

    @Override
    public void avertirNouvellePartie() {
        System.out.println("Une nouvelle partie commence");
    }

    @Override
    public void avertirFinPartie(int gagnant) {
        System.out.println("La partie est terminée ! ...");
    }

    @Override
    public void avertirEchec(int couleur, boolean estEnEchec) {
        String joueur = null;
        if(couleur == 1) joueur = "blanc";
        if(couleur == 2) joueur = "noir";
        if(estEnEchec) {
            System.out.println("Le roi du joueur " + joueur + " est en échec !");
        }
    }
    
}
