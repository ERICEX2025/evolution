package evolution.flappybird;

/**
 * nueral network class. used for determining if the birds should jump in the smartBird Game using nueral networks
 * that take in input and give out an output. option to passes and Mutate the weights for the next generation if needed
 * Syn0 and Syn1 represents weight arrays that mess with the input and return an output
 */

public class NeuralNetwork {

    private double[][] inputNodes;
    private double[][] syn0;
    private double[][] syn1;
    double distanceFromPipe;
    double distanceFromPipeGapHeight;
    double velocity;

    /**
     * Constructor for a random generation of birds. Sets up syn0 and syn1 with random weights
     */

    public NeuralNetwork(){
        this.setUpSyn0();
        this.setUpSyn1();
    }

    /**
     * Constructor for passing weights down to the next generation. Has a small chance one of the passed
     * down weights will mutate and be random
     * used to see if we can get a perfect combination of weights that can Win against the flappy Bird Game
     * @param syn0
     * @param syn1
     */

    public NeuralNetwork(double[][] syn0, double[][] syn1){
        this.setSyn0(syn0);
        this.setSyn1(syn1);
        this.mutate();
    }

    /**
     * setter for Syn0. sets syn0 equal to the passed down syn0
     * @param syn0
     */
    private void setSyn0(double[][] syn0) {
        this.syn0 = new double[2][3];
        for (int r = 0; r < this.syn0.length; r++) {
            for (int c = 0; c < this.syn0[0].length; c++) {
                this.syn0[r][c] = syn0[r][c];
            }
        }
    }

    /**
     * setter for Syn1. sets syn1 equal to the passed down syn1
     * @param syn1
     */

    private void setSyn1(double[][] syn1) {
        this.syn1 = new double[1][2];
        for (int r = 0; r < this.syn1.length; r++) {
            for (int c = 0; c < this.syn1[0].length; c++) {
                this.syn1[r][c] = syn1[r][c];
            }
        }
    }

    /**
     * has a chance to mutate one of the weights of the passed down syn0 and syn1 weight arrays
     * to a random weight which is (-1,1)
     */

    private void mutate(){
    //mutation rate decides the percentage it will mutate
        for(int r = 0; r < this.syn0.length; r++){
            for(int c = 0; c < this.syn0[0].length; c++) {
                if(Math.random() > Constants.MUTATION_RATE) {
                    this.syn0[r][c] = (Math.random() * 2) - 1;
                }
            }
        }

        for(int r = 0; r < this.syn1.length; r++){
            for(int c = 0; c < this.syn1[0].length; c++) {
                if(Math.random() > Constants.MUTATION_RATE) {
                    this.syn1[r][c] = (Math.random() * 2) - 1;
                }
            }
        }

    }

    /**
     * sets up syn0 with random weights values (-1, 1) for creating a population with random syn0 and syn1 weights
     */
    private void setUpSyn0(){
        this.syn0 = new double[2][3];
        for(int r = 0; r < this.syn0.length; r++){
            for(int c = 0; c < this.syn0[0].length; c++) {
                this.syn0[r][c] = (Math.random()*2)-1;
            }
        }
    }

    /**
     * sets up syn1 with random weights values (-1, 1) for creating a population with random syn0 and syn1 weights
     */
    private void setUpSyn1(){
        this.syn1 = new double[1][2];
        for(int r = 0; r < this.syn1.length; r++){
            for(int c = 0; c < this.syn1[0].length; c++) {
                this.syn1[r][c] = (Math.random()*2)-1;
            }
        }
    }

    /**
     * takes in inputs, normalizes them
     * used to update the inputNodes for the neural network so that it can go through the neural network
     * and make a decision based on the current environment situation of each bird. Returns the decision
     * if it should jump or not through a boolean based on the neural network and the bird's specific
     * syn0 and syn1 weight arrays
     * @param distanceFromPipe
     * @param distanceFromPipeGapHeight
     * @param velocity
     * @return
     */

    public boolean updateInputNodes(double distanceFromPipe, double distanceFromPipeGapHeight, double velocity){
        this.distanceFromPipe = distanceFromPipe/Constants.GAMEPANE_WIDTH;
        this.distanceFromPipeGapHeight = distanceFromPipeGapHeight/Constants.GAMEPANE_HEIGHT;
        this.velocity = velocity/Constants.VELOCITY_NORMALIZER;
        this.inputNodes = new double[][]{{this.distanceFromPipe}, {this.distanceFromPipeGapHeight}, {this.velocity}};
        return this.jump();
    }

    /**
     * helper method
     * normalizes a value (0, 1) through a sigmoid function to keep the numbers small and get an useful output
     * number used for deciding if it should jump or not
     * @param number
     * @return
     */

    private double normalize(double number){
        double newNumber = 1 / (1 + Math.pow(Math.E, -number));
        return newNumber;
    }

    /**
     * returns syn0 weights array for it to be passed to the next generation
     * @return
     */

    public double[][] getSyn0(){
        return this.syn0;
    }

    /**
     * returns syn1 weights array for it to be passed to the next generation
     * @return
     */

    public double[][] getSyn1(){
        return  this.syn1;
    }

    /**
     * helper method
     * MatrixMultiplication psudoCode used to mess with inputs to produce a hidden layer and then an output
     * @param weight
     * @param nodes
     * @return
     */
    private double[][] matrixMultiplication(double[][] weight, double[][] nodes){
        if(weight[0].length == nodes.length){
            double[][] C = new double[weight.length][nodes[0].length];
            for(int r = 0; r < weight.length; r++) {
                for(int c = 0; c < nodes[0].length; c++){
                    for (int i = 0; i < nodes.length; i++) {
                        C[r][c] += weight[r][i] * nodes[i][c];
                    }
                }
            }
            return C;
        }
        return null;
    }

    /**
     * Takes in normalized input array nodes and matrix multiplies it with syn0 array, normalizes it,
     * uses this hidden layer and again, matrix multiplies it with syn1 array, normalizes the output array
     * which is just one number with 1 row and col
     * @param inputNodes
     * @return
     */
    private double forwardPropagation(double[][] inputNodes){
        double[][] hiddenLayer = this.matrixMultiplication(syn0, inputNodes);
        for(double[] value : hiddenLayer){
            value[0] = this.normalize(value[0]);
        }
        double[][] outputNode = this.matrixMultiplication(syn1, hiddenLayer);

        return this.normalize(outputNode[0][0]);
    }

    /**
     * based on this normalized output between 0 and 1 which is the result of going through the neural network,
     * decides if the bird should jump, if the value is above 0.5 and does not if it returns 0.5 and under
     */

    private boolean jump() {
        if(this.forwardPropagation(this.inputNodes) > 0.5){
        return true;
        }
        else{
            return false;
        }
    }

}
