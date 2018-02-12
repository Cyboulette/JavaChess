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

/**
 *
 * @author qdesbin
 */
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
        
    }
    
    public void onMouseEntered(MouseEvent e) {
        if(e.getTarget() instanceof ImageView) {
            ImageView imgv = (ImageView) e.getTarget();
            ColorAdjust effect = new ColorAdjust();
            effect.setSaturation(0.5);
            effect.setHue(0.2);
            imgv.setEffect(effect);
        }
    }
    
    public void onMouseExited(MouseEvent e) {
        if(e.getTarget() instanceof ImageView) {
            ImageView imgv = (ImageView) e.getTarget();
            imgv.setEffect(null);
        }
    }
    
    public void onPieceClicked(MouseEvent e) {
        if(e.getTarget() instanceof ImageView) {
            this.parentController.promotePiece(((ImageView) e.getTarget()).getId(), this);
        }
    }
    
    public void updateImages() {
        if(this.joueurActuel == 1) {
            fou.setImage(new Image("fouB.png"));
            tour.setImage(new Image("tourB.png"));
            reine.setImage(new Image("reineB.png"));
            cavalier.setImage(new Image("cavalierB.png"));
        } else if(this.joueurActuel == 2) {
            fou.setImage(new Image("fouN.png"));
            tour.setImage(new Image("tourN.png"));
            reine.setImage(new Image("reineN.png"));
            cavalier.setImage(new Image("cavalierN.png"));
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
