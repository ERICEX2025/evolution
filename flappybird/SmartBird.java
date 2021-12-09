package evolution.flappybird;

import evolution.Arcade.Game;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;


public class SmartBird extends FlappyBird implements Game {

    private ArrayList<BirdsThatLearn> birds;
    private double distanceFromPipe;
    private double distanceFromGapHeight;
    private Pane gamePane;
    private VBox bottomPane;

    private Label aliveLabel;
    private Label generationLabel;
    private Label currentFitnessLabel;
    private Label avgFitnessLastGenLabel;
    private Label bestFitnessLastGenLabel;
    private Label bestAllTimeFitnessLastGenLabel;
    private int aliveCounter;
    private int generationCounter;
    private int currentFitnessCounter;
    private int avgFitnessLastGen;
    private int bestFitnessLastGen;
    private int bestAllTimeFitnessLastGen;

    public SmartBird(Timeline timeline, Stage stage, Pane gamePane, VBox bottomPane) {
        super(stage, gamePane, bottomPane);
        this.gamePane = gamePane;
        this.bottomPane = bottomPane;
        this.setUpLabels();
        this.birds = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            this.birds.add(new BirdsThatLearn(this.gamePane));
        }

    }

    @Override
    public void setUpLabels() {
        this.aliveLabel = new Label();
        this.generationLabel = new Label();
        this.currentFitnessLabel = new Label();
        this.avgFitnessLastGenLabel = new Label();
        this.bestFitnessLastGenLabel = new Label();
        this.bestAllTimeFitnessLastGenLabel = new Label();

        this.aliveCounter = 0;
        this.generationCounter = 0;
        this.currentFitnessCounter = 0;
        this.avgFitnessLastGen = 0;
        this.bestFitnessLastGen = 0;
        this.bestAllTimeFitnessLastGen = 0;

        this.aliveLabel.setText("Alive: " + this.aliveCounter);
        this.generationLabel.setText("Generation: " + this.generationCounter);
        this.currentFitnessLabel.setText("CurrentFitness: " + this.currentFitnessCounter);
        this.avgFitnessLastGenLabel.setText("AvgFitnessLastGen: " + this.avgFitnessLastGen);
        this.bestFitnessLastGenLabel.setText("BestFitnessLastGen: " + this.bestFitnessLastGen);
        this.bestAllTimeFitnessLastGenLabel.setText("BestAllTimeFitnessLastGen: " + this.bestAllTimeFitnessLastGen);

        this.bottomPane.getChildren().addAll(this.aliveLabel, this.generationLabel, this.currentFitnessLabel
        , this.avgFitnessLastGenLabel, this.bestFitnessLastGenLabel, this.bestAllTimeFitnessLastGenLabel);
    }


    @Override
    public void updateGame() {
        for(BirdsThatLearn Bird : this.birds) {
        Bird.addGravity();
        }

        this.setDistanceFromNextPipe();
        this.setDistanceFromGapHeight();
        this.updateNeuralNetworkForBirds();

        this.movePipes();
        this.generatePipes();
        this.removePipes();
    }

    @Override
    public double setDuration() {
        return Constants.DURATION;
    }

    @Override
    public boolean checkForGameOver() {
        return false;
    }

    @Override
    public void restart() {
    }

    @Override
    public void reset() {
    }

    @Override
    public void keyHandler(KeyEvent e) {
    }

//    public boolean collisionCheck() {
//            for(BirdsThatLearn Bird : this.birds){
//                Bird.checkIntersection()
//            }
//            if (this.bird.checkIntersection(this.getPipes().get(0).getTopBounds()) ||
//                    this.bird.checkIntersection(this.getPipes().get(0).getBotBounds())) {
//                return true;
//            }
//            return false;
//        }

    public void updateNeuralNetworkForBirds(){
        this.setDistanceFromNextPipe();
        this.setDistanceFromGapHeight();
        for(BirdsThatLearn Bird : this.birds){
            if(Bird.getNeuralNetwork().updateInputNodes(this.distanceFromPipe, this.distanceFromGapHeight,
                    Bird.getVelocityY()) == true){
                Bird.resetVelocity();
            }
        }
    }

    public void setDistanceFromNextPipe() {
        if(this.getPipes().get(0).getPosX() + Constants.PIPE_WIDTH - this.birds.get(0).getX() == 0){
            this.distanceFromPipe = this.getPipes().get(1).getPosX() + Constants.PIPE_WIDTH - this.birds.get(0).getX();
        }
        else{
            this.distanceFromPipe = this.getPipes().get(0).getPosX() + Constants.PIPE_WIDTH - this.birds.get(0).getX();
        }
    }

    public void setDistanceFromGapHeight() {
        if(this.getPipes().get(0).getPosX() + Constants.PIPE_WIDTH - this.birds.get(0).getX() == 0){
            this.distanceFromGapHeight = this.getPipes().get(1).getGapHeight() - this.birds.get(0).getY();
        }
        else{
            this.distanceFromGapHeight = this.getPipes().get(0).getPosX() - this.birds.get(0).getY();
        }
    }

}
