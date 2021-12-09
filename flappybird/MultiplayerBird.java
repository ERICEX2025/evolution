package evolution.flappybird;

import evolution.Arcade.Game;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MultiplayerBird extends FlappyBird implements Game {

    private Bird bird1;
    private Bird bird2;
    private boolean dead1;
    private boolean dead2;
    private Pane gamePane;

    public MultiplayerBird(Stage stage, Pane gamePane, VBox bottomPane){
        super(stage, gamePane, bottomPane);
        this.setUpLabels();
        this.bird1 = new Bird(gamePane);
        this.bird2 = new Bird(gamePane);
        this.bird2.setColor(Color.PURPLE);
        this.dead1 = false;
        this.dead2 = false;
        this.gamePane = gamePane;

    }

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

    @Override
    public double setDuration(){
        return Constants.DURATION;

    }

    @Override
    public boolean checkForGameOver(){
        if(this.dead1 && this.dead2) {
            return true;
        }
        return false;
    }

    @Override
    public void restart(){
        this.gamePane.getChildren().clear();
        this.reset();
        this.bird1 = new Bird(gamePane);
        this.bird2 = new Bird(gamePane);
        this.bird2.setColor(Color.PURPLE);
        this.dead1 = false;
        this.dead2 = false;

    }

    public void isDead(){
        if(dead1 == false) {
            if (this.bird1.checkIntersection(this.getPipes().get(0).getTopBounds()) ||
                    this.bird1.checkIntersection(this.getPipes().get(0).getBotBounds()) ||
                    this.bird1.getY() > Constants.STAGE_HEIGHT) {
                this.dead1 = true;
                this.bird1.removeBird();
                this.bird1 = null;
            }
        }
        if(dead2 == false) {
            if (this.bird2.checkIntersection(this.getPipes().get(0).getTopBounds()) ||
                    this.bird2.checkIntersection(this.getPipes().get(0).getBotBounds()) ||
                    this.bird2.getY() > Constants.STAGE_HEIGHT) {
                this.dead2 = true;
                this.bird2.removeBird();
                this.bird2 = null;
            }
        }
    }

    public void checkForBirdPassingPipe(){
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
}
