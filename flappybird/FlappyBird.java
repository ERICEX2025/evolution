package evolution.flappybird;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class FlappyBird {
    private ArrayList<Pipe> pipes;
    private Pipe rightMost;
    private Pane gamePane;
    private VBox bottomPane;
    private Label scoreLabel;
    private Label highScoreLabel;
    private int score;
    private int highScore;

    public FlappyBird(Pane gamePane, VBox bottomPane){
        this.gamePane = gamePane;
        this.gamePane.setPrefSize(Constants.GAMEPANE_WIDTH, Constants.GAMEPANE_HEIGHT);

        this.bottomPane = bottomPane;

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
        this.bottomPane.getChildren().addAll(this.scoreLabel, this.highScoreLabel);
    }

    public ArrayList<Pipe> getPipes() {
        return pipes;
    }

    public void reset(){
        this.score = 0;
        this.scoreLabel.setText("Score: " + this.score);
        this.pipes = new ArrayList<>();
        this.rightMost = new Pipe(Constants.PIPE_INITIAL_X);
        this.pipes.add(this.rightMost);
        this.gamePane.getChildren().addAll(this.rightMost.getTopPipe(), this.rightMost.getBotPipe());

    }


    public void addScore(){
        this.score++;
        this.scoreLabel.setText("Score: " + this.score);
        if(this.score > highScore){
            this.highScore = this.score;
            this.highScoreLabel.setText("High Score: " + this.highScore);
        }
    }

    public void generatePipes() {
        this.rightMost = this.pipes.get(this.pipes.size()-1);
        while (this.rightMost.getPosX() < Constants.GAMEPANE_WIDTH/2 + 1) {
            Pipe newPipe = new Pipe(this.rightMost.getPosX() + 250);
            this.gamePane.getChildren().addAll(newPipe.getTopPipe(), newPipe.getBotPipe());
            this.pipes.add(newPipe);
            this.rightMost = newPipe;
        }
    }

    public void movePipes(){
        for(Pipe pipe : this.pipes){
            pipe.movePipe();
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

}
