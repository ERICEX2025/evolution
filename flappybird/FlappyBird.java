package evolution.flappybird;

/**
 * the High Level Logic Class that deals with the pipes and setting up the Game
 * It is the parent class and lets the specific games implement their game logic their own specfic ways and their own
 * specific birds
 */

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

    /**
     * Constructor, takes in gamePane and bottomPane and so we can add the pipes and scores and birds to it
     * initializes the first pipe, puts it on screen and adds it to the array/
     * @param gamePane
     * @param bottomPane
     */

    public FlappyBird(Pane gamePane, VBox bottomPane){
        this.gamePane = gamePane;
        this.gamePane.setPrefSize(Constants.GAMEPANE_WIDTH, Constants.GAMEPANE_HEIGHT);

        this.bottomPane = bottomPane;

        this.pipes = new ArrayList<>();
        this.rightMost = new Pipe(Constants.PIPE_INITIAL_X);
        this.gamePane.getChildren().addAll(this.rightMost.getTopPipe(), this.rightMost.getBotPipe());
        this.pipes.add(this.rightMost);
    }

    /**
     * called if the individual game needs score and highScore, regular and multiplayer
     */

    public void setUpLabels(){
        this.scoreLabel = new Label();
        this.highScoreLabel = new Label();
        this.score = 0;
        this.highScore = 0;
        this.scoreLabel.setText("Score: " + this.score);
        this.highScoreLabel.setText("High Score: " + this.highScore);
        this.bottomPane.getChildren().addAll(this.scoreLabel, this.highScoreLabel);
    }

    /**
     * called mainly to check if there is a bird collision with a pipe
     * @return
     */

    public ArrayList<Pipe> getPipes() {
        return pipes;
    }

    /**
     * called to reset the score and is used for restart for Regular and Multiplayer bird
     */
    public void resetScore(){
        this.score = 0;
        this.scoreLabel.setText("Score: " + this.score);

    }

    /**
     * when the game ends or resets, called to reset the pipes
     */

    public void resetPipes(){
        this.pipes = new ArrayList<>();
        this.rightMost = new Pipe(Constants.PIPE_INITIAL_X);
        this.pipes.add(this.rightMost);
        this.gamePane.getChildren().addAll(this.rightMost.getTopPipe(), this.rightMost.getBotPipe());
    }

    /**
     * called to add score and update the high score if applicable
     */

    public void addScore(){
        this.score++;
        this.scoreLabel.setText("Score: " + this.score);
        if(this.score > highScore){
            this.highScore = this.score;
            this.highScoreLabel.setText("High Score: " + this.highScore);
        }
    }

    /**
     * called continuously
     * generatePipe if the Pipe before it reaches the middle of the screen
     */

    public void generatePipes() {
        this.rightMost = this.pipes.get(this.pipes.size()-1);
        while (this.rightMost.getPosX() < Constants.GAMEPANE_WIDTH/2 + 1) {
            Pipe newPipe = new Pipe(this.rightMost.getPosX() + Constants.DISTANCE_BETWEEN_PIPES);
            this.gamePane.getChildren().addAll(newPipe.getTopPipe(), newPipe.getBotPipe());
            this.pipes.add(newPipe);
            this.rightMost = newPipe;
        }
    }

    /**
     * called continuously
     * moves all the pipes by 1
     */

    public void movePipes(){
        for(Pipe pipe : this.pipes){
            pipe.movePipe();
        }
    }

    /**
     * called continuously
     * removes the pipes if it goes offscreen
     */

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
