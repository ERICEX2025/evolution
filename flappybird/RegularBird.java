package evolution.flappybird;

import evolution.arcade.Game;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class RegularBird extends FlappyBird implements Game {
    private Bird bird;
    private Pane gamePane;

    public RegularBird(Pane gamePane, VBox bottomPane){
        super(gamePane, bottomPane);
        this.setUpLabels();
        this.bird = new Bird(gamePane);
        this.gamePane = gamePane;
    }

    @Override
    public void updateGame(){
        this.bird.addGravity();
        this.checkForBirdPassingPipe();
        this.movePipes();
        this.generatePipes();
        this.removePipes();
    }

    @Override
    public double setDuration(){
        return Constants.DURATION;

    }

    @Override
    public boolean checkForGameOver(){
        if(this.bird.getY() > Constants.GAMEPANE_HEIGHT || this.collisionCheck()){
            this.bird.removeBird();
            return true;
        }
        return false;
    }

    @Override
    public void restart(){
        this.gamePane.getChildren().clear();
        this.resetScore();
        this.resetPipes();
        this.bird = new Bird(gamePane);
    }

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

    @Override
    public void setTimeline(Timeline timeline) {

    }

    public boolean collisionCheck(){
        if (this.bird.checkIntersection(this.getPipes().get(0).getTopBounds()) ||
                this.bird.checkIntersection(this.getPipes().get(0).getBotBounds()) ){
            return true;
        }
        return false;
    }

    public void checkForBirdPassingPipe(){
        if(this.bird.getX() == this.getPipes().get(0).getPosX() + Constants.PIPE_WIDTH){
            this.addScore();
        }
    }


}
