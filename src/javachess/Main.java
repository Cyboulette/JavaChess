package javachess;

import com.sun.java.swing.plaf.windows.resources.windows;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pieces.Piece;

/**
 *
 * @author qdesbin
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        boolean launchVueGraphique = true;
        
        Model model = new Model();
        
        Controller controller = new Controller(model);
        
        if(launchVueGraphique) {
            System.out.println("On crée une window");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VueGraphique.fxml"));
            AnchorPane anchorPane = loader.load();

            VueGraphiqueController controllerVue = loader.getController();

            // On ajoute l'observateur de notre controller à notre vue
            controllerVue.setController(controller);
            model.register(controllerVue);

            Scene scene = new Scene(anchorPane);

            stage.setScene(scene);
            stage.show();
        }
    }
    
    public static void main(String[] args) {
        // On lance la vue graphique
        launch(args);
    }
    
}
