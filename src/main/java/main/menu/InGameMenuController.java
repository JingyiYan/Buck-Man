/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2021
 * Instructor: Prof. Chris Dancy
 *
 * Name: Jane Yan
 * Section: 01
 * Date: 5/12/21
 * Time: 1:13 AM
 *
 * Project: csci205SP21FinalProject * Package: main.menu * Class: InGameMenuController
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class used to create and make functional the in-game menu
 */
public class InGameMenuController {
    @FXML
    private Text paused;
    @FXML
    private Button resume;

    @FXML
    private Button help;

    @FXML
    private Button quit;

    private static boolean isInGame;

    @FXML
    private void handleResume() {
        Stage theStage = (Stage)paused.getScene().getWindow();
        theStage.close();
    }

    @FXML
    private void handleHelp() {
        try {
            // update ingame value
            isInGame = true;

            Stage theStage = (Stage)paused.getScene().getWindow();
            Parent helpMenu = FXMLLoader.load(getClass().getClassLoader().getResource("HelpMenu.fxml"));
            theStage.setScene(new Scene(helpMenu));
            theStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleQuit() {
        System.exit(0);
    }


    /**
     * helper method
     * @return true if there is an ongoing game; false otherwise
     */
    public boolean getIsInGame() {
        return isInGame;
    }


}