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
        this.NeuralNetwork = new NeuralNetwork();
    }


    public NeuralNetwork getNeuralNetwork(){
        return this.NeuralNetwork;
    }

    public void setFitness(int fitness){
        this.fitness = fitness;
    }
    public int getFitness(){
        return this.fitness;
    }

}
