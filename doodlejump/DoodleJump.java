package evolution.doodlejump;

import evolution.arcade.Game;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * IMPLEMENTS Game so the arcade can call generic arcade game
 * methods without needing to know which game is being player
 * game handles the timeline and high level logic
 * contains doodle, Blue, Red, Black, Green, GameOver
 */
public class DoodleJump implements Game {

    private Doodle doodle;
    private Platform topMost;
    private ArrayList<Platform> platforms;
    private Pane gamePane;
    private double score;
    private Label counter;


    /**
     * associates with gamePane which is the middlePane and Score from PaneOrganizer
     * instantiates instance variables
     * setsup timeline
     * @param gamePane
     *
     */
    public DoodleJump(Pane gamePane, VBox bottomPane){
        this.gamePane = gamePane;
        this.gamePane.setPrefSize(Constants.STAGE_WIDTH, Constants.STAGE_HEIGHT);
        this.setupBackgroundPic();
        this.counter = new Label();
        bottomPane.getChildren().add(this.counter);
        this.doodle = new Doodle(this.gamePane);
        this.topMost = new Black(Constants.TOPMOST_X, Constants.TOPMOST_Y);
        this.score = 0;
        //adds initial platform and adds to the array and also graphically
        this.gamePane.getChildren().add(this.topMost.getPlatform());
        this.platforms = new ArrayList<>();
        this.platforms.add(this.topMost);
        }

    /**
     * updates the game
     */

    @Override
    public void updateGame(){
        this.doodle.addGravity();
        this.collisionCheck();
        this.generatePlatforms();
        this.scrollDown();
        this.updateLabel();
        this.checkBlue();
        this.removePlatform();
    }

    /**
     * sets the Duration of the timeline
     * @return
     */

    @Override
    public double setDuration(){
        return Constants.DURATION;
    }

    /**
     * restarts the game
     */
    @Override
    public void restart(){
        this.score = 0;
        this.gamePane.getChildren().clear();
        this.setupBackgroundPic();
        this.doodle = new Doodle(this.gamePane);
        this.topMost = new Black(Constants.TOPMOST_X, Constants.TOPMOST_Y);
        this.gamePane.getChildren().add(this.topMost.getPlatform());
        this.platforms = new ArrayList<>();
        this.platforms.add(this.topMost);
    }
    /**
     * if platform is blue, make it move
     */
    public void checkBlue(){
        for (Platform platform : this.platforms){
            if (platform.getColor() == Color.BLUE){
                platform.move();

            }
        }
    }

    /**
     * removes platform if they are off the stage
     * graphically and logically
     */
    public void removePlatform(){
        for (int i = 0; i < this.platforms.size(); i++){
            if (this.platforms.get(i).getPosY() > Constants.STAGE_HEIGHT - Constants.QUIT_PANE_HEIGHT){
                this.gamePane.getChildren().remove(platforms.get(i).getPlatform());
                this.platforms.remove(platforms.get(i));
                i--;
            }
        }
    }

    /**
     * given generate pseudocode
     * generates and adds logically and graphically
     */
    public void generatePlatforms() {
        this.topMost = this.platforms.get(this.platforms.size()-1);
            while (this.topMost.getPosY() > 0) {
                //4 * Constants move is the amount I think players can reasonablely move in terms of
                // x distance/4 key presses
                double min = Math.max(0, this.topMost.getPosX() - 4 * Constants.MOVE);
                double max = Math.min(Constants.STAGE_WIDTH - Constants.PLATFORM_WIDTH, this.topMost.getPosX() +
                        4 * Constants.MOVE);
                double PosX = (int) (Math.random() * (max - min + 1) + min);

                //buffer so blocks don't spawn right on top of each other
                double min2 = this.topMost.getPosY() - Constants.BUFFER;
                double max2 = this.topMost.getPosY() - Constants.MAX_Y_BETWEEN_PLATFORMS;
                double PosY = (int) (Math.random() * (max2 - min2 + 1) + min2);
                Platform newPlatform;
                int randomInt = (int) (Math.random() * 4);
                switch (randomInt) {
                    case 0:
                        newPlatform = new Black(PosX, PosY);
                        break;
                    case 1:
                        newPlatform = new Red(PosX, PosY);
                        break;
                    case 2:
                        newPlatform = new Blue(PosX, PosY);
                        break;
                    default:
                        newPlatform = new Green(PosX, PosY);
                        break;
                }

                this.gamePane.getChildren().add(newPlatform.getPlatform());
                this.platforms.add(newPlatform);
                this.topMost = newPlatform;


            }
        }

    /**
     * checks for intersection if intersection, reset velocity, if intersection with green, reset to bouncy velocity,
     * if intersection with red, reset velocity, remove it graphically and logically
     */
    public void collisionCheck(){
        for(int i = 0; i < this.platforms.size(); i++) {
            if (this.doodle.checkIntersection(this.platforms.get(i).getBounds()) && this.doodle.getVelocityY() > 0) {
                if(this.platforms.get(i).getColor() == Color.GREEN){
                    this.doodle.bouncyResetVelocity();
                }
                else if(this.platforms.get(i).getColor() == Color.RED){
                    this.doodle.resetVelocity();
                    this.gamePane.getChildren().remove(this.platforms.get(i).getPlatform());
                    this.platforms.remove(this.platforms.get(i));
                    i--;
                }
                else {
                    this.doodle.resetVelocity();
                }
            }

        }
    }

    /**
     * updates label to increase score by how many pixels the screen scrolls
     */
    public void updateLabel(){
        this.counter.setText(Constants.SCORE_LABEL_TEXT + (int)this.score);
        this.counter.setLayoutX(Constants.SCORE_LABEL_X -20);
        this.counter.setLayoutY(Constants.SCORE_LABEL_Y);

    }

    /**
     * checks if doodle falls and stops timeline which stops everything else
     */
    public boolean checkForGameOver(){
        if (this.doodle.getPositionY() > Constants.STAGE_HEIGHT - Constants.QUIT_PANE_HEIGHT){
            return true;

        }
        return false;
    }

    /**
     * if doodle is above mid, the platforms move down by the amount about mid
     * score also gets updated each time with how much it scrolls
     */
    public void scrollDown(){
        if (this.doodle.getPositionY() < Constants.MIDPOINT){
            double amountAboveMid = Constants.MIDPOINT - this.doodle.getPositionY();
            this.score += amountAboveMid;
            this.doodle.setPositionY(Constants.MIDPOINT);
            for (int i = 0; i < this.platforms.size(); i++){
                this.platforms.get(i).moveDown(amountAboveMid);
            }
        }
    }

    /**
     * keyhandler
     */
    @Override
    public void keyHandler (KeyEvent e){
        switch (e.getCode()){
            case LEFT:
                this.doodle.moveLeft();
                if (this.doodle.getDoodle().getX() < 0){
                    this.doodle.getDoodle().setX(Constants.STAGE_WIDTH - Constants.DOODLE_WIDTH);
                }
                break;

            case RIGHT:

                this.doodle.moveRight();
                if (this.doodle.getDoodle().getX() >= Constants.STAGE_WIDTH){
                    this.doodle.getDoodle().setX(0);
                }
                break;

            default:
                break;
        }
        e.consume();
    }

    /**
     * not used/not applicable
     * @param timeline
     */
    @Override
    public void setTimeline(Timeline timeline) {

    }

    /**
     * sets up background pic
     */
    private void setupBackgroundPic() {
        Image image = new Image(this.getClass().getResourceAsStream("violet.jpg"));
        ImageView iv = new ImageView(image);
        iv.setFitWidth(Constants.STAGE_WIDTH);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);
        this.gamePane.getChildren().add(iv);
    }
}

