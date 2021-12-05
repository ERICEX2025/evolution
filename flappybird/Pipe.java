package evolution.flappybird;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Pipe {
    private Rectangle pipeTop;
    private Rectangle pipeBot;
    private int xPos;

    public Pipe(int xPos) {

        this.xPos = xPos;
        int random =  (int) (Math.random() * (Constants.PIPE_GAP_POSITION_MAX - Constants.PIPE_GAP_POSITION_MIN + 1)
                + Constants.PIPE_GAP_POSITION_MIN);
        this.pipeTop = new Rectangle(this.xPos, Constants.PIPE_TOP_INITIAL_Y,
                Constants.PIPE_WIDTH, random);
        this.pipeTop.setFill(Color.GREEN);
        this.pipeBot = new Rectangle(this.xPos, random + 100, Constants.PIPE_WIDTH,
                Constants.STAGE_HEIGHT - (random + 100));
        this.pipeBot.setFill(Color.GREEN);
    }

    public int getPosX(){
        return this.xPos;
    }
    public Rectangle getTopPipe(){
        return this.pipeTop;
    }


    public Rectangle getBotPipe(){
        return this.pipeBot;
    }

    public Bounds getTopBounds() {
        return this.pipeTop.getLayoutBounds();
    }
    public Bounds getBotBounds() {
        return this.pipeBot.getLayoutBounds();
    }

    public void movePipe(){
        this.xPos--;
        this.pipeTop.setX(xPos);
        this.pipeBot.setX(xPos);
    }
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
