package evolution.flappybird;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bird {

    private Circle bird;
    private double positionY;
    private double velocityY;
    private double positionX;
    private Circle eye;
    private Pane myPane;

    public Bird(Pane myPane){
        this.myPane = myPane;
        this.velocityY = 0;
        this.setUpBird();
        this.setUpPane();
    }

    private void setUpBird() {
        this.bird = new Circle(Constants.BIRDINITIAL_X, Constants.BIRDINITIAL_Y, Constants.BIRD_RADIUS, Color.PURPLE);
        this.positionY = Constants.BIRDINITIAL_Y;
        this.positionX = Constants.BIRDINITIAL_X;
        this.eye = new Circle(Constants.BIRDINITIAL_X + Constants.EYEOFFSET_X, Constants.BIRDINITIAL_Y -
                Constants.EYEOFFSET_Y, Constants.EYE_RADIUS, Color.BLACK);
    }
    private void setUpPane(){
        this.myPane.getChildren().add(this.bird);
        this.myPane.getChildren().add(this.eye);
    }
    public void addGravity(){
        this.velocityY = this.velocityY + Constants.GRAVITY * Constants.DURATION;
        this.positionY = this.positionY + this.velocityY * Constants.DURATION;
        if(this.positionY < 0){
            this.positionY = 0;
        }
        this.bird.setCenterY(this.positionY);
        this.eye.setCenterY(this.positionY);

    }

    public double getY(){
        return this.positionY;
    }

    public double getX(){
        return this.positionX;
    }
    public boolean checkIntersection(Bounds bound){
        return this.bird.intersects(bound);
    }

    public void resetVelocity(){
        this.velocityY = Constants.REBOUND_VELOCITY;
    }
}
