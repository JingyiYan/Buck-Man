/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2021
 * Instructor: Prof. Chris Dancy
 *
 * Name: Nick Zhang
 * Section: 12:30
 * Date: 5/5/2021
 * Time: 8:29 PM
 *
 * Project: csci205SP21FinalProject
 * Package: main
 * Class: PacmanController
 *
 * Description: THIS IS A DESCRIPTION Y’ALL AND I NEED TO CHANGE THIS!
 *
 * ****************************************
 */
package main.Game;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * controller combines model and view
 */
public class PacmanController{

    private PacmanView theView;
    private PacmanModel theModel;
    private Timer timer;
    private final int FRAMES_PER_SECOND = 4;

    /**
     * a stage object for in-game menu
     */
    private Stage thePopup;

    /**
     * boolean for whether the game is paused or not
     */
    private static boolean isPaused;
    private static List<ImageView> lives;

    @FXML
    private BorderPane root;

    @FXML
    private Text scoreTxt;

    @FXML
    private Text highScoreTxt;

    @FXML
    private ImageView life1;

    @FXML
    private ImageView life2;

    @FXML
    private ImageView life3;

    @FXML
    private Label pauseText;

    @FXML
    private Pane mazePane;

    public PacmanController() throws URISyntaxException {
        this.theModel = new PacmanModel();
        this.theView = new PacmanView(this.theModel);
        this.isPaused = false;  // initialized as false
        lives = new ArrayList<ImageView>(){{
            add(life1);
            add(life2);
            add(life3);
        }};
    }

    /**
     * the init method is called after the constructor of the controller
     * any reference to the fx id's should be put here, not in the controller
     */
    @FXML
    public void initialize() {
        this.theView.initGrid(this.mazePane);
//        this.theView.initLifeCounter(this.lifeCounterHBox);
        this.mazePane.setOnKeyPressed(this::handleKeyPress);
        pauseText.layoutXProperty().bind(mazePane.widthProperty().subtract(pauseText.widthProperty()).divide(2));
        pauseText.layoutYProperty().bind(mazePane.heightProperty().subtract(pauseText.heightProperty()).divide(2));
        pauseText.setText("");
        startTimer();
    }

    /**
     * Updates the images on the grid for each move
     */
    protected void updateGrid() {


    }

    /**
     * starts timer
     */
    private void startTimer() {
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        update();
                    }
                });
            }
        };

        long updatePeriod = (long)(1000.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, updatePeriod);
    }

    /**
     * Update model and view
     *
     */
    private void update() {
        this.theModel.updateGhosts();
        this.theModel.update();
        this.theView.updateGird();
        this.scoreTxt.setText(Integer.toString(this.theModel.getScore()));
        if (!theModel.isGhostEating()) {
            theModel.handleGameOver();
        }
        removeLife();
        if (theModel.getNumLives() < 0) {
            this.timer.cancel();
            try {
                Stage theStage = (Stage)mazePane.getScene().getWindow();
                Parent winScreen = FXMLLoader.load(getClass().getClassLoader().getResource("game_over_screen.fxml"));
                theStage.setScene(new Scene(winScreen));
                theStage.show();
                winScreen.requestFocus();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        if (this.theModel.isWin()) {
            this.timer.cancel();
            try {
                Stage theStage = (Stage)mazePane.getScene().getWindow();
                Parent winScreen = FXMLLoader.load(getClass().getClassLoader().getResource("win_screen.fxml"));
                theStage.setScene(new Scene(winScreen));
                theStage.show();
                winScreen.requestFocus();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ties correct movement to specific key press
     * @param event is the specific key press
     */
    @FXML
    public void handleKeyPress(KeyEvent event){
        // if the game is paused, pressing any key should resume the game
        if (isPaused) {
            startTimer();
            this.pauseText.setText("");
            isPaused = false;
        }
        switch (event.getCode()) {
            case UP:
            case W:
                this.theModel.setCurrDirection(Direction.UP);
                break;
            case DOWN:
            case S:
                this.theModel.setCurrDirection(Direction.DOWN);
                break;
            case LEFT:
            case A:
                this.theModel.setCurrDirection(Direction.LEFT);
                break;
            case RIGHT:
            case D:
                this.theModel.setCurrDirection(Direction.RIGHT);
                break;
            case ESCAPE:
                isPaused = true;
                //
                pauseGame();
                initIGMenu();
                thePopup.showAndWait();
                break;
        }
    }

    /**
     * initialize the view and content of the in-game menu, ready to display
     *
     */
    public void initIGMenu() {
        try {
            Parent igm = FXMLLoader.load(getClass().getClassLoader().getResource("InGameMenu.fxml"));
            Stage currStage = (Stage) scoreTxt.getScene().getWindow();
            thePopup = new Stage();
            thePopup.setScene(new Scene(igm));
            thePopup.initModality(Modality.APPLICATION_MODAL);
            thePopup.setResizable(false);
            thePopup.initOwner(currStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * When paused, Bucky stays at its current position,
     * and the moving direction is reset to NONE
     */
    public void pauseGame() {
        this.timer.cancel();
        this.pauseText.setText("PAUSE");
        this.pauseText.toFront();
    }

    /**
     * @return false if there are lives remaining
     */
    @FXML
    public void removeLife() {
        int lifeNum = theModel.getNumLives();
        if (lifeNum == 2) {
            life3.setImage(null);
        }
        if (lifeNum == 1) {
            life2.setImage(null);
        }
        if (lifeNum == 0) {
            life1.setImage(null);
        }
    }

}

