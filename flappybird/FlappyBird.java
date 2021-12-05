package evolution.flappybird;

import evolution.Arcade.Game;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class FlappyBird implements Game {
    private Bird bird;
    private ArrayList<Pipe> pipes;
    private Pipe rightMost;
    private Pane gamePane;
    private Label scoreLabel;
    private Label highScoreLabel;
    private int score;
    private int highScore;

    public FlappyBird(Stage stage, Pane gamePane, VBox bottomPane){
        this.gamePane = gamePane;
        this.gamePane.setPrefSize(Constants.STAGE_WIDTH, Constants.STAGE_HEIGHT);
        stage.sizeToScene();

        this.setUpLabels();
        bottomPane.getChildren().addAll(this.scoreLabel, this.highScoreLabel);

        this.bird = new Bird(gamePane);

        this.pipes = new ArrayList<>();
        this.rightMost = new Pipe(Constants.PIPE_INITIAL_X);
        this.gamePane.getChildren().addAll(this.rightMost.getTopPipe(), this.rightMost.getBotPipe());
        this.pipes.add(this.rightMost);
    }

    public void setUpLabels(){
        this.scoreLabel = new Label();
        this.highScoreLabel = new Label();
        this.score = 0;
        this.highScore = 0;
        this.scoreLabel.setText("Score: " + this.score);
        this.highScoreLabel.setText("High Score: " + this.highScore);

    }

    public void addScoreOnceBirdMovesPastPipe(){
            if(this.bird.getX() == this.pipes.get(0).getPosX() + Constants.PIPE_WIDTH){
                this.score++;
                this.scoreLabel.setText("Score: " + this.score);
                if(this.score > highScore){
                    this.highScore = this.score;
                    this.highScoreLabel.setText("High Score: " + this.highScore);
                }
            }
    }

    @Override
    public double setDuration(){
       return Constants.DURATION;

    }

    @Override
    public void updateGame(){
        this.bird.addGravity();
        this.addScoreOnceBirdMovesPastPipe();
        this.movePipes();
        this.generatePipes();
        this.removePipes();

    }
    @Override
    public boolean checkForGameOver(){
        if(this.bird.getY() > Constants.STAGE_HEIGHT || this.collisionCheck()){
            return true;
        }
        return false;
    }
    @Override
    public void restart(){
        //whhy doesnt this clear the highscore
        this.gamePane.getChildren().clear();
        this.score = 0;
        this.scoreLabel.setText("Score: " + this.score);
        this.bird = new Bird(gamePane);
        this.pipes = new ArrayList<>();
        this.rightMost = new Pipe(Constants.PIPE_INITIAL_X);
        this.gamePane.getChildren().addAll(this.rightMost.getTopPipe(), this.rightMost.getBotPipe());
        this.pipes.add(this.rightMost);

    }

    public boolean collisionCheck(){
        if (this.bird.checkIntersection(this.pipes.get(0).getTopBounds()) ||
                this.bird.checkIntersection(this.pipes.get(0).getBotBounds()) ){
            return true;
        }
        return false;
    }

    public void movePipes(){
        for(Pipe pipe : this.pipes){
            pipe.movePipe();
        }
    }

    public void generatePipes() {
        this.rightMost = this.pipes.get(this.pipes.size()-1);
        while (this.rightMost.getPosX() < Constants.STAGE_WIDTH) {
            Pipe newPipe = new Pipe(this.rightMost.getPosX() + 250);
            this.gamePane.getChildren().addAll(newPipe.getTopPipe(), newPipe.getBotPipe());
            this.pipes.add(newPipe);
            this.rightMost = newPipe;


        }
    }

    public void removePipes(){
        for (int i = 0; i < this.pipes.size(); i++){
            if (this.pipes.get(i).getPosX() < 0){
                this.gamePane.getChildren().removeAll(this.pipes.get(i).getTopPipe(),
                        this.pipes.get(i).getBotPipe());
                this.pipes.remove(this.pipes.get(i));
                i--;
            }
        }
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
}
