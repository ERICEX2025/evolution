package evolution.flappybird;

import evolution.doodlejump.Constants;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

public class Pipe {
    private Rectangle pipeTop;
    private Rectangle pipeBot;
    private int xPos;
    private int YPos;

    public Pipe() {
        int random =  (int) (Math.random() * 11);
        this.pipeTop = new Rectangle();
        this.pipeBot = new Rectangle();

    }

//    public Bounds getBounds() {
//        return this.Pipe.getLayoutBounds();
//    }
//
//    public void movePipe(){
//        this.xPos++;
//        this.Pipe.setX(xPos);
//    }
//    public int getPosX() {
//        return (int) this.Pipe.getX();
//    }
//
//    public int getPosY() {
//        return (int) this.Pipe.getY();
//    }
//
//    public Rectangle getPlatform(){
//        return this.Pipe;
//    }
}
