package evolution.flappybird;

/**
 * RegularBird Game class. It inherits the flappyBird game class which has the flappybird game essentials and
 * adds a bird to make it the classic flappybird game. Implements Game so this game can be treated as a generic
 * arcade game
 */

import evolution.arcade.Game;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class RegularBird extends FlappyBird implements Game {
    private Bird bird;
    private Pane gamePane;

    /**
     * inherits basics of the game, pipes, score from flappyBird parent, sets up labels and intantiates
     * one bird and adds it to the pane
     * @param gamePane
     * @param bottomPane
     */

    public RegularBird(Pane gamePane, VBox bottomPane){
        super(gamePane, bottomPane);
        this.setUpLabels();
        this.bird = new Bird(gamePane);
        this.gamePane = gamePane;
    }

    /**
     * updates the bird's yposition based on gravity, checks for passing pipe to add score, moves, generates, and
     * removes Pipes in every iteration of the timeline
     */

    @Override
    public void updateGame(){
        this.bird.addGravity();
        this.checkForBirdPassingPipe();
        this.movePipes();
        this.generatePipes();
        this.removePipes();
    }

    /**
     * used to set duration of the keyframe of the timeline in the arcade class
     */

    @Override
    public double setDuration(){
        return Constants.DURATION;

    }

    /**
     * checks if the bird hits a pipe of falls down off screen
     * if it does, return gameOver as true for the arcade class
     * @return
     */

    @Override
    public boolean checkForGameOver(){
        if(this.bird.getY() > Constants.GAMEPANE_HEIGHT || this.collisionCheck()){
            this.bird.removeBird();
            return true;
        }
        return false;
    }

    /**
     * restarts the game by clearing the pane, resetting scores and pipes, and also readds the bird
     */

    @Override
    public void restart(){
        this.gamePane.getChildren().clear();
        this.resetScore();
        this.resetPipes();
        this.bird = new Bird(gamePane);
    }

    /**
     * jumps/resets velocity by space key
     * @param e
     */

    @Override
    public void keyHandler (KeyEvent e){
        switch (e.getCode()){
            case SPACE:
                this.bird.resetVelocity();
                break;
            default:
                break;
        }
        e.consume();
    }

    /**
     * not used, used for SmartBird for adjusting rate of timeline
     */
    @Override
    public void setTimeline(Timeline timeline) {

    }

    /**
     * checks if bird collides with pipe
     * @return
     */
    private boolean collisionCheck(){
        if (this.bird.checkIntersection(this.getPipes().get(0).getTopBounds()) ||
                this.bird.checkIntersection(this.getPipes().get(0).getBotBounds()) ){
            return true;
        }
        return false;
    }

    /**
     * checks if bird passes pipe, if it does, add a point to the score
     */
    private void checkForBirdPassingPipe(){
        if(this.bird.getX() == this.getPipes().get(0).getPosX() + Constants.PIPE_WIDTH){
            this.addScore();
        }
    }


}
