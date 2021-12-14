package evolution.cartoon;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * composite shape
 * move, getMovement methods to use
 * setMethods X and Y locations of the shapes that make up the composite shape
 * setupDropShadow
 */
public class Turtle {
    private Polygon turtleShell;
    private Circle turtleHead;
    private Circle turtleEye;
    private Line turtleTail;
    private Rectangle turtleLeg1;
    private Rectangle turtleLeg2;
    private Rectangle turtleLeg3;
    private Rectangle turtleLeg4;
    private boolean movement;

    /**
     * instantiates movement
     * instantiates Shell, Head, Eye, Tail, Legs
     * sets initial X and Y locations of all the shapes and dropShadow
     * adds all of them graphically onto middlePane
     */
    public Turtle(Pane middlePane){
        this.movement = true;

        //the shape (shell) that all other shapes' locations are relative to
        this.turtleShell = new Polygon(0,40, 0,20, 10,20, 10,10, 20,10, 20,0, 40,0, 40,10, 50,10, 50,20, 60,20, 60,40);
        this.turtleShell.setFill(Color.GREEN);

        this.turtleHead = new Circle(Constants.HEAD_RADIUS, Color.LIGHTGREEN);

        this.turtleEye = new Circle(Constants.EYE_RADIUS, Color.BLACK);

        this.turtleTail = new Line();
        this.turtleTail.setStrokeWidth(10);
        this.turtleTail.setStroke(Color.LIGHTGREEN);

        this.turtleLeg1 = new Rectangle(Constants.LEG_WIDTH, Constants.LEG_HEIGHT, Color.LIGHTGREEN);
        this.turtleLeg2 = new Rectangle(Constants.LEG_WIDTH, Constants.LEG_HEIGHT, Color.LIGHTGREEN);
        this.turtleLeg3 = new Rectangle(Constants.LEG_WIDTH, Constants.LEG_HEIGHT, Color.LIGHTGREEN);
        this.turtleLeg4 = new Rectangle(Constants.LEG_WIDTH, Constants.LEG_HEIGHT, Color.LIGHTGREEN);

        this.setXLoc(Constants.INITIAL_SHELL_X);
        this.setYLoc(Constants.INITIAL_SHELL_Y);
        this.setupDropShadow();

        middlePane.getChildren().addAll(this.turtleTail, this.turtleHead, this.turtleEye, this.turtleShell, this.turtleLeg1,
              this.turtleLeg2, this.turtleLeg3, this.turtleLeg4);
    }

    /**
     *
     * if right direction is true and its location is not at the right border,
     * then set movement to true and move turtle right
     *
     * if right direction is false and its location is not at the left border,
     * then set movement to true and move turtle left
     *
     * if turtle is at the borders, dont move and set movement to false
     */
    public void move(boolean rightDirection) {
        if(rightDirection == true && this.turtleShell.getLayoutX() < Constants.APP_WIDTH){
            this.movement = true;
            //sets/moves shell by the changed amount therefore moving all of its other parts by the same amount
            this.setXLoc(this.turtleShell.getLayoutX() + Constants.DISTANCE_X);
        }
        else if (rightDirection == false && this.turtleShell.getLayoutX() > 10){
            this.movement = true;
            //sets/moves shell by the changed amount therefore moving all of its other parts by the same amount
            this.setXLoc(this.turtleShell.getLayoutX() - Constants.DISTANCE_X);
        }
        else{
            this.movement = false;
        }
    }

    //getter method to see if there is movement
    public boolean getMovement(){
        return this.movement;
    }


    /**
     * sets x location of turtleShell and its body parts relative to the shell
     */
    private void setXLoc(double x) {
        this.turtleShell.setLayoutX(x);

        this.turtleHead.setCenterX(x + Constants.HEAD_X_OFFSET);
        this.turtleEye.setCenterX(x + Constants.EYE_X);
        this.turtleTail.setStartX(x + Constants.TAIL_START_X_OFFSET);
        this.turtleTail.setEndX(x + Constants.TAIL_END_X_OFFSET);
        this.turtleLeg1.setX(x + Constants.LEG1_X);
        this.turtleLeg2.setX(x + Constants.LEG2_X);
        this.turtleLeg3.setX(x + Constants.LEG3_X);
        this.turtleLeg4.setX(x + Constants.LEG4_X);

    }

    /**
     * sets up the y locations of the shapes that make up the composite shape relative to the shell y location
     */
    public void setYLoc(double y){
        this.turtleShell.setLayoutY(y);

        this.turtleHead.setCenterY(y + Constants.HEAD_Y_OFFSET);
        this.turtleEye.setCenterY(y + Constants.EYE_Y);
        this.turtleTail.setStartY(y + Constants.TAIL_START_Y_OFFSET);
        this.turtleTail.setEndY(y + Constants.TAIL_END_Y_OFFSET);
        this.turtleLeg1.setY(y + Constants.LEG_Y);
        this.turtleLeg2.setY(y + Constants.LEG_Y);
        this.turtleLeg3.setY(y + Constants.LEG_Y);
        this.turtleLeg4.setY(y + Constants.LEG_Y);
    }

    /**
     * sets up dropShadow for Head and Shell
     */
    public void setupDropShadow(){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.color(0.135, 0.135, 0.135));
        dropShadow.setOffsetX(-10.0);
        dropShadow.setOffsetY(30.0);
        this.turtleShell.setEffect(dropShadow);

        DropShadow dropShadow2 = new DropShadow();
        dropShadow2.setColor(Color.color(0.135, 0.135, 0.135));
        dropShadow2.setOffsetX(-10.0);
        dropShadow2.setOffsetY(30.0);
        this.turtleHead.setEffect(dropShadow2);
    }

    public double getXPos(){
        return this.turtleHead.getCenterX();
    }
}


