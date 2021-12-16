package evolution.flappybird;

/**
 * this is the multiplayer version of the flappybird game. inherits from flappybird to use its Pipe generation,
 * removal, and move methods. Also uses flappybird's score methods. IMPLEMENTS Game so arcade can use this game
 * generically without having to know its specifics
 */

import evolution.arcade.Game;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MultiplayerBird extends FlappyBird implements Game {

    private Bird bird1;
    private Bird bird2;
    private boolean dead1;
    private boolean dead2;
    private Pane gamePane;

    /**
     * because it has 2 birds and but is the same game, it inherits the basics of the game and implements it
     * differently using 2 birds and overrides when necessary to take into account the 2 birds
     * @param gamePane
     * @param bottomPane
     */
    public MultiplayerBird(Pane gamePane, VBox bottomPane){
        super(gamePane, bottomPane);
        this.gamePane = gamePane;
        this.setUpLabels();
        this.setUpBirds();
        this.dead1 = false;
        this.dead2 = false;

    }

    /**
     * helper method to set up birds, setting opacity to help its looks 0.0
     */

    private void setUpBirds(){
        this.bird1 = new Bird(gamePane);
        this.bird1.setOpacity();
        this.bird2 = new Bird(gamePane);
        this.bird2.setOpacity();
        this.bird2.setColor(Color.PURPLE);
    }

    /**
     * called every iteration of the timeline, used by arcade generically
     */

    @Override
    public void updateGame(){
        this.isDead();
        if(dead1 == false) {
            this.bird1.addGravity();
        }
        if(dead2 == false) {
            this.bird2.addGravity();
        }
        this.checkForBirdPassingPipe();
        this.movePipes();
        this.generatePipes();
        this.removePipes();
    }

    /**
     * returns the duration for the timeline's keyframe in arcade
     * @return
     */

    @Override
    public double setDuration(){
        return Constants.DURATION;

    }

    /**
     * called every iteration to see if its game Over
     * @return
     */

    @Override
    public boolean checkForGameOver(){
        if(this.dead1 && this.dead2) {
            return true;
        }
        return false;
    }

    /**
     * used to reset the Pane, score, Pipes, and generate new birds
     *
     */

    @Override
    public void restart(){
        this.gamePane.getChildren().clear();
        this.resetScore();
        this.resetPipes();
        this.setUpBirds();
        this.dead1 = false;
        this.dead2 = false;

    }

    /**
     * checks if the birds are dead if they collide with pipe or fall down the screen. removes the birds
     * grafically and logically if it does die while also setting the booleans to true so gameOver will know
     * and put a game Over label on screen
     */

    private void isDead(){
        if(dead1 == false) {
            if (this.bird1.checkIntersection(this.getPipes().get(0).getTopBounds()) ||
                    this.bird1.checkIntersection(this.getPipes().get(0).getBotBounds()) ||
                    this.bird1.getY() > Constants.GAMEPANE_HEIGHT) {
                this.dead1 = true;
                this.bird1.removeBird();
                this.bird1 = null;
            }
        }
        if(dead2 == false) {
            if (this.bird2.checkIntersection(this.getPipes().get(0).getTopBounds()) ||
                    this.bird2.checkIntersection(this.getPipes().get(0).getBotBounds()) ||
                    this.bird2.getY() > Constants.GAMEPANE_HEIGHT) {
                this.dead2 = true;
                this.bird2.removeBird();
                this.bird2 = null;
            }
        }
    }

    /**
     * checks if the bird passes the Pipe, if its not dead. Adds score if it does pass a pipe
     */

    private void checkForBirdPassingPipe(){
        if(dead1 == false) {
            if (this.bird1.getX() == this.getPipes().get(0).getPosX() + Constants.PIPE_WIDTH) {
                this.addScore();
            }
        }
        else if(dead2 == false) {
            if (this.bird2.getX() == this.getPipes().get(0).getPosX() + Constants.PIPE_WIDTH) {
                this.addScore();
            }
        }
    }

    /**
     * keyhandler that uses up and space to make the birds jump
     * @param e
     */
    @Override
    public void keyHandler (KeyEvent e){
        switch (e.getCode()){
            case SPACE:
                if(dead1 == false) {
                    this.bird1.resetVelocity();
                }
                break;
            case UP:
                if(dead2 == false) {
                    this.bird2.resetVelocity();
                }
                break;
            default:
                break;
        }
        e.consume();
    }

    /**
     * not used but necessary to include because it implements game and is needed for smartBird Game
     */
    @Override
    public void setTimeline(Timeline timeline) {

    }
}
