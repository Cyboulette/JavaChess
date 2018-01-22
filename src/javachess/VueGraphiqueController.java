package javachess;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author qdesbin
 */
public class VueGraphiqueController implements Initializable {
    
    @FXML
    private AnchorPane root;
    
    @FXML
    private AnchorPane contentCases;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        double currentX = 0;
        double currentY = 0;
        double compte8 = 0;
        boolean startWithBlanc = true; // Start with blanc = 1 true, noir = 0 false
        
        for(int i = 0; i < 64; i++) {
            Image caseBlanche = new Image("case_blanche.png");
            Image caseNoire = new Image("case_noire.png");
            ImageView imgView = new ImageView();
            
            if(compte8 == 8) {
                startWithBlanc = !startWithBlanc;
                compte8 = 0;
                currentX = 0;
                currentY = currentY + 44;
            }
            
            if(startWithBlanc) {
                imgView.setImage(caseNoire);
                if(i%2 == 0) imgView.setImage(caseBlanche);
            } else {
                imgView.setImage(caseBlanche);
                if(i%2 == 0) imgView.setImage(caseNoire);
            }
            
            imgView.setFitHeight(44);
            imgView.setFitWidth(44);
            
            imgView.setLayoutX(currentX);
            imgView.setLayoutY(currentY);
            
            imgView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                // Quand on clique sur une case.
                ImageView eltClicked = (ImageView) event.getSource();
                System.out.println(eltClicked.getLayoutX());
                System.out.println(eltClicked.getLayoutY());
            });
            
            contentCases.getChildren().add(imgView);
            
            currentX = currentX + 44;
            compte8++;
        }
        
        // Images pions noirs
        Image tourN = new Image("tourN.png");
        Image cavalierN = new Image("cavalierN.png");
        Image fouN = new Image("fouN.png");
        Image reineN = new Image("reineN.png");
        Image roiN = new Image("roiN.png");
        Image pionN = new Image("pionN.png");
      
        // Images pions blancs
        Image tourB = new Image("tourB.png");
        Image cavalierB = new Image("cavalierB.png");
        Image fouB = new Image("fouB.png");
        Image reineB = new Image("reineB.png");
        Image roiB = new Image("roiB.png");
        Image pionB = new Image("pionB.png");
        
        for(int x=0; x < 8; x++){
            for(int y=0; y < 8; y++){
                if(y==1||y==6){
                    if(y==1){
                        ImageView imgV = new ImageView(pionN);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(44);
                        contentCases.getChildren().add(imgV);
                    }
                    if(y==6){
                        ImageView imgV = new ImageView(pionB);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(264);
                        contentCases.getChildren().add(imgV);
                    }                   
                }
                
                if(((x==0)&&(y==0||y==7))||((x == 7)&&(y == 7||y == 0))){
                    if((x==0||x==7) && (y==0)){
                        ImageView imgV = new ImageView(tourN);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        contentCases.getChildren().add(imgV);
                    }
                    if((x==0||x==7) && (y==7)){
                        ImageView imgV = new ImageView(tourB);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        contentCases.getChildren().add(imgV);
                    }                 
                }
                
                if(((x==1)&&(y==0||y==7))||((x==6)&&(y == 7|| y == 0))){
                    if((x==1||x==6) && (y==7)){
                        ImageView imgV = new ImageView(cavalierB);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        contentCases.getChildren().add(imgV);
                    }
                    if((x==1||x==6) && (y==0)){
                        ImageView imgV = new ImageView(cavalierN);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        contentCases.getChildren().add(imgV);
                    }
                }
                
                if(((x==2)&&(y==0||y==7))||((x==5)&&(y == 7|| y ==0))){
                    if((x==2||x==5) && (y==7)){
                        ImageView imgV = new ImageView(fouB);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        contentCases.getChildren().add(imgV);
                    }
                    if((x==2||x==5) && (y==0)){
                        ImageView imgV = new ImageView(fouN);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        contentCases.getChildren().add(imgV);
                    }
                }
                
                if(((x==4)&&(y==0||y==7))){
                    if(y==0){
                        ImageView imgV = new ImageView(reineN);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        contentCases.getChildren().add(imgV);
                    }
                    else{
                        ImageView imgV = new ImageView(reineB);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        contentCases.getChildren().add(imgV);
                    }
                }
                
                if(((x==3)&&(y==0||y==7))){
                    if(y==0){
                        ImageView imgV = new ImageView(roiN);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        contentCases.getChildren().add(imgV);
                    }
                    else{
                        ImageView imgV = new ImageView(roiB);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        contentCases.getChildren().add(imgV);
                    }
                }
            }
        }
    }
    
}
