package javachess;

import pieces.Piece;

public class Case {

    private int positionX;
    private int positionY;
    private Piece unePiece;
    private Plateau plateau;
    
    public Case(int x, int y, Plateau plateau){
        positionX = x;
        positionY = y;
        unePiece = null;
        this.plateau = plateau;
    }
    
    public boolean isEmpty(){
        return this.unePiece == null;
    }
    
    public Piece getUnePiece(){
        return this.unePiece;
    }
    
    
    public void setUnePiece(Piece unePiece) {
        this.unePiece = unePiece;
        if(unePiece != null) {
            this.unePiece.setCase(this);
        }
    }

    /**
     * @return the positionX
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * @return the positionY
     */
    public int getPositionY() {
        return positionY;
    }
    
    public Plateau getPlateau() {
        return this.plateau;
    }
}
