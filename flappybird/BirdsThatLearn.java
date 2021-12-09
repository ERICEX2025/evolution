package evolution.flappybird;

import javafx.scene.layout.Pane;


public class BirdsThatLearn extends Bird{

    private NeuralNetwork NeuralNetwork;

    public BirdsThatLearn(Pane myPane){
        super(myPane);
        this.NeuralNetwork = new NeuralNetwork();
    }


    public NeuralNetwork getNeuralNetwork(){
        return this.NeuralNetwork;
    }

}
