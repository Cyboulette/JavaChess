package javachess;

import pieces.Piece;

public class Case {

    private int positionX;
    private int positionY;
    private Piece unePiece;
    
    public Case(int x, int y){
        positionX = x;
        positionY = y;
        unePiece = null;
        
    }
    
    public boolean isEmpty(Piece uneP){
        return this.unePiece == null;
    }
    
    public Piece getUnePiece(){
        return this.unePiece;
    }

    public void setUnePiece(Piece unePiece) {
        this.unePiece = unePiece;
        this.unePiece.setCase(this);
    }
    
}
