package evolution.flappybird;

/**
 * Bird class, used for the regular and multiplayer games and is a parent of BirdsthatLearn which is used for the
 * SmartBird Game
 */

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Constructor for A bird wrapper class that wraps a circle
 */

public class Bird {

    private Circle bird;
    private double positionY;
    private double velocityY;
    private int positionX;
    private Circle eye;
    private Pane gamePane;

    /**
     * initializes a starting position and takes in a pane to set up the bird on
     * @param myPane
     */

    public Bird(Pane myPane){
        this.gamePane = myPane;
        this.positionX = Constants.BIRDINITIAL_X;
        this.positionY = Constants.BIRDINITIAL_Y;
        this.velocityY = 0;
        this.setUpBird();
    }

    /**
     * sets up the 2 circles resembling the birds
     */

    private void setUpBird() {
        this.bird = new Circle(Constants.BIRDINITIAL_X, Constants.BIRDINITIAL_Y, Constants.BIRD_RADIUS, Color.ORANGE);
        this.eye = new Circle(Constants.BIRDINITIAL_X + Constants.EYEOFFSET_X, Constants.BIRDINITIAL_Y -
                Constants.EYEOFFSET_Y, Constants.EYE_RADIUS, Color.YELLOW);
        this.gamePane.getChildren().add(this.bird);
        this.gamePane.getChildren().add(this.eye);
    }

    /**
     * adds gravity to the birds used every timeline iteration to update their position based on the gravity psudocode
     */

    public void addGravity(){
        this.velocityY = this.velocityY + Constants.GRAVITY * Constants.DURATION;
        this.positionY = this.positionY + this.velocityY * Constants.DURATION;
        if(this.positionY < Constants.BIRD_RADIUS){
            this.positionY = Constants.BIRD_RADIUS;
        }
        this.bird.setCenterY(this.positionY);
        this.eye.setCenterY(this.positionY - Constants.EYEOFFSET_Y);
    }


    /**
     * used to remove the bird graphically
     */

    public void removeBird(){
        this.gamePane.getChildren().removeAll(this.bird, this.eye);
    }

    /**
     * getter
     * returns the y position of the circle, used to get the y distance to gapheight
     * @return
     */

    public double getY(){
        return this.positionY;
    }

    /**
     * getter
     * returns the x position of the circle, used to get the x pos to see if it passes the first pipe and to see
     * the distance to next pipe
     * @return
     */

    public int getX(){
        return this.positionX;
    }

    /**
     * used to see if the birds collides with the Pipe bounds which will be passed in
     * @param bound
     * @return
     */

    public boolean checkIntersection(Bounds bound){
        return this.bird.intersects(bound);
    }

    /**
     * used to get velocity input for neural network for each bird
     * @return
     */

    public double getVelocityY(){
        return this.velocityY;
    }

    /**
     * resets velocity which acts as the bird jumping
     */

    public void resetVelocity(){
        this.velocityY = Constants.REBOUND_VELOCITY;
    }

    /**
     * setter for the color of the birds :D
     * @param color
     */

    public void setColor(Color color){
        this.bird.setFill(color);
    }

    /**
     * setter to make the birds more visible in the 50 population of birds
     */

    public void setOpacity(){
        this.bird.setOpacity(0.5);
        this.eye.setOpacity(0.5);
    }
}
