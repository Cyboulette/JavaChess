package javachess;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pieces.Cavalier;
import pieces.Fou;
import pieces.Piece;
import pieces.Reine;
import pieces.Tour;

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
    private ArrayList<ImageView> casesImages;
    
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
                    if(!caseC.isEmpty() && caseC.getUnePiece().getCouleur() == this.getController().getJoueurActuel()) {
                            isPieceClicked = true;
                            pieceClicked = caseC.getUnePiece();
                            
                            if(source.getUserData() != null) {
                                String imageName = source.getUserData().toString();
                                imageName = imageName.substring(0, imageName.length()-1);
                                source.setEffect(new ImageInput(new Image("hover/"+imageName+".png")));
                            }
                            
                            imageClicked = source.getImage();
                            
                            ArrayList<Case> deplacements = controller.getDeplacements(pieceClicked);
                            if(deplacements.size() > 0) {
                                for(Case c : deplacements) {
                                    ImageView caseImgView = this.getCaseImageViewAtCoord(c.getPositionX(), c.getPositionY());
                                    if(caseImgView.getUserData().equals("blanche")) {
                                        caseImgView.setEffect(new ImageInput(new Image("case_verte_1.png")));
                                    } else if(caseImgView.getUserData().equals("noire")) {
                                        caseImgView.setEffect(new ImageInput(new Image("case_verte_2.png")));
                                    }
                                }
                            }
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
                resetCasesEffect();
            } else {
                // On clique sur une autre pièce que nous, on essaie de jouer (et de manger ?)
                Piece savePieceClicked = pieceClicked;
                int joueur = this.controller.getJoueurActuel();
                boolean havePlayed = this.controller.play(pieceClicked, caseC);
                if(havePlayed && this.controller.needToPromote(savePieceClicked, caseC, joueur)) {
                    try {
                        // On charge la vue de promotion de pièce
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PromotionPion.fxml"));     
                        Parent root = (Parent)fxmlLoader.load(); 
                        // On récupère le controlleur du pion
                        PromotionPionController pionController = fxmlLoader.<PromotionPionController>getController();
                        pionController.setJoueurActuel(joueur); // On met le joueur actuel
                        pionController.updateImages(); // On met à jour les images
                        pionController.setParentController(this); // On passe le controlleur parent
                        
                        Stage stage = new Stage();
                        stage.setTitle("Promotion d'un pion");
                        Scene scene = new Scene(root, 350, 200);
                        stage.setScene(scene);

                        pionController.setStage(stage); // On passe le stage/scene vue courante
                        pionController.setCurrentPiece(pieceClicked); // La pièce cliquée
                        pionController.setCase(caseC); // La case cliquée
                        pionController.setImageViewSource(source); // Et l'image view source
                        
                        // On bloque le fait de fermer la fenêtre, on doit forcer l'utilisateur à choisir une nouvelle pièce
                        scene.getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
                            public void handle(WindowEvent ev) {
                                ev.consume();
                            }
                        });
                        
                        // On utilise une modale qui va bloquer le jeu en attendant qu'elle soit fermée
                        stage.initModality(Modality.APPLICATION_MODAL);
                        // On force l'affichage de la modale
                        stage.showAndWait();
                    } catch(IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
    
    public void promotePiece(String piece, PromotionPionController pionController) {
        if(piece.equals("fou")) {
            pieceClicked = new Fou();
        } else if(piece.equals("cavalier")) {
            pieceClicked = new Cavalier();
        } else if(piece.equals("reine")) {
            pieceClicked = new Reine();
        } else if(piece.equals("tour")) {
            pieceClicked = new Tour();
        }
        
        pieceClicked.setCouleur(pionController.getJoueurActuel());
        String sJ = null;
        if(pionController.getJoueurActuel() == 1) sJ = "B";
        if(pionController.getJoueurActuel() == 2) sJ = "N";
        pionController.getImageViewSource().setImage(new Image(piece+sJ+".png"));
        this.controller.promote(pionController.getCase(), pieceClicked);
        pionController.getStage().close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.piecesImages = new ArrayList<ImageView>();
        this.casesImages = new ArrayList<ImageView>();
        initBoard();
    }
    
    public void initBoard() {
        this.isPieceClicked = false;
        this.pieceClicked = null;
        this.imageClicked = null;
        
        double currentX = 0;
        double currentY = 0;
        double compte8 = 0;
        boolean startWithBlanc = true; // On commence avec blanc = 1 true, noir = 0 false
        
        //Creating the mouse event handler 
        EventHandler<MouseEvent> eventClickPiece = (MouseEvent e) -> {
            this.onClickPiece(e);
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
            
            if(!casesImages.contains(imgView)) casesImages.add(imgView);
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
                        imgV.setPickOnBounds(true);
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        imgV.setUserData("pionN");
                        this.piecesImages.add(imgV);
                        contentCases.getChildren().add(imgV);
                    }
                    if(y==6){
                        ImageView imgV = new ImageView(pionB);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(264);
                        imgV.setPickOnBounds(true);
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        imgV.setUserData("pionB");
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
                        imgV.setPickOnBounds(true);
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        imgV.setUserData("tourN");
                        this.piecesImages.add(imgV);
                        contentCases.getChildren().add(imgV);
                    }
                    if((x==0||x==7) && (y==7)){
                        ImageView imgV = new ImageView(tourB);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        imgV.setPickOnBounds(true);
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        imgV.setUserData("tourB");
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
                        imgV.setPickOnBounds(true);
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        imgV.setUserData("cavalierB");
                        this.piecesImages.add(imgV);
                        contentCases.getChildren().add(imgV);
                    }
                    if((x==1||x==6) && (y==0)){
                        ImageView imgV = new ImageView(cavalierN);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        imgV.setPickOnBounds(true);
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        imgV.setUserData("cavalierN");
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
                        imgV.setPickOnBounds(true);
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        imgV.setUserData("fouB");
                        this.piecesImages.add(imgV);
                        contentCases.getChildren().add(imgV);
                    }
                    if((x==2||x==5) && (y==0)){
                        ImageView imgV = new ImageView(fouN);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        imgV.setPickOnBounds(true);
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        imgV.setUserData("fouN");
                        this.piecesImages.add(imgV);
                        contentCases.getChildren().add(imgV);
                    }
                }
                
                if(((x==4)&&(y==0||y==7))){
                    if(y==0){
                        ImageView imgV = new ImageView(roiN);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        imgV.setPickOnBounds(true);
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        imgV.setUserData("roiN");
                        this.piecesImages.add(imgV);
                        contentCases.getChildren().add(imgV);
                    }
                    else{
                        ImageView imgV = new ImageView(roiB);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        imgV.setPickOnBounds(true);
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        imgV.setUserData("roiB");
                        this.piecesImages.add(imgV);
                        contentCases.getChildren().add(imgV);
                    }
                }
                
                if(((x==3)&&(y==0||y==7))){
                    if(y==0){
                        ImageView imgV = new ImageView(reineN);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        imgV.setPickOnBounds(true);
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        imgV.setUserData("reineN");
                        this.piecesImages.add(imgV);
                        contentCases.getChildren().add(imgV);
                    }
                    else{
                        ImageView imgV = new ImageView(reineB);
                        imgV.setFitHeight(44);
                        imgV.setFitWidth(44);
                        imgV.setLayoutX(x*44);
                        imgV.setLayoutY(y*44);
                        imgV.setPickOnBounds(true);
                        imgV.addEventHandler(MouseEvent.MOUSE_CLICKED, eventClickPiece);
                        imgV.setUserData("reineB");
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
                    imgV.setPickOnBounds(true);
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
    
    private ImageView getCaseImageViewAtCoord(int x, int y) {
        x = x*44;
        y = y*44;
        ImageView imageFound = null;
        int i = 0;
        while(imageFound == null) {
            ImageView img = casesImages.get(i);
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
    
    private void resetCasesEffect() {
        for(ImageView img : casesImages) {
            img.setEffect(null);
        }
    }

    @Override
    public void avertir(Piece piece, Case source, Case destination, Boolean aMange) {
        ImageView imgSource = this.getImageViewAtCoord(source.getPositionX(), source.getPositionY());
        ImageView imgDestination = this.getImageViewAtCoord(destination.getPositionX(), destination.getPositionY());
        if(imgSource != null & imgDestination != null) {
            imgDestination.setUserData(imgSource.getUserData());
            imgSource.setUserData(null);
            imgDestination.setImage(imgSource.getImage());
            imgSource.setImage(null);
            imgSource.setEffect(null);
            resetCasesEffect();
            this.isPieceClicked = false;
            this.pieceClicked = null;
            this.imageClicked = null;
        }
        // Commenter cette ligne pour arrêter de voir l'état du jeu
        // System.out.println(this.controller.getEtatJeu());
    }
    
    @Override
    public void avertirDisparition(Piece piece, Case source, Case destination) {
        ImageView imgSource = this.getImageViewAtCoord(source.getPositionX(), source.getPositionY());
        imgSource.setImage(null);
        imgSource.setEffect(null);
        this.isPieceClicked = false;
        this.pieceClicked = null;
        this.imageClicked = null;
    }

    @Override
    public void avertirNouvellePartie() {
        this.casesImages.clear();
        this.piecesImages.clear();
        this.contentCases.getChildren().clear();
        this.initBoard();
    }

    @Override
    public void avertirFinPartie(int gagnant) {
        
    }
    
}
