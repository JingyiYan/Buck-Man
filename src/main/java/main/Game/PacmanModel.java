/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2021
 * Instructor: Prof. Chris Dancy
 *
 * Name: Nick Zhang
 * Section: 12:30
 * Date: 5/4/2021
 * Time: 1:22 AM
 *
 * Project: csci205SP21FinalProject
 * Package: main
 * Class: PacmanModel
 *
 * Description: THIS IS A DESCRIPTION Y’ALL AND I NEED TO CHANGE THIS!
 *
 * ****************************************
 */
package main.Game;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import main.ghosts.Ghost;

import java.util.*;

/**
 * models how pacman will work. Much of the actual gameplay physics is contained in this class
 */
public class PacmanModel {

    private CellValue[][] grid;
    private int gridRow;
    private int gridCol;
    private Point2D buckyLoc;
    private Direction currDirection = Direction.NONE;
    private Direction prevDirection;
    private boolean ghostEating = false;
    private boolean isWin = false;
    private final long POWERPERIOD = 10000;
    private int score = 0;
    private int dotCount = 0;
    private int livesCount = 3;

    // Ghosts and ghost homes
    public Ghost mather;
    public Ghost dancy;
    public Ghost badal;
    public Ghost bravman;
    public Point2D matherHome;
    public Point2D dancyHome;
    public Point2D badalHome;
    public Point2D bravmanHome;

    public List<Ghost> ghosts;
    private final String mapFile;
    private Point2D buckyStart;


    /**
     * Constructor for pacman model
     */
    public PacmanModel() {
        // Read the map from a txt file
        mapFile = "src/main/resources/MalesardiLevel.txt";
        try {
            initLevel(mapFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // init the ghosts
        ghosts = new ArrayList<Ghost>(){{
            add(mather);
            add(dancy);
            add(badal);
            add(bravman);
        }};
    }

    /**
     * initiates maze based on txt file
     * @param mapFile is a string of characters
     * @throws IOException
     */
    protected void initLevel(String mapFile) throws IOException {
        Path mapFilePath = Path.of(mapFile);
       System.out.println(Files.exists(mapFilePath));

        String maze = Files.readString(mapFilePath);

        // count the number of rows and cols in the string
        this.gridRow = maze.split("\n").length;
        this.gridCol = maze.split("\n")[0].split("").length;
        grid = new CellValue[gridRow][gridCol];

        // Read in maze string
        int row = 0;
        Scanner mazeRow = new Scanner(maze);
        while (mazeRow.hasNext()) {
            String mazeCol = mazeRow.nextLine();
            for (int col = 0; col < mazeCol.length(); col ++) {
                CellValue cellValue;
                switch (mazeCol.charAt(col)) {
                    case 'D':
                        cellValue = CellValue.DOT;
                        this.dotCount ++;
                        break;
                    case 'W':
                        cellValue = CellValue.WALL;
                        break;
                    case 'P':
                        cellValue = CellValue.POWERDOT;
                        this.dotCount ++;
                        break;
                    case 'A':
                        cellValue = CellValue.BADALHOME;
                        badal = new Ghost(col, row, Direction.LEFT);
                        this.badalHome = new Point2D(col, row);
                        break;
                    case 'C' :
                        cellValue = CellValue.DANCYHOME;
                        dancy = new Ghost(col, row, Direction.LEFT);
                        this.dancyHome = new Point2D(col, row);
                        break;
                    case 'J' :
                        cellValue = CellValue.BRAVMANHOME;
                        bravman = new Ghost(col, row, Direction.LEFT);
                        this.bravmanHome = new Point2D(col, row);
                        break;
                    case 'M' :
                        cellValue = CellValue.MATHERHOME;
                        mather = new Ghost(col, row, Direction.LEFT);
                        this.matherHome = new Point2D(col, row);
                        break;
                    case 'B':
                        cellValue = CellValue.BUCKYHOME;
                        this.buckyLoc = new Point2D(col, row);
                        buckyStart = buckyLoc;
                        break;
                    case 'G':
                        cellValue = CellValue.GHOSTDOOR;
                    default:
                        cellValue = CellValue.EMPTY;
                        break;
                }

                grid[row][col] = cellValue;
//                col ++;
            }
            row ++;
        }
    }

    /**
     * Start a count down timer to set ghostEating back to false
     */
    private void startGhostEatingTimer() {
        Timer timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        setGhostEating(false);
                    }
                });
            }
        };

        timer.schedule(timerTask, POWERPERIOD);
    }

    /**
     * Update the model based on where the next step of Bucky is
     */
    public void update() {
        Point2D nextBuckyLoc = nextBuckyLoc();
        if (!isGhostEating()) {
            handleGameOver();
        }
        int buckyRow = (int)nextBuckyLoc.getY();
        int buckyCol = (int)nextBuckyLoc.getX();
        switch (grid[buckyRow][buckyCol]) {
            case WALL:
                return;
            case POWERDOT:
                this.ghostEating = true;
                addPoint(50);
                this.dotCount --;
                moveBucky();
                grid[buckyRow][buckyCol] = CellValue.EMPTY;
                if (this.dotCount == 0) {
                    this.isWin = true;
                }
                startGhostEatingTimer();
                break;
            case DOT:
                addPoint(10);
                this.dotCount --;
                moveBucky();
                grid[buckyRow][buckyCol] = CellValue.EMPTY;
                if (this.dotCount == 0) {
                    this.isWin = true;
                }
                break;
            case BUCKYHOME:
            case EMPTY:
                moveBucky();
                break;
        }
    }

    /**
     * determines the location Bucky will be in in the next frame potentially
     * if it keeps moving in the current direction
     *
     * @return location of Bucky as a Point2D object
     */
    private Point2D nextBuckyLoc() {
        switch (this.currDirection) {
            case UP:
                return this.buckyLoc.add(new Point2D(0.0, -1.0));
            case DOWN:
                return this.buckyLoc.add(new Point2D(0.0, 1.0));
            case LEFT:
                return this.buckyLoc.add(new Point2D(-1.0, 0.0));
            case RIGHT:
                return this.buckyLoc.add(new Point2D(1.0, 0.0));
            case NONE:
                return this.buckyLoc;
        }
        return null;
    }

    /**
     * add point to the score
     *
     * @param point number of points added
     */
    private void addPoint(int point) {
        this.score += point;
    }

    private void moveBucky() {
        switch (this.currDirection) {
            case UP:
                this.buckyLoc = this.buckyLoc.add(new Point2D(0.0, -1.0));
                break;
            case DOWN:
                this.buckyLoc = this.buckyLoc.add(new Point2D(0.0, 1.0));
                break;
            case LEFT:
                this.buckyLoc = this.buckyLoc.add(new Point2D(-1.0, 0.0));
                break;
            case RIGHT:
                this.buckyLoc = this.buckyLoc.add(new Point2D(1.0, 0.0));
                break;
            case NONE:
                break;
        }
    }

    /**
     * For each ghost, check if it is going to hit the wall
     * if so, find a new direction and then move the ghost
     */
    public void updateGhosts() {
        for (Ghost ghost : ghosts) {
            switch (checkIntersection(ghost)) {
                case 3:
                case 4:
                    ghost.getNewDirection();
            }

            if (checkNextPos(ghost) || checkBackToStart(ghost)) {
                ghost.getNewDirection();

                while(checkNextPos(ghost)){
                    ghost.getNewDirection();
                }
                while(checkBackToStart(ghost)) {
                    ghost.getNewDirection();
                }
                ghost.setDirection(ghost.getDirection());
            }

            ghost.move();
        }
    }

    /**
     * Checks the next position of the ghost in the grid
     * @param ghost - the ghost object to be checked
     * @return true -- next position is a wall; false otherwise
     */
    public boolean checkNextPos(Ghost ghost) {
        int nextY = ghost.getNextY();
        int nextX = ghost.getNextX();
        CellValue ghostVal = grid[nextY][nextX];
        return ghostVal == CellValue.WALL;
    }

    /**
     * @param ghost is a ghost object
     * @return cntr where there is an intersection if the ghost has left the birth place
     */
    public int checkIntersection(Ghost ghost) {
        int currY = ghost.getyPos();
        int currX = ghost.getxPos();
        int upX = ghost.getxPos() - 1;
        int downX = ghost.getxPos() + 1;
        int rightY = ghost.getyPos() - 1;
        int leftY = ghost.getyPos() + 1;

        List<CellValue> surr = new ArrayList<CellValue>() {{
            add(grid[currY][upX]);
            add(grid[currY][downX]);
            add(grid[leftY][currX]);
            add(grid[rightY][currX]);
        }};
        int cntr = 0;
        for (CellValue val: surr) {
            if(val != CellValue.WALL) {
                cntr ++;
            }
        }
        return cntr;
    }

    /**
     * checks if the ghost has left the birth place
     * @param ghost
     * @return
     */
    private boolean leftBirthPlace(Ghost ghost) {
        // check if the ghosts left birth place
        int currY = ghost.getyPos();
        int currX = ghost.getxPos();

        if (currY == 16 && currX >= 33) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * if the ghost is head back to the birth place, change its direction
     * @param ghost
     * @return
     */
    private boolean checkBackToStart(Ghost ghost) {
        int nextY = ghost.getNextY();
        int nextX = ghost.getNextX();

        Direction currDir = ghost.getDirection();

        if (currDir == Direction.RIGHT && nextX == 33 && nextY == 16) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * When Buck encounters a ghost, the lives counter - 1
     */
    public void handleGameOver() {
        for (Ghost ghost: ghosts) {
            if (ghost.getxPos() == buckyLoc.getX() && ghost.getyPos() == buckyLoc.getY()) {
                if (livesCount > 0) {
                    livesCount--;
                    resetAllSprites();
                }
            }
        }
    }

    /**
     * When life - 1, reset Bucky and all ghosts to original post
     */
    public void resetAllSprites() {
        buckyLoc = buckyStart;
        mather.setxPos((int) matherHome.getX());
        mather.setyPos((int) matherHome.getY());
        badal.setyPos((int) badalHome.getY());
        badal.setxPos((int) badalHome.getX());
        dancy.setxPos((int) dancyHome.getX());
        dancy.setyPos((int) dancyHome.getY());
        bravman.setyPos((int) bravmanHome.getY());
        bravman.setxPos((int) bravmanHome.getX());
    }

    // Getters and Setters
    // Getters
    public CellValue[][] getGrid() {
        return grid;
    }

    public int getGridRow() {
        return gridRow;
    }

    public int getGridCol() {
        return gridCol;
    }

    public CellValue getCellVal(int row, int Col) {
        return getGrid()[row][Col];
    }

    public Point2D getBuckyLoc() { return buckyLoc; }

    public int getScore() {
        return score;
    }

    public boolean isGhostEating() {
        return ghostEating;
    }

    public boolean isWin() {
        return isWin;
    }

    // setters
    public void setCurrDirection(Direction currDirection) {
        this.prevDirection = currDirection;
        this.currDirection = currDirection;
    }

    public void setBuckyLoc(Point2D newLocation) {
        this.buckyLoc = newLocation;
    }

    public void setGhostEating(boolean ghostEating) {
        this.ghostEating = ghostEating;
    }

    public int getNumLives() {
        return livesCount;
    }

    public List<Ghost> getGhosts() {
        return ghosts;
    }

    /**
     * for unit tests
     * @return the ghost object
     */
    public Ghost getDancy() {
        return dancy;
    }

    public Point2D getDancyHome() {
        return dancyHome;
    }
}
