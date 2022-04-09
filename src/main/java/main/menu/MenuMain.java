package main.menu;

//import androidx.media2.player.MediaPlayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;


/**
 * Main JavaFX application code to create the application
 */
public class MenuMain extends Application {


    /**
     * constructor for application
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        super.init();
    }

    /**
     * creates the application
     * @param primaryStage is the main stage where the scene will be built
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainMenu.fxml"));
        primaryStage.setTitle("BUCK-MAN");
        primaryStage.setScene(new Scene(root));
        MenuController.addMusic();
        primaryStage.show();
    }


    /**
     * launches the application
     * @param args are command-line arguments as a set of strings
     */
    public static void main(String[] args) {
        launch(args);
    }

}
