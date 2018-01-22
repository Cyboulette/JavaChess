/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javachess;

import java.util.ArrayList;
import pieces.*;

/**
 *
 * @author ahertel
 */
public class Plateau {
    
    private ArrayList<Case> lesCases;
    
    public Plateau(){
        lesCases = new ArrayList<Case>();
    }
    
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
                uneCase = new Case(x, y);
                lesCases.add(uneCase);
                
                if(y==1||y==6){
                    if(y==1){
                        unP= new Pion();
                        unP.setCouleur(1);
                    }
                    if(y==6){
                        unP= new Pion();
                        unP.setCouleur(0);
                    }
                    uneCase.setUnePiece(unP);                    
                }
                if(((x==0)&&(y==0||y==7))||((x == 7)&&(y == 7||y == 0))){
                    if((x==0||x==7) && (y==0)){
                        uneT = new Tour();
                        uneT.setCouleur(1);
                    }
                    if((x==0||x==7) && (y==7)){
                        uneT = new Tour();
                        uneT.setCouleur(0);
                    }
                    uneCase.setUnePiece(uneT);                    
                }
                if(((x==1)&&(y==0||y==7))||((x==6)&&(y == 7|| y == 0))){
                    if((x==1||x==6) && (y==7)){
                        unC = new Cavalier();
                        unC.setCouleur(0);
                    }
                    if((x==1||x==6) && (y==0)){
                        unC = new Cavalier();
                        unC.setCouleur(1);
                    }
                    uneCase.setUnePiece(unC);
                }
                if(((x==2)&&(y==0||y==7))||((x==5)&&(y == 7|| y ==0))){
                    if((x==2||x==5) && (y==7)){
                        unF = new Fou();
                        unF.setCouleur(0);
                    }
                    if((x==2||x==5) && (y==0)){
                        unF = new Fou();
                        unF.setCouleur(1);
                    }
                    uneCase.setUnePiece(unF);
                }
                if(((x==3)&&(y==0||y==7))){
                        
                    if(y==0){
                        uneR = new Reine();
                        uneR.setCouleur(1);
                    }
                    else{
                        uneR = new Reine();
                        uneR.setCouleur(0);
                    }
                        
                    uneCase.setUnePiece(uneR);
                }
                if(((x==4)&&(y==0||y==7))){
                    
                    if(y==0){
                        unR = new Roi();
                        unR.setCouleur(1);
                    }
                    else{
                        unR = new Roi();
                        unR.setCouleur(0);
                    }
                    uneCase.setUnePiece(unR);
                }
                
                
            }
        }
    

    }
    
}
