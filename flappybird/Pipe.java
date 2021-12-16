package evolution.flappybird;

/**
 * This is the Pipe class. it is a wrapper class and wraps 2 rectangles, these are used to determine if they collide
 * with the birds
 */

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Pipe {
    private Rectangle pipeTop;
    private Rectangle pipeBot;
    private int xPos;
    private int random;

    /**
     * Constructor for Pipe, has a parameter for xPos used to Position the next pipe 250 length apart
     * @param xPos
     */

    public Pipe(int xPos) {

        this.xPos = xPos;

        /*
         * random determines the Pipe Gap height and has a max and min that makes it spawn at least 50 pixels away
         * from the top of the screen and bottom of the screen
         */

        this.random =  (int) (Math.random() * (Constants.PIPE_GAP_POSITION_MAX - Constants.PIPE_GAP_POSITION_MIN + 1)
                + Constants.PIPE_GAP_POSITION_MIN);

        this.pipeTop = new Rectangle(this.xPos, Constants.PIPE_TOP_INITIAL_Y,
                Constants.PIPE_WIDTH, this.random);
        this.pipeTop.setFill(Color.GREEN);

        //yPos is random + gap length and height is from the gap height + Pipegap to the bottom of the screen
        this.pipeBot = new Rectangle(this.xPos, this.random + Constants.PIPE_GAP, Constants.PIPE_WIDTH,
                Constants.GAMEPANE_HEIGHT - (this.random + Constants.PIPE_GAP));
        this.pipeBot.setFill(Color.GREEN);
    }

    /**
     * getter to get PosX to determine if the bird passed the pipe, and if so, start comparing bird distance to the next
     * pipe in the array
     * also used as an input to see how far the bird is from the upcoming pipe
     * @return
     */

    public int getPosX(){
        return this.xPos;
    }

    /**
     * getter to get the gap height of the middle of the gap, used as an input for nueral network
     * @return
     */

    public int getGapHeight(){
        return (this.random + Constants.PIPE_GAP/2);
    }

    /**
     * getter for the top pipe for adding/removing graphically
     * @return
     */

    public Rectangle getTopPipe(){
        return this.pipeTop;
    }

    /**
     * getter for the bot pipe for adding/removing graphically
     * @return
     */

    public Rectangle getBotPipe(){
        return this.pipeBot;
    }

    /**
     * getter
     * gets the top bounds, used for checking collision
     * @return
     */

    public Bounds getTopBounds() {
        return this.pipeTop.getLayoutBounds();
    }

    /**
     * getter
     * gets the bot bounds, used for checking collision
     * @return
     */

    public Bounds getBotBounds() {
        return this.pipeBot.getLayoutBounds();
    }

    /**
     * used to move the pipe by 1 every timeline iteration
     */

    public void movePipe(){
        this.xPos--;
        this.pipeTop.setX(xPos);
        this.pipeBot.setX(xPos);
    }
}
