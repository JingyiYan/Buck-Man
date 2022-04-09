/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2021
 * Instructor: Prof. Chris Dancy
 *
 * Name: Rachel Milio
 * Section: 12:30-1:20
 * Date: 4/19/2021
 * Time: 12:46 PM
 *
 * Project: csci205SP21FinalProject
 * Package: main
 * Class: Main
 *
 * Description:
 *
 * ****************************************
 */
package main.Game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

/**
 * main code launch the Buck-Man game using the model and view
 */
public class Main extends Application{

    PacmanModel theModel;
    PacmanView theView;

    /**
     * launches the application
     * @param args are command line arguments as a set of strings
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * starts the application
     * @param primaryStage stage where scene is built
     */
    @Override
    public void start(Stage primaryStage) {
//        primaryStage.setScene(new Scene(theView.getRoot()));
//        primaryStage.setTitle("BU PacMan");
//        primaryStage.sizeToScene();
//        primaryStage.show();
    }

    /**
     * constructor for model and view
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        super.init();
        this.theModel = new PacmanModel();
        this.theView = new PacmanView(this.theModel);
    }
}
