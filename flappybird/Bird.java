package evolution.flappybird;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bird {

    private Circle bird;
    private double positionY;
    private double velocityY;
    private int positionX;
    private Circle eye;
    private Pane gamePane;

    public Bird(Pane myPane){
        this.gamePane = myPane;
        this.positionX = Constants.BIRDINITIAL_X;
        this.positionY = Constants.BIRDINITIAL_Y;
        this.velocityY = 0;
        this.setUpBird();
    }

    private void setUpBird() {
        this.bird = new Circle(Constants.BIRDINITIAL_X, Constants.BIRDINITIAL_Y, Constants.BIRD_RADIUS, Color.ORANGE);
        this.eye = new Circle(Constants.BIRDINITIAL_X + Constants.EYEOFFSET_X, Constants.BIRDINITIAL_Y -
                Constants.EYEOFFSET_Y, Constants.EYE_RADIUS, Color.YELLOW);
        this.gamePane.getChildren().add(this.bird);
        this.gamePane.getChildren().add(this.eye);
    }

    public void addGravity(){
        this.velocityY = this.velocityY + Constants.GRAVITY * Constants.DURATION;
        this.positionY = this.positionY + this.velocityY * Constants.DURATION;
        if(this.positionY < Constants.BIRD_RADIUS){
            this.positionY = Constants.BIRD_RADIUS;
        }
        this.bird.setCenterY(this.positionY);
        this.eye.setCenterY(this.positionY);
    }

    public void removeBird(){
        this.gamePane.getChildren().removeAll(this.bird, this.eye);
    }

    public double getY(){
        return this.positionY;
    }

    public int getX(){
        return this.positionX;
    }

    public boolean checkIntersection(Bounds bound){
        return this.bird.intersects(bound);
    }

    public double getVelocityY(){
        return this.velocityY;
    }

    public void resetVelocity(){
        this.velocityY = Constants.REBOUND_VELOCITY;
    }

    public void setColor(Color color){
        this.bird.setFill(color);
    }

    public void setOpacity(){
        this.bird.setOpacity(0.5);
        this.eye.setOpacity(0.5);
    }
}
