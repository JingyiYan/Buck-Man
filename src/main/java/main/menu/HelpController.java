/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2021
 * Instructor: Prof. Chris Dancy
 *
 * Name: Jane Yan
 * Section: 01
 * Date: 5/1/21
 * Time: 1:39 AM
 *
 * Project: csci205SP21FinalProject * Package: main.menu * Class: HelpController
 *
 * Description: THIS IS A DESCRIPTION Y’ALL AND I NEED TO CHANGE THIS! *
 * ****************************************
 */
package main.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;


/**
 * Class used to create and make functional the help menu
 */
public class HelpController {
    @FXML
    private Button back;

    private InGameMenuController igmController;
    private Stage currStage;
    private Parent prevScene;

    @FXML
    private void initialize() {
        this.back.setOnAction(this::handleBackAction);
    }

    /**
     * Navigate back to the level select menu or back to the game
     */
    @FXML
    private void handleBackAction(ActionEvent event) {
        getPrevScene();
        currStage = (Stage) back.getScene().getWindow();
        currStage.setScene(new Scene(prevScene));
        currStage.show();
    }

    /**
     * decide the previous scene so that the back button navigate to the correct one
     * @return LevelMenu if accessed help from level select
     *         In-game Menu if accessed during game
     */
    private Parent getPrevScene() {
        try {
            igmController = new InGameMenuController();
            if (igmController.getIsInGame()) {
                prevScene = FXMLLoader.load(getClass().getClassLoader().getResource("InGameMenu.fxml"));
                System.out.println(igmController.getIsInGame() + " -> true?");
            } else {
                prevScene = FXMLLoader.load(getClass().getClassLoader().getResource("LevelMenu.fxml"));
                System.out.println(igmController.getIsInGame() + " -> false?");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prevScene;
    }
}