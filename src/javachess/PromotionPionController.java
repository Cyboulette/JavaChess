package javachess;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pieces.Piece;

public class PromotionPionController implements Initializable {

    @FXML
    private ImageView fou;
    
    @FXML
    private ImageView tour;
    
    @FXML
    private ImageView reine;
    
    @FXML
    private ImageView cavalier;
    
    private int joueurActuel;
    private VueGraphiqueController parentController;
    private Stage stage;
    private Piece pieceClicked;
    private Case caseC;
    private ImageView imageViewSource;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // On a rien a faire de spécial pour cette vue
    }
    
    public void onMouseEntered(MouseEvent e) {
        // Quand on "hover" sur une pièce, on la passe en "orange" avec un effet
        if(e.getTarget() instanceof ImageView) {
            ImageView imgv = (ImageView) e.getTarget();
            String imageName = imgv.getUserData().toString();
            imageName = imageName.substring(0, imageName.length()-1);
            imgv.setEffect(new ImageInput(new Image("hover/"+imageName+".png")));
        }
    }
    
    public void onMouseExited(MouseEvent e) {
        // Quand on quitte le hover, on retire l'effet
        if(e.getTarget() instanceof ImageView) {
            ImageView imgv = (ImageView) e.getTarget();
            imgv.setEffect(null);
        }
    }
    
    // Quand on clique sur la pièce choisie pour la promotion
    public void onPieceClicked(MouseEvent e) {
        if(e.getTarget() instanceof ImageView) {
            // On l'indique au controleur graphique parent
            this.parentController.promotePiece(((ImageView) e.getTarget()).getId(), this);
        }
    }
    
    // On met à jour les images en fonction de si c'est une pièce blanche ou noire qu'on doit promettre
    public void updateImages() {
        if(this.joueurActuel == 1) {
            fou.setImage(new Image("fouB.png"));
            fou.setUserData("fouB");
            tour.setImage(new Image("tourB.png"));
            tour.setUserData("tourB");
            reine.setImage(new Image("reineB.png"));
            reine.setUserData("reineB");
            cavalier.setImage(new Image("cavalierB.png"));
            cavalier.setUserData("cavalierB");
        } else if(this.joueurActuel == 2) {
            fou.setImage(new Image("fouN.png"));
            fou.setUserData("fouN");
            tour.setImage(new Image("tourN.png"));
            tour.setUserData("tourN");
            reine.setImage(new Image("reineN.png"));
            reine.setUserData("reineN");
            cavalier.setImage(new Image("cavalierN.png"));
            cavalier.setUserData("cavalierN");
        }
    }
    
    public void setJoueurActuel(int joueur) {
        this.joueurActuel = joueur;
    }
    
    public int getJoueurActuel() {
        return this.joueurActuel;
    }
    
    public void setParentController(VueGraphiqueController controller) {
        this.parentController = controller;
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public Stage getStage() {
        return this.stage;
    }
    
    public void setCurrentPiece(Piece pieceClicked) {
        this.pieceClicked = pieceClicked;
    }
    
    public void setCase(Case caseC) {
        this.caseC = caseC;
    }
    
    public Case getCase() {
        return this.caseC;
    }
    
    public void setImageViewSource(ImageView source) {
        this.imageViewSource = source;
    }
    
    public ImageView getImageViewSource() {
        return this.imageViewSource;
    }
}
