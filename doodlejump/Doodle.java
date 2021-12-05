package evolution.doodlejump;

import javafx.geometry.Bounds;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;

/**
 * composite shape
 */
public class Doodle {

    private Rectangle doodle;
    private double positionY;
    private double velocityY;
    private Circle leftEye;
    private Circle rightEye;
    private Line mouth;
    private Pane myPane;

    /**
     * sets up doodle/ initializes instance variables
     * associates with Pane that is set in the middle
     * @param myPane
     */
    public Doodle(Pane myPane){
        this.myPane = myPane;
        this.velocityY = 0;
        this.setUpDoodle();
        this.setUpPane();
    }

    /**
     * adds the shapes using the doodle rectangle as the base
     */
    private void setUpDoodle(){
        this.doodle = new Rectangle(Constants.DOODLE_INITIAL_X, Constants.DOODLE_INITIAL_Y, Constants.DOODLE_WIDTH,
                Constants.DOODLE_HEIGHT);
        this.doodle.setFill(Color.LIGHTGREEN);
        this.positionY = Constants.DOODLE_INITIAL_Y;
        this.leftEye = new Circle(Constants.DOODLE_INITIAL_X + 5, Constants.DOODLE_INITIAL_Y + 10,
                2, Color.BLACK);
        this.rightEye = new Circle(Constants.DOODLE_INITIAL_X + 15, Constants.DOODLE_INITIAL_Y + 10,
                2, Color.BLACK);

        this.mouth = new Line(Constants.DOODLE_INITIAL_X + 5, Constants.DOODLE_INITIAL_Y + 10,
                Constants.DOODLE_INITIAL_X + 15, Constants.DOODLE_INITIAL_Y + 10);
        this.mouth.setStrokeWidth(4);

    }

    /**
     * adds everything to pane and sets keypress
     */
    private void setUpPane(){
        myPane.getChildren().add(this.doodle);
        myPane.getChildren().add(this.leftEye);
        myPane.getChildren().add(this.rightEye);
        myPane.getChildren().add(this.mouth);
//        myPane.setFocusTraversable(true);
//        myPane.setOnKeyPressed((KeyEvent e) -> this.keyHandler(e));
    }


    /**
     * adds gravity to all the shapes
     */
    public void addGravity(){
        this.velocityY = this.velocityY + Constants.GRAVITY * Constants.DURATION;
        this.positionY = this.positionY + this.velocityY * Constants.DURATION;
        this.doodle.setY(this.positionY);
        this.rightEye.setCenterY(this.positionY + 10);
        this.leftEye.setCenterY(this.positionY + 10);
        this.mouth.setStartY(this.positionY + 20);
        this.mouth.setEndY(this.positionY + 20);
    }

    /**
     *
     * checks for intersection
     * @param bound
     * @return
     */
    public boolean checkIntersection(Bounds bound){
        return this.doodle.intersects(bound);
    }

    /**
     * getter method
     * @return
     */
    public double getVelocityY(){
        return this.velocityY;
    }

    /**
     * reset velocity used when intersects with platform bounds
     */
    public void resetVelocity(){
        this.velocityY = Constants.REBOUND_VELOCITY;
    }

    /**
     * reset velocity to bouncy, used when intersects with green platform bounds
     */
    public void bouncyResetVelocity(){
        this.velocityY = Constants.BOUNCY_REBOUND_VELOCITY;

    }

    /**
     * getter for doodle Y position
     * @return
     */
    public double getPositionY() {
        return positionY;
    }


    /**
     * mutator/setter for Y position
     * @param positionY
     */
    public void setPositionY(double positionY){
        this.positionY = positionY;
    }

    /**
     * set up left and right keys and if they move offscreen set them on the other side
     * @param e
     */

    /**
     * moves composite shape right
     */
    public void moveRight(){
        this.doodle.setX(this.doodle.getX() + Constants.MOVE);
        this.leftEye.setCenterX(this.doodle.getX()+5);
        this.rightEye.setCenterX(this.doodle.getX()+15);
        this.mouth.setStartX(this.doodle.getX()+5);
        this.mouth.setEndX(this.doodle.getX()+15);
    }

    /**
     * moves composite shape left
     */
    public void moveLeft(){
        this.doodle.setX(this.doodle.getX() - Constants.MOVE);
        this.leftEye.setCenterX(this.doodle.getX()+5);
        this.rightEye.setCenterX(this.doodle.getX()+15);
        this.mouth.setStartX(this.doodle.getX()+5);
        this.mouth.setEndX(this.doodle.getX()+15);
    }

    public Rectangle getDoodle(){
        return this.doodle;
    }
}