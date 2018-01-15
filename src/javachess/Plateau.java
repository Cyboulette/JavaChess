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
                
                if((x==0)&&(y==7||y==0))||((x==7)&&(y==7)){
                
                }
            }
        }
    

    }
    
}
