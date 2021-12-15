package evolution.flappybird;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class BirdsThatLearn extends Bird{

    private NeuralNetwork NeuralNetwork;
    private int fitness;

    public BirdsThatLearn(Pane myPane){
        super(myPane);
        this.NeuralNetwork = new NeuralNetwork();
        this.setColor(this.createRandomColor());
        this.setOpacity();
    }

    public BirdsThatLearn(double[][] syn0, double[][] syn1, Pane myPane){
        super(myPane);
        this.NeuralNetwork = new NeuralNetwork(syn0, syn1);
        this.setColor(this.createRandomColor());
        this.setOpacity();
    }

    public NeuralNetwork getNeuralNetwork(){
        return this.NeuralNetwork;
    }

    public void addFitness(){
        this.fitness++;
    }

    public int getFitness(){
        return this.fitness;
    }

    private Color createRandomColor() {
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);
        return Color.rgb(red, green, blue);
    }
}
