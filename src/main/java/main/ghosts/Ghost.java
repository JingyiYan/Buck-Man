/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2021
 * Instructor: Prof. Chris Dancy
 *
 * Name: Rachel Milio
 * Section: 12:30-1:20
 * Date: 5/18/2021
 * Time: 8:23 PM
 *
 * Project: csci205SP21FinalProject
 * Package: main.ghosts
 * Class: Ghost
 *
 * Description: Ghost class
 *
 * ****************************************
 */
package main.ghosts;

import javafx.geometry.Point2D;
import main.Game.Direction;

import java.util.Random;

/**
 * Class to make the ghosts and make them move
 */
public class Ghost {

    private int xPos;
    private int yPos;
    private Direction direction;
    private int nextX;
    private int nextY;

    /**
     * creates a ghost instance
     * @param x is x coordinate position
     * @param y is y coordinate position
     * @param direction is which way ghost will move
     */
    public Ghost(int x, int y, Direction direction) {
        this.xPos = x;
        this.yPos = y;
        this.direction = direction;
        determineNextPos();
    }

    /**
     * method to determine the next position of the ghost
     */
    private void determineNextPos() {
        switch (this.direction) {
            case UP:
                this.nextX = this.xPos;
                this.nextY = this.yPos - 1;
                break;
            case DOWN:
                this.nextX = this.xPos;
                this.nextY = this.yPos + 1;
                break;
            case RIGHT:
                this.nextX = this.xPos + 1;
                this.nextY = this.yPos;
                break;
            case LEFT:
                this.nextX = this.xPos - 1;
                this.nextY = this.yPos;
                break;
        }
    }


    /**
     * Choose a new direction other than its current direction
     */
    public void getNewDirection() {
        int pick = new Random().nextInt(Direction.values().length);
        Direction newDirection = Direction.values()[pick];
        if (newDirection == this.direction || newDirection == Direction.NONE) {
            getNewDirection();
        } else {
            this.direction = newDirection;
        }
        determineNextPos();
    }


    /**
     * method used for ghost movement by one block
     */
    public void move() {
        switch (this.direction) {
            case NONE:
                break;
            case UP:
                this.yPos -= 1;
                break;
            case DOWN:
                this.yPos += 1;
                break;
            case LEFT:
                this.xPos -= 1;
                break;
            case RIGHT:
                this.xPos += 1;
                break;
        }
        determineNextPos();
    }

    public int getNextX() {
        return nextX;
    }

    public int getNextY() {
        return nextY;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

}
