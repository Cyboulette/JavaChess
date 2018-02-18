package javachess;

import java.util.ArrayList;
import pieces.*;

public class Plateau {
    
    private Piece roiBlanc;
    private Piece roiNoir;
    private ArrayList<Case> lesCases;
    
    public Plateau(){
        roiBlanc = null;
        roiNoir = null;
        lesCases = new ArrayList<Case>();
    }
    
    // Permet de retourner la case en position x;y
    public Case getCase(int x, int y) {
        Case caseTrouvee = null;
        int i = 0;
        try {
            while(caseTrouvee == null) {
                Case c = lesCases.get(i);
                if(c.getPositionX() == x && c.getPositionY() == y) {
                    caseTrouvee = c;
                }
                i++;
            }
        } catch(IndexOutOfBoundsException e) {
            caseTrouvee = null;
        }
        
        return caseTrouvee;
    }
    
    // permet d'init le plateau de jeu
    public void initBoarder(){
        Case uneCase; 
        Tour uneT = new Tour();
        Cavalier unC = new Cavalier();
        Pion unP = new Pion();
        Fou unF= new Fou();
        Reine uneR = new Reine();
        Roi unR = new Roi();
        
        for(int x=0; x < 8; x++){
            for(int y =0; y < 8; y++){
                uneCase = new Case(x, y, this);
                lesCases.add(uneCase);
                
                if(y==1||y==6){
                    if(y==1){
                        unP= new Pion();
                        unP.setCouleur(2);
                    }
                    if(y==6){
                        unP= new Pion();
                        unP.setCouleur(1);
                    }
                    uneCase.setUnePiece(unP);                    
                }
                if(((x==0)&&(y==0||y==7))||((x == 7)&&(y == 7||y == 0))){
                    if((x==0||x==7) && (y==0)){
                        uneT = new Tour();
                        uneT.setCouleur(2);
                    }
                    if((x==0||x==7) && (y==7)){
                        uneT = new Tour();
                        uneT.setCouleur(1);
                    }
                    uneCase.setUnePiece(uneT);                    
                }
                if(((x==1)&&(y==0||y==7))||((x==6)&&(y == 7|| y == 0))){
                    if((x==1||x==6) && (y==7)){
                        unC = new Cavalier();
                        unC.setCouleur(1);
                    }
                    if((x==1||x==6) && (y==0)){
                        unC = new Cavalier();
                        unC.setCouleur(2);
                    }
                    uneCase.setUnePiece(unC);
                }
                if(((x==2)&&(y==0||y==7))||((x==5)&&(y == 7|| y ==0))){
                    if((x==2||x==5) && (y==7)){
                        unF = new Fou();
                        unF.setCouleur(1);
                    }
                    if((x==2||x==5) && (y==0)){
                        unF = new Fou();
                        unF.setCouleur(2);
                    }
                    uneCase.setUnePiece(unF);
                }
                if(((x==3)&&(y==0||y==7))){
                        
                    if(y==0){
                        uneR = new Reine();
                        uneR.setCouleur(2);
                    }
                    else{
                        uneR = new Reine();
                        uneR.setCouleur(1);
                    }
                        
                    uneCase.setUnePiece(uneR);
                }
                if(((x==4)&&(y==0||y==7))){
                    
                    if(y==0){
                        unR = new Roi();
                        unR.setCouleur(2);
                        roiNoir = unR;
                    }
                    else{
                        unR = new Roi();
                        unR.setCouleur(1);
                        roiBlanc = unR;
                    }
                    uneCase.setUnePiece(unR);
                }
            }
        }
    }
    
    public ArrayList<Case> getLesCases() {
        return this.lesCases;
    }
    
    public Piece getRoiBlanc() {
        return this.roiBlanc;
    }
    
    public Piece getRoiNoir() {
        return this.roiNoir;
    }
    
}
