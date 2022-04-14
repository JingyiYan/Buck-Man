/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2021
 * Instructor: Prof. Chris Dancy
 *
 * Name: Nick Zhang
 * Section: 12:30
 * Date: 5/3/2021
 * Time: 12:49 PM
 *
 * Project: csci205SP21FinalProject
 * Package: main
 * Class: PacmanView
 *
 * Description: THIS IS A DESCRIPTION Y’ALL AND I NEED TO CHANGE THIS!
 *
 * ****************************************
 */
package main.Game;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.net.URISyntaxException;

/**
 * The view where the aesthetics are created
 */
public class PacmanView {

    private final PacmanModel theModel;
    private final Image wallImage;
    private final Image dotImage;
    private final Image powerDotImage;
    private final Image buckyImage;
    private final Image buckyLogo;
    private final Image quadBG;
    private Image badalImage;
    private Image bravmanImage;
    private Image dancyImage;
    private Image matherImage;
    private boolean ghostEating;
    private BooleanProperty ghostEatingProperty;

    private final static double cellSize = 20.0;
    private int gridRow;
    private int gridCol;
    private ImageView[][] cellView;
    private Pane mazePane;
    private HBox lifeCounterHBox;


    /**
     * constructor for the view
     * @param theModel is the model from the PacmanModel class
     * @throws URISyntaxException
     */
    public PacmanView(PacmanModel theModel) throws URISyntaxException {
        this.theModel = theModel;
        // read the images
        this.wallImage = new Image("wall.png");
        this.dotImage = new Image("smalldot.png");
        this.powerDotImage = new Image("whitedot.png");
        this.buckyImage = new Image("Bucky.png");
        this.buckyLogo = new Image("bucky_Logo.png");
        this.quadBG = new Image("Malesardie.png");
        this.gridRow = this.theModel.getGridRow();
        this.gridCol = this.theModel.getGridCol();
        this.cellView = new ImageView[this.gridRow][this.gridCol];
        this.ghostEating = this.theModel.isGhostEating();
        this.ghostEatingProperty = new SimpleBooleanProperty(this.ghostEating);
        loadGhosts();

    }


    /**
     * Set the imageView objects, and the images held by the imageView
     */
    protected void initGrid(Pane maze) {
        // Set the background color of the maze to black, commented out after moving to fxml
        // background is now set in the controller of the level fxml
//        this.root.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), Insets.EMPTY)));

        this.mazePane = maze;
        // set the size of the maze to be the same as the background
//        this.mazePane.setMinWidth(this.quadBG.getWidth());
//        this.mazePane.setMinHeight(this.quadBG.getHeight());
        // set the background of the gird
        BackgroundSize bgSize = new BackgroundSize(850, 500, false, false, false, false);
        BackgroundImage bgImage = new BackgroundImage(this.quadBG, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, bgSize);
        this.mazePane.setBackground(new Background(bgImage));
        // Set the imageView objects
        for (int row = 0; row < this.gridRow; row++) {
            for (int col = 0; col < this.gridCol; col++) {
                ImageView newCell = new ImageView();
                // set location of the image
                newCell.setX(cellSize * col);
                newCell.setY(cellSize * row);
                newCell.setFitHeight(cellSize);
                newCell.setFitWidth(cellSize);
                // add ImageView to the grid
                cellView[row][col] = newCell;
                maze.getChildren().add(newCell);
//                System.out.println(newCell.getX() + " " + newCell.getY());
            }
        }

        // removed after moving to fxml
//        this.root.setCenter(maze);

        // load the images according to the model
        for (int row = 0; row < this.gridRow; row++) {
            for (int column = 0; column < this.gridCol; column++) {
                CellValue value = this.theModel.getCellVal(row, column);
                if (value == CellValue.WALL) {
                    this.cellView[row][column].setImage(this.wallImage);
                } else if (value == CellValue.POWERDOT) {
                    this.cellView[row][column].setImage(this.powerDotImage);
                } else if (value == CellValue.DOT) {
                    this.cellView[row][column].setImage(this.dotImage);
                } else if (value == CellValue.BUCKYHOME) {
                    this.cellView[row][column].setImage(this.buckyImage);
                } else if (value == CellValue.BADALHOME) {
                    this.cellView[row][column].setImage(badalImage);
                } else if (value == CellValue.BRAVMANHOME) {
                    this.cellView[row][column].setImage(bravmanImage);
                } else if (value == CellValue.DANCYHOME) {
                    this.cellView[row][column].setImage(dancyImage);
                } else if (value == CellValue.MATHERHOME) {
                    this.cellView[row][column].setImage(matherImage);
                } else {
                    this.cellView[row][column].setImage(null);
                }
            }
        }
    }

    /**
     * Updates the view of the game board
     * refreshes with the timer in controller
     */
    protected void updateGird() {
        for (int row = 0; row < this.gridRow; row++) {
            for (int column = 0; column < this.gridCol; column++) {
                CellValue value = this.theModel.getCellVal(row, column);
                if (value == CellValue.WALL) {
                    this.cellView[row][column].setImage(this.wallImage);
                } else if (value == CellValue.POWERDOT) {
                    this.cellView[row][column].setImage(this.powerDotImage);
                } else if (value == CellValue.DOT) {
                    this.cellView[row][column].setImage(this.dotImage);
                } else {
                    this.cellView[row][column].setImage(null);
                }

            }
        }

        int buckyX = (int) this.theModel.getBuckyLoc().getX();
        int buckyY = (int) this.theModel.getBuckyLoc().getY();
        this.cellView[buckyY][buckyX].setImage(this.buckyImage);

        this.ghostEating = this.theModel.isGhostEating();
        this.ghostEatingProperty.set(this.ghostEating);

        this.cellView[theModel.badal.getyPos()][theModel.badal.getxPos()].setImage(badalImage);
        this.cellView[theModel.bravman.getyPos()][theModel.bravman.getxPos()].setImage(bravmanImage);
        this.cellView[theModel.mather.getyPos()][theModel.mather.getxPos()].setImage(matherImage);
        this.cellView[theModel.dancy.getyPos()][theModel.dancy.getxPos()].setImage(dancyImage);
    }


//    Removed after moving to fxml
//    public BorderPane getRoot() {
//        return root;
//    }

    public int getGridRow() {
        return gridRow;
    }

    public int getGridCol() {
        return gridCol;
    }

    public ImageView[][] getCellView() {
        return cellView;
    }

    public static double getCellSize() {
        return cellSize;
    }

    /**
     * loads the ghosts into the game
     * @throws URISyntaxException
     */
    public void loadGhosts() throws URISyntaxException {
        File blueGhostFile = new File("src/main/resources/ghosts/blueghost.gif");
        File badalFile = new File("src/main/resources/ghosts/badal_sprite.png");
        File matherFile = new File("src/main/resources/ghosts/mather_sprite.png");
        File bravmanFile = new File("src/main/resources/ghosts/bravman_sprite.png");
        File dancyFile = new File("src/main/resources/ghosts/dancy_sprite.png");

        this.ghostEatingProperty.addListener((observable, oldValue, newValue) ->{
            System.out.println("ghost eating");
            if (!newValue) {
                dancyImage = new Image(dancyFile.toURI().toString(), 50, 50, false, false);
                badalImage = new Image(badalFile.toURI().toString(), 20, 20, false, false);
                matherImage = new Image(matherFile.toURI().toString(), 50, 50, false, false);
                bravmanImage = new Image(bravmanFile.toURI().toString(), 50, 50, false, false);
            }
            else {
                dancyImage = new Image(blueGhostFile.toURI().toString(), 50, 50, false, false);
                badalImage = new Image(blueGhostFile.toURI().toString(), 20, 20, false, false);
                matherImage = new Image(blueGhostFile.toURI().toString(), 50, 50, false, false);
                bravmanImage = new Image(blueGhostFile.toURI().toString(), 50, 50, false, false);
            }
        });

        dancyImage = new Image(dancyFile.toURI().toString(), 50, 50, false, false);
        badalImage = new Image(badalFile.toURI().toString(), 20, 20, false, false);
        matherImage = new Image(matherFile.toURI().toString(), 50, 50, false, false);
        bravmanImage = new Image(bravmanFile.toURI().toString(), 50, 50, false, false);

    }


}
