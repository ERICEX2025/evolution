package evolution.flappybird;

/**
 * This is the BirdsThatLearn class! Birds that have nueralnetworks used for the SmartBird game.
 * this class inherits from the Bird class and just adds a fitness tracker and nueral networks
 */

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class BirdsThatLearn extends Bird{

    private NeuralNetwork NeuralNetwork;
    private int fitness; //keeps track of the fitness of each bird

    /**
     * Constructor for when you want random weights for the neuralnetwork of the bird
     * @param myPane
     */

    public BirdsThatLearn(Pane myPane){
        super(myPane);
        this.NeuralNetwork = new NeuralNetwork();
        this.setColor(this.createRandomColor());
        this.setOpacity();
    }

    /**
     * Constructor for when you want to pass down weights to a new bird
     * @param syn0
     * @param syn1
     * @param myPane
     */

    public BirdsThatLearn(double[][] syn0, double[][] syn1, Pane myPane){
        super(myPane);
        this.NeuralNetwork = new NeuralNetwork(syn0, syn1);
        this.setColor(this.createRandomColor());
        this.setOpacity();
    }

    /**
     * this is a getter for the neural networks of each bird which is used to get each bird's weights to pass down to
     * the next generation
     * @return
     */

    public NeuralNetwork getNeuralNetwork(){
        return this.NeuralNetwork;
    }

    /**
     * adds fitness to each bird everytime it is alive for each timeline iteration
     */

    public void addFitness(){
        this.fitness++;
    }

    /**
     * getter for fitness. used for determining the fittest of birds
     * @return
     */

    public int getFitness(){
        return this.fitness;
    }

    /**
     * COLORRRRRRRRRRRRR! :D
     * @return
     */

    private Color createRandomColor() {
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);
        return Color.rgb(red, green, blue);
    }
}
