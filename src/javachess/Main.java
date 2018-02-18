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

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        boolean launchVueGraphique = true;
        
        // On crée notre modèle
        Model model = new Model();
        
        // On associe le modèle à notre controller
        Controller controller = new Controller(model);
        
        if(launchVueGraphique) {
            // On charge notre vu graphique
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VueGraphique.fxml"));
            AnchorPane anchorPane = loader.load();

            // On crée notre controller de VUE
            VueGraphiqueController controllerVue = loader.getController();

            // On ajoute l'observateur de notre controller à notre vue
            controllerVue.setController(controller);
            model.register(controllerVue);

            Scene scene = new Scene(anchorPane, 340, 368);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        
        // On crée notre "vue de console"
        VueConsole vueConsole = new VueConsole();
        // On ajoute l'observateur à notre vue
        model.register(vueConsole);
    }
    
    public static void main(String[] args) {
        // On lance les observateurs (vue graphique et/ou console et/ou etc..)
        launch(args);
    }
    
}
