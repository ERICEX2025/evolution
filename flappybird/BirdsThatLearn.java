package evolution.flappybird;

import javafx.scene.layout.Pane;


public class BirdsThatLearn extends Bird{

    private NeuralNetwork NeuralNetwork;
    private int fitness;

    public BirdsThatLearn(Pane myPane){
        super(myPane);
        this.NeuralNetwork = new NeuralNetwork();
    }

    public BirdsThatLearn(double[][] syn0, double[][] syn1, Pane myPane){
        super(myPane);
        this.NeuralNetwork = new NeuralNetwork(syn0, syn1);
    }

    public NeuralNetwork getNeuralNetwork(){
        return this.NeuralNetwork;
    }

    public void addFitness(int fitness){
        this.fitness++;
    }

    public int getFitness(){
        return this.fitness;
    }

}
