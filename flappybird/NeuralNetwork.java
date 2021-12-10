package evolution.flappybird;

import java.util.Random;

public class NeuralNetwork {

    private double[][] inputNodes;
    private double[][] syn0;
    private double[][] syn1;
    double distanceFromPipe;
    double pipeGapHeight;
    double velocity;

    public NeuralNetwork(){
        this.setUpSyn0();
        this.setUpSyn1();
    }

    public NeuralNetwork(double[][] syn0, double[][] syn1){
        this.syn0 = syn0;
        this.syn1 = syn1;
    }

    public boolean updateInputNodes(double distanceFromPipe, double pipeGapHeight, double velocity){
        this.distanceFromPipe = distanceFromPipe/Constants.STAGE_WIDTH;
        this.pipeGapHeight = pipeGapHeight/Constants.STAGE_HEIGHT;
        this.velocity = velocity/Constants.MAX_VELOCITY;

        this.inputNodes = new double[][]{{this.distanceFromPipe}, {this.pipeGapHeight}, {this.velocity}};
//        System.out.println(inputNodes[1][0]);
        return this.jump();
    }

    public void setUpSyn0(){
        this.syn0 = new double[2][3];
        for(int r = 0; r < this.syn0.length; r++){
            for(int c = 0; c < this.syn0[0].length; c++) {
//                Random random = new Random();
//                this.syn0[r][c] = -1 + (1 - (-1)) * random.nextDouble();
                this.syn0[r][c] = (Math.random()*2)-1;
            }
        }
    }

    public void setUpSyn1(){
        this.syn1 = new double[1][2];
        for(int r = 0; r < this.syn1.length; r++){
            for(int c = 0; c < this.syn1[0].length; c++) {
//                Random random = new Random();
//                this.syn0[r][c] = -1 + (1 - (-1)) * random.nextDouble();
                this.syn1[r][c] = (Math.random()*2)-1;
            }
        }
    }

    public double normalize(double number){
        double newNumber = 1 / (1 + Math.pow(Math.E, -number));
        return newNumber;
    }

//    public double[][] getSyn0(){
//        return this.syn0;
//    }
//
//    public double[][] getSyn1(){
//        return  this.syn1;
//    }

    public double[][] matrixMultiplication(double[][] weight, double[][] nodes){
        if(weight[0].length == nodes.length){
            double[][] C = new double[weight.length][nodes[0].length];
            for(int r = 0; r < weight.length; r++) {
                for(int c = 0; c < nodes[0].length; c++){
                    for (int i = 0; i < nodes.length; i++) {
                        C[r][c] += weight[r][i] * nodes[i][c];
                        return C;
                    }
                }
            }
        }
        return null;
    }

//    double[][] dotProduct(double[][] A, double[][] B):
//            // assume A has m rows and n columns,
//// B has p rows and q columns
//            if n and p are equal
//    make an empty double[][] C with m rows and q columns
//		for each row in A (from 0 to m):
//            for each col in B (from 0 to q):
//    from i=0 to p:
//    C[row][col] += A[row][i] * B[i][col]
//            return C


    public double forwardPropagation(double[][] inputNodes){
        double[][] hiddenLayer = this.matrixMultiplication(syn0, inputNodes);
        for(double[] value : hiddenLayer){
            value[0] = this.normalize(value[0]);
        }
        double[][] outputNode = this.matrixMultiplication(syn1, hiddenLayer);

//        System.out.println(this.normalize(outputNode[0][0]));
        return this.normalize(outputNode[0][0]);
    }


    public boolean jump() {
        System.out.println(this.forwardPropagation(this.inputNodes));
        if(this.forwardPropagation(this.inputNodes) > 0.5){
        return true;
        }
        else{
            return false;
        }
    }

}
