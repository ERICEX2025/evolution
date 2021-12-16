package evolution.flappybird;

/**
 * SMARTBIRD class!!! it learns!
 * implements Game so the generic game methods can be called by arcade without knowing specifics of the individual game
 * inherits FlappyBird to get the fundamentals of the game: pipes, and its methods while overriding to deal with
 * the specifics of smartBird game
 * Unlike the other modes, smartbird has a population of 50 birds contained in an arraylist and each of the birds
 * have neural networks. As the generations increase, they learn.
 */

import evolution.arcade.Game;
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

    /**
     * sets up the fundamentals through the parent class and instantiates the panes for the labels and scores.
     * sets up the labels and scores, new birds array and a fitness array that is used to calculate each bird's
     * fitness. Finally creates 50 birds with random weights
     * @param gamePane
     * @param bottomPane
     */
    public SmartBird(Pane gamePane, VBox bottomPane) {
        super(gamePane, bottomPane);
        this.gamePane = gamePane;
        this.bottomPane = bottomPane;
        this.setUpBottomPane();
        this.birds = new ArrayList<>();
        this.fitnessArray = new ArrayList<>();
        this.create50RandomBirds();

    }

    /**
     * creates 50 birds with random weights
     */
    private void create50RandomBirds() {
        for (int i = 0; i < 50; i++) {
            this.birds.add(new BirdsThatLearn(this.gamePane));
        }
    }

    /**
     * creates 50 birds while passing the top 3 birds from the last generation if at least one of them from
     * the previous generation passes the first pipe. else just recreate birds with random weights
     */
    private void createNextGenBirds() {
        boolean passFirstPipe = false;
        for (BirdsThatLearn Bird : this.fitnessArray) {
            if (Bird.getFitness() > 290) {
                passFirstPipe = true;
            }

        }
        /*
        loops through the fitness array to get the fittest bird, pass down its weights, removes it from the fitness
        array then loops through the fitness array again to get the second fittest bird, and etc until we pass down
        the top 3 fittest birds from the previous generation, their weights that is.
         */
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


    /**
     * called every timeline iteration. loops through the birds to apply gravity and adds a fitness as long as they are
     * alive. Calls the inherited pipe commands and updates the input to the neural network, lastly checks for collision
     * or if they fall down off screen.
     */

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

    }

    /**
     * updates fitness label
     */
    private void updateFitness() {
        this.currentFitnessCounter = this.birds.get(0).getFitness();
        this.currentFitnessLabel.setText("CurrentFitness: " + this.currentFitnessCounter);
        if (this.currentFitnessCounter > this.bestAllTimeFitness) {
            this.bestAllTimeFitness = this.currentFitnessCounter;
            this.bestAllTimeFitnessLabel.setText("BestAllTimeFitness: " + this.bestAllTimeFitness);
        }

    }

    /**
     * checks if the birds collides with a pipe or falls down off screen. if it does, add it to the fitness array
     * so the bird's fitness wont stop increasing and is used to determine the fittest birds.
     * then remove graphically and logically from birds array. and deals with the aliveCounter
     *
     * Once all of the birds die, clear everything reset and spawn the next generation and their weights
     * if at least one of them pass the first pipe
     */
    private void collisionCheck() {
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

    /**
     * helper method
     * updates the labels once a generation dies
     * for the next generation
     */
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

    /**
     * updates the neuralNetwork with updated inputs and asks for a decision to jump or not based on the updated inputs
     * and the bird's weights for each bird that is alive
     */
    private void updateNeuralNetworkForBirds() {
        this.setDistanceFromNextPipe();
        /*
         * sets the distance from the middle of the gap height to the bird's height to give the neural network the
         * latest updated input. once the pipe passes the bird, reset the y distance relative to the upcoming pipe
         */
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

    /**
     * sets the x distance from pipe array to give the neural network the latest updated input.
     * once the pipe passes the bird, reset the x distance relative to the upcoming pipe
     */
    private void setDistanceFromNextPipe() {
        if (this.getPipes().get(0).getPosX() + Constants.PIPE_WIDTH - this.birds.get(0).getX() < 0) {
            this.distanceFromPipe = this.getPipes().get(1).getPosX() + Constants.PIPE_WIDTH - this.birds.get(0).getX();
        } else {
            this.distanceFromPipe = this.getPipes().get(0).getPosX() + Constants.PIPE_WIDTH - this.birds.get(0).getX();
        }
    }

    /**
     * restarts the game. First clears the Pane, reset bird and fitness arraylists, pipes, bottomPane with
     * default values and lastly create 50 random weighted birds
     */
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

    /**
     * once one of the birds are good enough and hits 10,000 fitness, the game ends
     * @return
     */
    @Override
    public boolean checkForGameOver() {

        if (this.currentFitnessCounter == 10000) {
            return true;
        }

        return false;
    }

    /**
     * sets duration of the keyframe the timeline uses
     * @return
     */
    @Override
    public double setDuration() {
        return Constants.DURATION;
    }

    /**
     * not used. used for single and multiplayer birds, and other arcade games
     * @param e
     */
    @Override
    public void keyHandler(KeyEvent e) {
    }

    /**
     * associates with the main timeline so we can adjust the rate
     * @param timeline
     */
    @Override
    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    /**
     * sets up the bottom pane with the labels and with default values
     * helper method used in constructor and restart
     */
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

    /**
     * creates stats bar label and default values
     */
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

    /**
     * creates the timeline buttons for changing the rate of the timeline
     */
    private void createTimeLineButtons() {
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