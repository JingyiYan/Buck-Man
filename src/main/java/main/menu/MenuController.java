/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2021
 * Instructor: Prof. Chris Dancy
 *
 * Name: Jane Yan
 * Section: 01
 * Date: 4/30/21
 * Time: 7:08 PM
 *
 * Project: csci205SP21FinalProject * Package: main.menu * Class: MenuController
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

/**
 * Class to create and make functional the main menu
 */
public class MenuController {
    @FXML
    private Text title;

    @FXML
    private Button start;

    @FXML
    private Button quit;

    @FXML
    private Text score;

    static MediaPlayer mediaPlayer;

    /**
     * primary stage, different scenes for menu are swapped
     */
    private Stage theStage;

    /**
     * When the start button is pressed, navigate to level select memu
     */
    @FXML
    private void handleStartAction() {
        try {
            theStage = (Stage) title.getScene().getWindow();
            Parent level = FXMLLoader.load(getClass().getClassLoader().getResource("LevelMenu.fxml"));
            theStage.setScene(new Scene(level));
            theStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * When the quit button is pressed, quit the application
     */
    @FXML
    private void handleQuitAction() {
        try{
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * adds music to the game
     */
    public static void addMusic() {
        String path = "src/main/resources/Wizwars - 8 Bit Raceway.mp3";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

    }

    /**
     * allows audio to be muted
     */
    @FXML
    void muteAudio() {
        if (mediaPlayer.isMute()) {
            mediaPlayer.muteProperty().set(false);
        }
        else {
            mediaPlayer.muteProperty().set(true);
        }
    }



}
