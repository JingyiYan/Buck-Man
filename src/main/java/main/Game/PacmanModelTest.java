package main.Game;

import javafx.geometry.Point2D;
import javafx.scene.control.Cell;
import main.ghosts.Ghost;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PacmanModelTest {

    private PacmanModel theModel;
    private Point2D theBuckyLoc;
    private PacmanView theView;
    private List<Ghost> theGhosts;
    private Ghost dancyModel;

    @BeforeEach
    void setUp() {
        theModel = new PacmanModel();
        try {
            theView = new PacmanView(theModel);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        theBuckyLoc = theModel.getBuckyLoc();
        // move bucky one unit to the right
        theBuckyLoc.add(new Point2D(-1.0, 0.0));

        dancyModel = theModel.getDancy();

    }

    @Test
    void resetAllSprites() {
        dancyModel.setyPos(10);
        dancyModel.setxPos(8);
        theModel.resetAllSprites();

        int initX = (int) theModel.getDancyHome().getX();
        int initY = (int) theModel.getDancyHome().getY();
        assertEquals(dancyModel.getxPos(), initX);
        assertEquals(dancyModel.getyPos(), initY);
    }

    @Test
    void checkNextPos() {
        this.dancyModel.setDirection(Direction.UP);
        assertFalse(theModel.checkNextPos(dancyModel));
    }

    @AfterEach
    void tearDown() {
    }
}