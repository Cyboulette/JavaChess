package javachess;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import pieces.Piece;

/**
 *
 * @author qdesbin
 */
public class VueGraphiqueController extends AbstractVueGraphiqueController implements Initializable {
    
    @FXML
    private AnchorPane root;
    
    @FXML
    private AnchorPane contentCases;
    
    // Notre controller du jeu
    private Controller controller;
    
    // Est-ce qu'une pièce est déjà cliquée ?
    private boolean isPieceClicked = false;
    private Piece pieceClicked = null;
    private Image imageClicked = null;
    
    private ArrayList<ImageView> piecesImages;
    
    public Controller getController() {
        return controller;
    }
    
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    private void onClickPiece(MouseEvent e) {
        if(!isPieceClicked) {
            if(e.getSource() instanceof ImageView) {
                ImageView source = (ImageView) e.getSource();
                Case caseC = this.controller.getCase((int) source.getLayoutX()/44, (int) source.getLayoutY()/44);
                if(caseC != null) {
                    // Si c'est au joueur de jouer
                    if(caseC.getUnePiece().getCouleur() == this.getController().getJoueurActuel()) {
                            isPieceClicked = true;
                            pieceClicked = caseC.getUnePiece();
                            imageClicked = source.getImage();
                            ColorAdjust effect = new ColorAdjust();
                            effect.setSaturation(1);
                            effect.setHue(1);
                            if(caseC.getUnePiece().getCouleur() == 1) effect.setBrightness(-0.5);
                            if(caseC.getUnePiece().getCouleur() == 2) effect.setBrightness(0.5);
                            source.setEffect(effect);
                    }
                }
            }
        } else {
            ImageView source = (ImageView) e.getSource();
            Case caseC = this.controller.getCase((int) source.getLayoutX()/44, (int) source.getLayoutY()/44);
            Piece pieceClick = caseC.getUnePiece();
            
            // Si on reclique sur nous même on annule le coup
            if(pieceClicked.equals(pieceClick)) {
                isPieceClicked = false;
                pieceClicked = null;
                source.setEffect(null);
            } else {
                // On clique sur une autre pièce que nous, on essaie de jouer (et de manger ?)
                this.controller.play(pieceClicked, caseC);
            }
        }
    }
    
    private void onClickCase(MouseEvent e) {
        if(isPieceClicked) {
            if(e.getSource() instanceof ImageView) {
                ImageView source = (ImageView) e.getSource();
                String typeCase = source.getUserData().toString();
                if(typeCase.equals("noire") || typeCase.equals("blanche")) {
                    Case caseC = this.controller.getCase((int) source.getLayoutX()/44, (int) source.getLayoutY()/44);
                    this.controller.play(pieceClicked, caseC);
                }
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.piecesImages = new ArrayList<ImageView>();
        initBoard();
    }
    
    public void initBoard() {
        this.isPieceClicked = false;
        this.pieceClicked = null;
        this.imageClicked = null;
        
        double currentX = 0;
        double currentY = 0;
        double compte8 = 0;
        boolean startWithBlanc = true; // Start with blanc = 1 true, noir = 0 false
        
        //Creating the mouse event handler 
        EventHandler<MouseEvent> eventClickPiece = (MouseEvent e) -> {
            this.onClickPiece(e);
        };
        
        //Creating the mouse event handler 
        EventHandler<MouseEvent> eventClickCase = (MouseEvent e) -> {
            this.onClickCase(e);
        };
        
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
                imgView.setUserData("noire");
                imgView.setImage(caseNoire);
                if(i%2 == 0) {
                    imgView.setUserData("blanche");
                    imgView.setImage(caseBlanche);
                }
            } else {
                imgView.setUserData("blanche");
                imgView.setImage(caseBlanche);
                if(i%2 == 0) {
                    imgView.setUserData("noire");
                    imgView.setImage(caseNoire);
                }
            }
            
            imgView.setFitHeight(44);
            imgView.setFitWidth(44);
            
            imgView.setLayoutX(currentX);
            imgView.setLayoutY(currentY);
            
            imgView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                this.onClickCase(event);
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
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        this.piecesImages.add(imgV);
                        contentCases.getChildren().add(imgV);
                    }
                    if(y==6){
                        ImageView imgV = new ImageView(pionB);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(264);
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        this.piecesImages.add(imgV);
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
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        this.piecesImages.add(imgV);
                        contentCases.getChildren().add(imgV);
                    }
                    if((x==0||x==7) && (y==7)){
                        ImageView imgV = new ImageView(tourB);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        this.piecesImages.add(imgV);
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
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        this.piecesImages.add(imgV);
                        contentCases.getChildren().add(imgV);
                    }
                    if((x==1||x==6) && (y==0)){
                        ImageView imgV = new ImageView(cavalierN);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        this.piecesImages.add(imgV);
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
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        this.piecesImages.add(imgV);
                        contentCases.getChildren().add(imgV);
                    }
                    if((x==2||x==5) && (y==0)){
                        ImageView imgV = new ImageView(fouN);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        this.piecesImages.add(imgV);
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
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        this.piecesImages.add(imgV);
                        contentCases.getChildren().add(imgV);
                    }
                    else{
                        ImageView imgV = new ImageView(reineB);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        this.piecesImages.add(imgV);
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
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        this.piecesImages.add(imgV);
                        contentCases.getChildren().add(imgV);
                    }
                    else{
                        ImageView imgV = new ImageView(roiB);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        this.piecesImages.add(imgV);
                        contentCases.getChildren().add(imgV);
                    }
                }
                
                if(y >= 2 && y<=5) {
                    ImageView imgV = new ImageView();
                    imgV.setFitHeight(44);
                    imgV.setFitWidth(44);
                    imgV.setLayoutX(x*44);
                    imgV.setLayoutY(y*44);
                    imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                    this.piecesImages.add(imgV);
                    contentCases.getChildren().add(imgV);
                }
            }
        }
    }
    
    private ImageView getImageViewAtCoord(int x, int y) {
        x = x*44;
        y = y*44;
        ImageView imageFound = null;
        int i = 0;
        while(imageFound == null) {
            ImageView img = piecesImages.get(i);
            if(img.getLayoutX() == x && img.getLayoutY() == y) {
                imageFound = img;
            }
            i++;
        }
        
        return imageFound;
    }
    
    public void nouvellePartie() {
        this.controller.nouvellePartie();
    }

    @Override
    public void avertir(Piece piece, Case source, Case destination, Boolean aMange) {
        System.out.println("On a été averti par le retour du controller/modèle");
        System.out.println("On a mangé = "+aMange);
        ImageView imgSource = this.getImageViewAtCoord(source.getPositionX(), source.getPositionY());
        ImageView imgDestination = this.getImageViewAtCoord(destination.getPositionX(), destination.getPositionY());
        if(imgSource != null & imgDestination != null) {
            imgDestination.setImage(imgSource.getImage());
            imgSource.setImage(null);
            imgSource.setEffect(null);
            this.isPieceClicked = false;
            this.pieceClicked = null;
            this.imageClicked = null;
        }
    }

    @Override
    public void avertirNouvellePartie() {
        this.piecesImages.clear();
        this.contentCases.getChildren().clear();
        this.initBoard();
    }

    @Override
    public void avertirFinPartie(int gagnant) {
        
    }
    
}
