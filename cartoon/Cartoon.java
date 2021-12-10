package evolution.cartoon;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import java.util.ArrayList;

/**
 * high level logic class and does graphics that include logic
 * contains Turtle, Rain, and Lolipop
 */
public class Cartoon{

    private Turtle me;
    private Pane middlePane;
    //condition booleans
    private boolean startRain;
    private boolean directionRight;
    private ArrayList<Rain> rainList;
    private int counterForArray;
    private Label stepsLabel;
    private int numberOfSteps;

    /**
     * Creates Turtle and Lolipop, both in middlePane
     * sets middlePane to be the same as the middlePane so it can be referenced outside of constructor
     * instantiates booleans, array, and int
     * sets up timeline and middlePane
     */
    public Cartoon(Pane middlePane){
        //named me because the turtle is me
    this.me = new Turtle(middlePane);
    new Lolipop(middlePane);
    this.middlePane = middlePane;
    this.startRain = false;
    this.directionRight = true;
    this.rainList = new ArrayList<Rain>();
    this.counterForArray = 0;

    this.setupMiddlePane();
    this.setupTimeline();
    }

    /**
     *  sets up and adds label for amount of steps the turtle takes
     *  lets keypressed work in middlePane
     */
    private void setupMiddlePane(){
        this.middlePane.getChildren().add(stepsLabel = new Label(" 0" + " steps"));
        this.stepsLabel.setFont(Font.font(20));
        this.middlePane.setOnKeyPressed((KeyEvent e) -> this.handleKeyPress(e));
        this.middlePane.setFocusTraversable(true);
    }

    /**
     * 4 timelines and their corresponding keyframe
     */
    private void setupTimeline() {

        //sets turtle to move left or right depending on boolean directionRight which is decided by key inputs
        KeyFrame turtleStep = new KeyFrame(Duration.seconds(1),
                (ActionEvent e) -> this.me.move(directionRight));
        //updates label when Turtle moves
        KeyFrame stepsLabel = new KeyFrame(Duration.seconds(1),
                (ActionEvent e) -> this.updateStepsLabel());
        //creates rain when startRain is true which is decided by space bar
        KeyFrame createRain = new KeyFrame(Duration.seconds(0.4),
              (ActionEvent e) -> this.createRain(startRain));
        //makes the rain on the screen fall
        KeyFrame makeItRain = new KeyFrame(Duration.seconds(0.005),
                (ActionEvent e) -> this.makeItRain());

        Timeline timeline = new Timeline(turtleStep);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Timeline timeline2 = new Timeline(stepsLabel);
        timeline2.setCycleCount(Animation.INDEFINITE);
        timeline2.play();

        Timeline timeline3 = new Timeline(createRain);
        timeline3.setCycleCount(Animation.INDEFINITE);
        timeline3.play();

        Timeline timeline4 = new Timeline(makeItRain);
        timeline4.setCycleCount(Animation.INDEFINITE);
        timeline4.play();
    }

    /**
     * based on left or right arrow key, decide if directionRight is true or false
     * based on space bar decide if rain should start and stop generating
     */
    private void handleKeyPress(KeyEvent e) {
        KeyCode keyPressed = e.getCode();
        if (keyPressed == KeyCode.SPACE) {
            this.startRain = !this.startRain;
        }
        if (keyPressed == KeyCode.LEFT) {
            this.directionRight = false;
        }
        if (keyPressed == KeyCode.RIGHT){
            this.directionRight = true;
        }

    }

    /**
     * updates label by one if the turtle moves one step
     */
    private void updateStepsLabel() {
        if(me.getMovement() == true) {
            this.numberOfSteps++;
            this.stepsLabel.setText(" " + this.numberOfSteps + " steps");
        }

    }

    /**
     * if startRain becomes true, start generating a number from 2 to 13
     * based on that number, create that many rain droplets
     * and adds to arrayList
     */
    private void createRain(boolean startRain) {
        if (startRain == true) {
            int random = (int) (Math.random() * 12 + 2);
            for (int i = 0; i < random; i++) {
                this.rainList.add(new Rain());
                //counterForArray used to find the spot in where the new rain is added and add graphically
                this.middlePane.getChildren().add(this.rainList.get(this.counterForArray).getRain());
                this.counterForArray++;
            }
        }
    }

    /**
     * if there is rain on the screen, make them move
     * if rain is almost on the ground delete from screen and arrayList
     */
    private void makeItRain(){
        if(this.rainList.size() > 0) {
            for (int i = 0; i < this.rainList.size(); i++) {
                if(this.rainList.get(i).getRainStartY() > 550) {

                    //deletes from screen and arrayList
                    this.middlePane.getChildren().remove(this.rainList.get(i).getRain());
                    this.rainList.remove(i);
                    //so it doesn't go to the next i and so it doesn't skip a raindrop, cause of removing a rain
                    i--;
                    //so it doesn't mess up and add null to the screen when getting number (counterForArray) raindrop,
                    //cause of removing a rain
                    this.counterForArray--;
                }
                else {
                    //moves rain
                    this.rainList.get(i).moveRain();
                }
            }
        }
    }


}
