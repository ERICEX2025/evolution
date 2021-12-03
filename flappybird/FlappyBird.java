package evolution.flappybird;

import evolution.doodlejump.Platform;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class FlappyBird {
    private Bird test;
    private ArrayList<Pipe> pipes;
    private Pipe rightMost;

    public FlappyBird(Pane gamePane){
        this.test = new Bird(gamePane);
        this.setUpTimeline();
        gamePane.setFocusTraversable(true);
        gamePane.setOnKeyPressed((KeyEvent e) -> this.keyHandler(e));
        this.pipes = new ArrayList<>();
        this.rightMost = new Pipe();
        this.pipes.add(this.rightMost);

    }
    public void setUpTimeline(){
        KeyFrame kf1 = new KeyFrame(Duration.seconds(Constants.DURATION), (ActionEvent e) -> updateGame());
        Timeline timeline = new Timeline(kf1);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }
    public void updateGame(){
        this.test.addGravity();
        this.checkforGameOver();

    }

    public void checkforGameOver(){

    }
    public void keyHandler (KeyEvent e){
        switch (e.getCode()){
            case SPACE:
                System.out.println("hi");
                this.test.resetVelocity();
                break;
            default:
                break;
        }
        e.consume();
    }
}
