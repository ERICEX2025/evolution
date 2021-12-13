package evolution.flappybird;

import evolution.Arcade.Game;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.util.ArrayList;


public class SmartBird extends FlappyBird implements Game {

    private Timeline timeline;
    private ArrayList<BirdsThatLearn> birds;
    private Bird[] topThree;
    private double distanceFromPipe;
    private double distanceFromGapHeight;
    private Pane gamePane;
    private VBox bottomPane;

    private Label aliveLabel;
    private Label generationLabel;
    private Label currentFitnessLabel;
    private Label avgFitnessLastGenLabel;
    private Label bestFitnessLastGenLabel;
    private Label bestAllTimeFitnessLabel;
    private int aliveCounter;
    private int generationCounter;
    private int currentFitnessCounter;
    private int avgFitnessLastGen;
    private int bestFitnessLastGen;
    private int bestAllTimeFitness;

    public SmartBird(Timeline timeline,Pane gamePane, VBox bottomPane) {
        super(gamePane, bottomPane);
        this.timeline = timeline;
        this.gamePane = gamePane;
        this.bottomPane = bottomPane;
        this.setUpBottomPane();
        this.birds = new ArrayList<>();
//        this.topFive = new Bird[5];
        for (int i = 0; i < 50; i++) {
            BirdsThatLearn bird = new BirdsThatLearn(this.gamePane);

            bird.setColor(this.createRandomColor());
            bird.setOpacity();
            this.birds.add(bird);

        }

    }

    public Color createRandomColor(){
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);
        return Color.rgb(red, green, blue);
    }

    public void setUpBottomPane() {
        this.aliveLabel = new Label();
        this.generationLabel = new Label();
        this.currentFitnessLabel = new Label();
        this.avgFitnessLastGenLabel = new Label();
        this.bestFitnessLastGenLabel = new Label();
        this.bestAllTimeFitnessLabel = new Label();

        this.aliveCounter = 50;
        this.generationCounter = 0;
        this.currentFitnessCounter = 0;
        this.avgFitnessLastGen = 0;
        this.bestFitnessLastGen = 0;
        this.bestAllTimeFitness = 0;

        HBox buttonPane = new HBox();
        buttonPane.setAlignment(Pos.CENTER);
        Button oneSpeed = new Button();
        Button twoSpeed = new Button();
        Button fiveSpeed = new Button();
        Button max = new Button();

        oneSpeed.setText("1x");
        twoSpeed.setText("2x");
        fiveSpeed.setText("5x");
        max.setText("Max");

        oneSpeed.setOnAction(ActionEvent -> this.timeline.setRate(1));
        oneSpeed.setFocusTraversable(false);
        twoSpeed.setOnAction(ActionEvent -> this.timeline.setRate(2));
        twoSpeed.setFocusTraversable(false);
        fiveSpeed.setOnAction(ActionEvent -> this.timeline.setRate(5));
        fiveSpeed.setFocusTraversable(false);
        max.setOnAction(ActionEvent -> this.timeline.setRate(100));
        max.setFocusTraversable(false);

        buttonPane.getChildren().addAll(oneSpeed,
                twoSpeed, fiveSpeed, max);

        this.updateLabels();

        this.bottomPane.getChildren().addAll(this.aliveLabel, this.generationLabel, this.currentFitnessLabel
        , this.avgFitnessLastGenLabel, this.bestFitnessLastGenLabel, this.bestAllTimeFitnessLabel, buttonPane);
    }

    public void updateLabels(){
        this.aliveLabel.setText("Alive: " + this.aliveCounter);
        this.generationLabel.setText("Generation: " + this.generationCounter);
        this.currentFitnessLabel.setText("CurrentFitness: " + this.currentFitnessCounter);
        this.avgFitnessLastGenLabel.setText("AvgFitnessLastGen: " + this.avgFitnessLastGen);
        this.bestFitnessLastGenLabel.setText("BestFitnessLastGen: " + this.bestFitnessLastGen);
        this.bestAllTimeFitnessLabel.setText("BestAllTimeFitnessLastGen: " + this.bestAllTimeFitness);
    }


    @Override
    public void updateGame() {
        for(BirdsThatLearn Bird : this.birds) {
        Bird.addGravity();
        }
//        this.collisionCheck();
        this.movePipes();
        this.generatePipes();
        this.removePipes();

        this.updateNeuralNetworkForBirds();

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
//        for (int i = 0; i < 50; i++) {
//            BirdsThatLearn bird = new BirdsThatLearn(this.gamePane);
//            bird.setColor(this.createRandomColor());
//            this.birds.add(bird);
//        }

    }

    @Override
    public void keyHandler(KeyEvent e) {
    }

//    public void collisionCheck() {
//        if(this.birds.size() < 5){
//            if(this.birds.get(0).getX() > this.getPipes().get(0).getPosX() + Constants.PIPE_WIDTH){
//                for(Bird bird : this.topFive){
//                    if(this.score > 0){
//
//                    }
//                }
//            }
//        }
//        if(this.birds.size() > 0) {
//            for (BirdsThatLearn Bird : this.birds) {
//                if (Bird.checkIntersection(this.getPipes().get(0).getTopBounds()) ||
//                        Bird.checkIntersection(this.getPipes().get(0).getBotBounds())) {
//
//                    Bird.removeBird();
//                    this.birds.remove(Bird);
//                    this.aliveCounter--;
//                    this.aliveLabel.setText("Alive: " + this.aliveCounter);
//                }
//
//            }
//        }
//        else{
////            this.reset();
//        }
//    }


    public void updateNeuralNetworkForBirds(){

        this.setDistanceFromNextPipe();
        for(BirdsThatLearn Bird : this.birds){

            if(this.getPipes().get(0).getPosX() + Constants.PIPE_WIDTH - this.birds.get(0).getX() < 0){
                this.distanceFromGapHeight = this.getPipes().get(1).getGapHeight() - Bird.getY();
            }
            else{
                this.distanceFromGapHeight = this.getPipes().get(0).getGapHeight() - Bird.getY();
            }

            if(Bird.getNeuralNetwork().updateInputNodes(this.distanceFromPipe, this.distanceFromGapHeight,
                    Bird.getVelocityY()) == true){
                Bird.resetVelocity();
            }
        }
    }

    public void setDistanceFromNextPipe() {
        if(this.getPipes().get(0).getPosX() + Constants.PIPE_WIDTH - this.birds.get(0).getX() < 0){
            this.distanceFromPipe = this.getPipes().get(1).getPosX() + Constants.PIPE_WIDTH - this.birds.get(0).getX();
        }
        else{
            this.distanceFromPipe = this.getPipes().get(0).getPosX() + Constants.PIPE_WIDTH - this.birds.get(0).getX();
        }
    }

}
