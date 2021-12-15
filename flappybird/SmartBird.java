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

import java.util.ArrayList;


public class SmartBird extends FlappyBird implements Game {

    private Timeline timeline;
    private ArrayList<BirdsThatLearn> birds;
    private ArrayList<BirdsThatLearn> fitnessArray;

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

    public SmartBird(Pane gamePane, VBox bottomPane) {
        super(gamePane, bottomPane);
        this.gamePane = gamePane;
        this.bottomPane = bottomPane;
        this.setUpBottomPane();
        this.birds = new ArrayList<>();
        this.fitnessArray = new ArrayList<>();
        this.create50RandomBirds();

    }

    private void create50RandomBirds() {
        for (int i = 0; i < 50; i++) {
            this.birds.add(new BirdsThatLearn(this.gamePane));
        }
    }

    private void createNextGenBirds() {
        boolean passFirstPipe = false;
        for (BirdsThatLearn Bird : this.fitnessArray) {
            if (Bird.getFitness() > 290) {
                passFirstPipe = true;
            }

        }
        if (passFirstPipe) {

            //BEST BIRD
            BirdsThatLearn birdToBePassed = this.fitnessArray.get(0);
            int index = 0;
            for (int i = 0; i < this.fitnessArray.size(); i++) {
                if (this.fitnessArray.get(i).getFitness() > birdToBePassed.getFitness()) {
                    birdToBePassed = this.fitnessArray.get(i);
                    index = i;
                }
            }
            for (int i = 0; i < 17; i++) {
                this.birds.add(new BirdsThatLearn(birdToBePassed.getNeuralNetwork().getSyn0(),
                        birdToBePassed.getNeuralNetwork().getSyn1(), this.gamePane));
            }
            this.fitnessArray.remove(index);

            //SECOND BEST BIRD
            BirdsThatLearn birdToBePassed2 = this.fitnessArray.get(0);
            int index2 = 0;
            for (int i = 0; i < this.fitnessArray.size(); i++) {
                if (this.fitnessArray.get(i).getFitness() > birdToBePassed2.getFitness()) {
                    birdToBePassed2 = this.fitnessArray.get(i);
                    index2 = i;
                }
            }
            for (int i = 0; i < 17; i++) {
                this.birds.add(new BirdsThatLearn(birdToBePassed2.getNeuralNetwork().getSyn0(),
                        birdToBePassed2.getNeuralNetwork().getSyn1(), this.gamePane));
            }
            this.fitnessArray.remove(index2);


            //THIRD BEST BIRD
            BirdsThatLearn birdToBePassed3 = this.fitnessArray.get(0);
            int index3 = 0;
            for (int i = 0; i < this.fitnessArray.size(); i++) {
                if (this.fitnessArray.get(i).getFitness() > birdToBePassed3.getFitness()) {
                    birdToBePassed3 = this.fitnessArray.get(i);
                    index3 = i;
                }
            }
            for (int i = 0; i < 16; i++) {
                this.birds.add( new BirdsThatLearn(birdToBePassed3.getNeuralNetwork().getSyn0(),
                        birdToBePassed3.getNeuralNetwork().getSyn1(), this.gamePane));
            }
            this.fitnessArray.remove(index3);
        }

        else {
            this.create50RandomBirds();
        }
    }


    @Override
    public void updateGame() {
        for (BirdsThatLearn Bird : this.birds) {
            Bird.addGravity();
            Bird.addFitness();
        }
        this.updateFitness();

        this.movePipes();
        this.generatePipes();
        this.removePipes();

        this.updateNeuralNetworkForBirds();
        this.collisionCheck();
        this.addPointsIfNearPipeYPos();

    }

    private void updateFitness() {
        this.currentFitnessCounter = this.birds.get(0).getFitness();
        this.currentFitnessLabel.setText("CurrentFitness: " + this.currentFitnessCounter);
        if (this.currentFitnessCounter > this.bestAllTimeFitness) {
            this.bestAllTimeFitness = this.currentFitnessCounter;
            this.bestAllTimeFitnessLabel.setText("BestAllTimeFitness: " + this.bestAllTimeFitness);
        }

    }

    private void addPointsIfNearPipeYPos() {
        if (this.birds.size() > 0) {
            for (int i = 0; i < this.birds.size(); i++) {
                if (Math.abs(this.getPipes().get(0).getGapHeight() - this.birds.get(i).getY()) < 40) {
                    this.birds.get(i).addFitness();
                }
            }
        }
    }

    public void collisionCheck() {
        if (this.birds.size() > 0) {
            for (int i = 0; i < this.birds.size(); i++) {
                if (this.birds.get(i).checkIntersection(this.getPipes().get(0).getTopBounds()) ||
                        this.birds.get(i).checkIntersection(this.getPipes().get(0).getBotBounds()) ||
                        this.birds.get(i).getY() > Constants.GAMEPANE_HEIGHT) {
                    this.fitnessArray.add(this.birds.get(i));
                    this.birds.get(i).removeBird();
                    this.birds.remove(this.birds.get(i));
                    this.aliveCounter--;
                    this.aliveLabel.setText("Alive: " + this.aliveCounter);
                    i--;
                }
            }

        }

        if (this.birds.size() == 0) {
            this.gamePane.getChildren().clear();
            this.resetPipes();
            this.updateLabels();
            this.createNextGenBirds();
            this.fitnessArray.clear();
        }
    }

    private void updateLabels() {
        this.avgFitnessLastGen = 0;
        for (BirdsThatLearn bird : this.fitnessArray) {
            this.avgFitnessLastGen += bird.getFitness();
        }
        this.avgFitnessLastGen /= 50;
        this.avgFitnessLastGenLabel.setText("AvgFitnessLastGen: " + this.avgFitnessLastGen);
        this.aliveCounter = 50;
        this.aliveLabel.setText("Alive: " + this.aliveCounter);
        this.generationCounter++;
        this.generationLabel.setText("Generation: " + this.generationCounter);
        this.bestFitnessLastGen = this.currentFitnessCounter;
        this.bestFitnessLastGenLabel.setText("BestFitnessLastGen: " + this.bestFitnessLastGen);
        this.currentFitnessCounter = 0;
        this.currentFitnessLabel.setText("CurrentFitness: " + this.currentFitnessCounter);
    }


    public void updateNeuralNetworkForBirds() {
        this.setDistanceFromNextPipe();
        for (BirdsThatLearn Bird : this.birds) {
            if (this.getPipes().get(0).getPosX() + Constants.PIPE_WIDTH - this.birds.get(0).getX() < 0) {
                this.distanceFromGapHeight = this.getPipes().get(1).getGapHeight() - Bird.getY();
            } else {
                this.distanceFromGapHeight = this.getPipes().get(0).getGapHeight() - Bird.getY();
            }

            if (Bird.getNeuralNetwork().updateInputNodes(this.distanceFromPipe, this.distanceFromGapHeight,
                    Bird.getVelocityY()) == true) {
                Bird.resetVelocity();
            }
        }
    }

    public void setDistanceFromNextPipe() {
        if (this.getPipes().get(0).getPosX() + Constants.PIPE_WIDTH - this.birds.get(0).getX() < 0) {
            this.distanceFromPipe = this.getPipes().get(1).getPosX() + Constants.PIPE_WIDTH - this.birds.get(0).getX();
        } else {
            this.distanceFromPipe = this.getPipes().get(0).getPosX() + Constants.PIPE_WIDTH - this.birds.get(0).getX();
        }
    }

    @Override
    public void restart() {
        this.gamePane.getChildren().clear();
        this.birds = new ArrayList<>();
        this.fitnessArray = new ArrayList<>();
        this.resetPipes();
        this.bottomPane.getChildren().clear();
        this.setUpBottomPane();
        this.create50RandomBirds();
    }

    @Override
    public boolean checkForGameOver() {

        if (this.currentFitnessCounter == 20000) {
            return true;
        }

        return false;
    }

    @Override
    public double setDuration() {
        return Constants.DURATION;
    }

    @Override
    public void keyHandler(KeyEvent e) {
    }

    @Override
    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    private void setUpBottomPane() {
        this.aliveLabel = new Label();
        this.generationLabel = new Label();
        this.currentFitnessLabel = new Label();
        this.avgFitnessLastGenLabel = new Label();
        this.bestFitnessLastGenLabel = new Label();
        this.bestAllTimeFitnessLabel = new Label();

        this.setDefaultValues();

        this.bottomPane.getChildren().addAll(this.aliveLabel, this.generationLabel, this.currentFitnessLabel
                , this.avgFitnessLastGenLabel, this.bestFitnessLastGenLabel, this.bestAllTimeFitnessLabel);

        this.createTimeLineButtons();
    }

    private void setDefaultValues() {
        this.aliveCounter = 50;
        this.generationCounter = 0;
        this.currentFitnessCounter = 0;
        this.avgFitnessLastGen = 0;
        this.bestFitnessLastGen = 0;
        this.bestAllTimeFitness = 0;
        this.aliveLabel.setText("Alive: " + this.aliveCounter);
        this.generationLabel.setText("Generation: " + this.generationCounter);
        this.currentFitnessLabel.setText("CurrentFitness: " + this.currentFitnessCounter);
        this.avgFitnessLastGenLabel.setText("AvgFitnessLastGen: " + this.avgFitnessLastGen);
        this.bestFitnessLastGenLabel.setText("BestFitnessLastGen: " + this.bestFitnessLastGen);
        this.bestAllTimeFitnessLabel.setText("BestAllTimeFitness: " + this.bestAllTimeFitness);

    }

    private void createTimeLineButtons(){
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
        max.setOnAction(ActionEvent -> this.timeline.setRate(25));
        max.setFocusTraversable(false);

        buttonPane.getChildren().addAll(oneSpeed,
                twoSpeed, fiveSpeed, max);
        this.bottomPane.getChildren().add(buttonPane);
    }
}