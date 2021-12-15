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
            BirdsThatLearn bird = new BirdsThatLearn(this.gamePane);

            bird.setColor(this.createRandomColor());
            bird.setOpacity();
            this.birds.add(bird);
        }
    }

    private void createNextGenBirds() {
//        boolean passFirstPipe = false;
//        for(BirdsThatLearn Bird: this.fitnessArray){
//            if(Bird.getFitness()>290){
//                passFirstPipe = true;
//            }
//
//        }
//       if(passFirstPipe) {
           BirdsThatLearn birdToBePassed = this.fitnessArray.get(0);
           int index = 0;
           for (int i = 0; i < this.fitnessArray.size(); i++) {
               if (this.fitnessArray.get(i).getFitness() > birdToBePassed.getFitness()) {
                   birdToBePassed = this.fitnessArray.get(i);
                   index = i;
               }
           }
           for (int i = 0; i < 17; i++) {
               BirdsThatLearn bird = new BirdsThatLearn(birdToBePassed.getNeuralNetwork().getSyn0(),
                       birdToBePassed.getNeuralNetwork().getSyn1(), this.gamePane);

               bird.setColor(this.createRandomColor());
               bird.setOpacity();
               this.birds.add(bird);
           }
           this.fitnessArray.remove(index);

           //SECOND BIRD
           BirdsThatLearn birdToBePassed2 = this.fitnessArray.get(0);
           int index2 = 0;
           for (int i = 0; i < this.fitnessArray.size(); i++) {
               if (this.fitnessArray.get(i).getFitness() > birdToBePassed2.getFitness()) {
                   birdToBePassed2 = this.fitnessArray.get(i);
                   index2 = i;
               }
           }
           for (int i = 0; i < 17; i++) {
               BirdsThatLearn bird = new BirdsThatLearn(birdToBePassed2.getNeuralNetwork().getSyn0(),
                       birdToBePassed2.getNeuralNetwork().getSyn1(), this.gamePane);

               bird.setColor(this.createRandomColor());
               bird.setOpacity();
               this.birds.add(bird);
           }
           this.fitnessArray.remove(index2);

           //Third BIRD
           BirdsThatLearn birdToBePassed3 = this.fitnessArray.get(0);
           int index3 = 0;
           for (int i = 0; i < this.fitnessArray.size(); i++) {
               if (this.fitnessArray.get(i).getFitness() > birdToBePassed3.getFitness()) {
                   birdToBePassed3 = this.fitnessArray.get(i);
                   index3 = i;
               }
           }
           for (int i = 0; i < 16; i++) {
               BirdsThatLearn bird = new BirdsThatLearn(birdToBePassed3.getNeuralNetwork().getSyn0(),
                       birdToBePassed3.getNeuralNetwork().getSyn1(), this.gamePane);

               bird.setColor(this.createRandomColor());
               bird.setOpacity();
               this.birds.add(bird);
           }
           this.fitnessArray.remove(index3);
       }
//       else{
//           this.create50RandomBirds();
//       }
//    }


    @Override
    public void updateGame() {
        for (BirdsThatLearn Bird : this.birds) {
            Bird.addGravity();
        }

        this.movePipes();
        this.generatePipes();
        this.removePipes();

        this.updateNeuralNetworkForBirds();
        this.collisionCheck();
        this.updateFitness();

    }

    private void updateFitness() {
        for (BirdsThatLearn Bird : this.birds) {
            Bird.addFitness();
        }
        this.currentFitnessCounter = this.birds.get(0).getFitness();
        this.currentFitnessLabel.setText("CurrentFitness: " + this.currentFitnessCounter);
            if (this.currentFitnessCounter > this.bestAllTimeFitness) {
                this.bestAllTimeFitness = this.currentFitnessCounter;
                this.bestAllTimeFitnessLabel.setText("BestAllTimeFitness: " + this.bestAllTimeFitness);
            }

    }


    public void collisionCheck() {
        if (this.birds.size() > 0) {
            for (int i = 0; i < this.birds.size(); i++) {
                if (this.birds.get(i).checkIntersection(this.getPipes().get(0).getTopBounds()) ||
                        this.birds.get(i).checkIntersection(this.getPipes().get(0).getBotBounds()) ||
                        this.birds.get(i).getY() > Constants.GAMEPANE_HEIGHT) {
                    if(Math.abs(this.getPipes().get(0).getGapHeight()-this.birds.get(i).getY()) < 400){
                        for(int o = 0; o < 100; o++){
                            this.birds.get(i).addFitness();
                        }
                    }
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
    public double setDuration() {
        return Constants.DURATION;
    }

    @Override
    public boolean checkForGameOver() {

            if (this.currentFitnessCounter == 10000) {
                return true;
            }

        return false;
    }

    @Override
    public void restart() {
        this.gamePane.getChildren().clear();
        this.bottomPane.getChildren().clear();
        this.resetPipes();
        this.setUpBottomPane();
        this.birds = new ArrayList<>();
        this.fitnessArray = new ArrayList<>();
        this.create50RandomBirds();
    }

    @Override
    public void keyHandler(KeyEvent e) {
    }

    @Override
    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }


    public void setUpBottomPane() {
        this.aliveLabel = new Label();
        this.generationLabel = new Label();
        this.currentFitnessLabel = new Label();
        this.avgFitnessLastGenLabel = new Label();
        this.bestFitnessLastGenLabel = new Label();
        this.bestAllTimeFitnessLabel = new Label();

        this.setDefaultLabels();

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

        this.bottomPane.getChildren().addAll(this.aliveLabel, this.generationLabel, this.currentFitnessLabel
                , this.avgFitnessLastGenLabel, this.bestFitnessLastGenLabel, this.bestAllTimeFitnessLabel,
                buttonPane);
    }

    private void setDefaultLabels() {
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

    private Color createRandomColor() {
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);
        return Color.rgb(red, green, blue);
    }

//    private void createNextGenBirds() {
////        System.out.println(this.selectedFittestBirds.size());
//        if (this.selectedFittestBirds.size() > 0) {
//            int o = 0;
//            for (int i = 0; i < this.selectedFittestBirds.size(); i++) {
//                this.birdToBePassed = this.selectedFittestBirds.get(i);
//                if (this.selectedFittestBirds.get(i).getFitness() > this.birdToBePassed.getFitness()) {
//                    this.birdToBePassed = this.selectedFittestBirds.get(i);
//                    o = i;
//                }
//            }
//            this.selectedFittestBirds.remove(o);
//
//            for (int i = 0; i < 17; i++) {
//                BirdsThatLearn bird = new BirdsThatLearn(this.avgFitnessLastGen,this.birdToBePassed.getNeuralNetwork().getSyn0(),
//                        this.birdToBePassed.getNeuralNetwork().getSyn1(), this.gamePane);
//
//                bird.setColor(this.createRandomColor());
//                bird.setOpacity();
//                this.fitness.add(bird);
//                this.birds.add(bird);
//            }
//
//            if (this.selectedFittestBirds.size() > 0) {
//                o = 0;
//                for (int i = 0; i < this.selectedFittestBirds.size(); i++) {
//                    this.birdToBePassed = this.selectedFittestBirds.get(i);
//                    if (this.selectedFittestBirds.get(i).getFitness() > this.birdToBePassed.getFitness()) {
//                        this.birdToBePassed = this.selectedFittestBirds.get(i);
//                        o = i;
//                    }
//
//                }
//                this.selectedFittestBirds.remove(o);
//            }
//
//
//            for (int i = 0; i < 17; i++) {
//                BirdsThatLearn bird = new BirdsThatLearn(this.avgFitnessLastGen, this.birdToBePassed.getNeuralNetwork().getSyn0(),
//                         this.birdToBePassed.getNeuralNetwork().getSyn1(), this.gamePane);
//
//                bird.setColor(this.createRandomColor());
//                bird.setOpacity();
//                this.fitness.add(bird);
//                this.birds.add(bird);
//            }
//
//            if (this.selectedFittestBirds.size() > 0) {
//                o = 0;
//                for (int i = 0; i < this.selectedFittestBirds.size(); i++) {
//                    this.birdToBePassed = this.selectedFittestBirds.get(i);
//                    if (this.selectedFittestBirds.get(i).getFitness() > this.birdToBePassed.getFitness()) {
//                        this.birdToBePassed = this.selectedFittestBirds.get(i);
//                        o = i;
//                    }
//
//                }
//                this.selectedFittestBirds.remove(o);
//            }
//
//            for (int i = 0; i < 16; i++) {
//                BirdsThatLearn bird = new BirdsThatLearn(this.avgFitnessLastGen, this.birdToBePassed.getNeuralNetwork().getSyn0(),
//                         this.birdToBePassed.getNeuralNetwork().getSyn1(), this.gamePane);
//                bird.setColor(this.createRandomColor());
//                bird.setOpacity();
//                this.fitness.add(bird);
//                this.birds.add(bird);
//            }
//        }
//        else{
//            this.create50RandomBirds();
//        }
//    }

//    private void createNextGenBirds() {
//        BirdsThatLearn birdToBePassed = this.fitnessArray.get(0);
//        int arrayi = 0;
//        for(int i = 0; i < this.fitnessArray.size(); i++){
//            if(this.fitnessArray.get(i).getFitness() > birdToBePassed.getFitness()) {
//                if(this.fitnessArray.get(i).getY() != 10) {
//                    birdToBePassed = this.fitnessArray.get(i);
//                    arrayi = i;
//                }
//            }
//        }
//        if(birdToBePassed.)

//       this.getTop3Fitness();
//        for (int i = 0; i < 17; i++) {
//                BirdsThatLearn bird = new BirdsThatLearn(this.avgFitnessLastGen,this.selectedFittestBirds.get(0).
//                        getNeuralNetwork().getSyn0(),
//                        this.selectedFittestBirds.get(0).getNeuralNetwork().getSyn1(), this.gamePane);
//
//                bird.setColor(this.createRandomColor());
//                bird.setOpacity();
//                this.fitnessArray.add(bird);
//                this.birds.add(bird);
//            }
//        for (int i = 0; i < 17; i++) {
//            BirdsThatLearn bird = new BirdsThatLearn(this.avgFitnessLastGen,this.selectedFittestBirds.get(1).
//                    getNeuralNetwork().getSyn0(),
//                    this.selectedFittestBirds.get(1).getNeuralNetwork().getSyn1(), this.gamePane);
//
//            bird.setColor(this.createRandomColor());
//            bird.setOpacity();
//            this.fitnessArray.add(bird);
//            this.birds.add(bird);
//        }
//        for (int i = 0; i < 16; i++) {
//            BirdsThatLearn bird = new BirdsThatLearn(this.avgFitnessLastGen,this.selectedFittestBirds.get(2).
//                    getNeuralNetwork().getSyn0(),
//                    this.selectedFittestBirds.get(2).getNeuralNetwork().getSyn1(), this.gamePane);
//
//            bird.setColor(this.createRandomColor());
//            bird.setOpacity();
//            this.fitnessArray.add(bird);
//            this.birds.add(bird);
//        }
//
//    }
//
//    private void getTop3Fitness(){
//        for(int ii = 0; ii < 3; ii++) {
//            BirdsThatLearn birdToBePassed = this.fitnessArray.get(0);
//            for (int i = 0; i < this.fitnessArray.size(); i++) {
//                if (this.fitnessArray.get(i).getFitness() > birdToBePassed.getFitness()) {
//                    birdToBePassed = this.fitnessArray.get(i);
//                    this.fitnessArray.remove(i);
//                    i--;
//                }
//            }
//            this.selectedFittestBirds.add(birdToBePassed);
//        }
//    }

}
