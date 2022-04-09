/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2021
 * Instructor: Prof. Chris Dancy
 *
 * Name: Jane Yan
 * Section: 01
 * Date: 4/30/21
 * Time: 8:19 PM
 *
 * Project: csci205SP21FinalProject * Package: main.menu * Class: LevelController
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
 * Class used to open the different levels and access the help menu or exit from them.
 */
public class LevelController {

    @FXML
    private Text levelTitle;

    @FXML
    private Button quad;

    @FXML
    private Button library;

    @FXML
    private Button exit;

    @FXML
    private Button help;

    /**
     * primary stage
     */
    private Stage theStage;

    /**
     * Navigate to the Malesardi level scene on the same stage
     */
    @FXML
    private void handleSelectQuad(){
        try {
            theStage = (Stage)levelTitle.getScene().getWindow();
            Parent quadLevel = FXMLLoader.load(getClass().getClassLoader().getResource("MalesardiLevel.fxml"));
            theStage.setScene(new Scene(quadLevel));
            theStage.show();
            quadLevel.requestFocus();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigate to the library level map
     */
    @FXML
    private void handleSelectLibrary(){
        try {
            theStage = (Stage)levelTitle.getScene().getWindow();
            Parent libLevel = FXMLLoader.load(getClass().getClassLoader().getResource("LibraryLevel.fxml"));
            theStage.setScene(new Scene(libLevel));
            theStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Exit the application
     */
    @FXML
    private void handleExitAction() {
        try{
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Display the help menu -- instructions on how to play
     */
    @FXML
    private void handleHelpAction() {
        try {
            theStage = (Stage)levelTitle.getScene().getWindow();
            Parent helpMenu = FXMLLoader.load(getClass().getClassLoader().getResource("HelpMenu.fxml"));
            theStage.setScene(new Scene(helpMenu));
            theStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}