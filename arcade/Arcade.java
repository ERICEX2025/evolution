package evolution.arcade;

/**
 * This is the Arcade class. It contains the middlePane which is the arcade screen containing the games and a quit
 * button, when clicking on a game, the game appears and there is an option to go back, reset, or quit. It has a shared
 * timeline, gameOver and pause label. Contains currentGame which is Game, an interface. With currentGame, it is where
 * all the commands an arcade game needs.
 */

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import static javafx.scene.input.KeyCode.P;

public class Arcade {
    private BorderPane root;
    private VBox middlePane; //arcade screen pane
    private HBox topPane;
    private Pane gamePane;
    private VBox bottomPane;

    private Stage stage; //to resize
    private Timeline timeline; //shared timeline
    private Game currentGame;

    private Boolean pause;
    private Label pauseLabel;
    private Label gameOverLabel;

    /**
     * here is the Arcade Constructor. First instantiates instance variables and sets up all the Panes and labels,
     * in the Arcade setUp, the root is automatically set to have the arcade screen in the center already. Also sets the
     * keyhandler and focus
     * @param stage
     */
    public Arcade(Stage stage){
        this.root = new BorderPane();
        this.stage = stage;
        this.pause = false;

        this.setUpArcade();

        this.setUpTopPane();
        this.setUpBotPane();
        this.setUpGameOverLabel();
        this.setUpPauseLabel();

        this.gamePane = new Pane();
        this.gamePane.setFocusTraversable(true);
        this.gamePane.setOnKeyPressed((KeyEvent keyPressed) -> this.keyHandler(keyPressed));
    }

    /**
     * sets up arcade with buttons that when pressed, calls the method startGame. Delegates using Enums to make button
     * and start game all in a short forloop. Has Quit button and sets up the middlePane while also setting it in the
     * center.
     */

    private void setUpArcade(){
        this.middlePane = new VBox();

        for(Games game : Games.values()){
            Button button = new Button(game.getName());
            button.setOnAction(ActionEvent -> startGame(game));
            this.middlePane.getChildren().add(button);
        }

        Button quit = new Button("Quit");
        quit.setOnAction(ActionEvent -> System.exit(0));
        quit.setFocusTraversable(false);

        this.middlePane.getChildren().add(quit);
        this.middlePane.setPrefSize(Constants.ARCADE_WIDTH, Constants.ARCADE_HEIGHT);
        this.middlePane.setAlignment(Pos.CENTER);
        this.middlePane.setSpacing(Constants.TEXT_SPACING);
        this.middlePane.setStyle(Constants.LIGHT_BLUE);
        this.root.setCenter(this.middlePane);
    }

    /**
     * Sets up GameOverLabel and is called in constructor
     */

    private void setUpGameOverLabel(){
        this.gameOverLabel = new Label();
        this.gameOverLabel.setTextFill(Color.RED);
        this.gameOverLabel.setText("Game Over");
    }

    /**
     * Sets up PauseLabel and is called in constructor
     */

    private void setUpPauseLabel() {
        this.pauseLabel = new Label();
        this.pauseLabel.setTextFill(Color.PURPLE);
        this.pauseLabel.setText("Pause");
    }

    /**
     * Sets up Labels in the Center after game is chosen and their gamePane Width and Height is determined
     */

    private void setLabelsCenter(){
        this.gameOverLabel.setAlignment(Pos.CENTER);
        this.gameOverLabel.setPrefSize(this.gamePane.getWidth(), this.gamePane.getHeight());
        this.pauseLabel.setAlignment(Pos.CENTER);
        this.pauseLabel.setPrefSize(this.gamePane.getWidth(), this.gamePane.getHeight());
    }

    /**
     * Based on what button, an enum is pased in which instantiates the game and sets the currentGame. Sets a keyframe
     * with the game's specific duration and calls the game's update and checks for GameOver. Creates new Timeline and
     * passes it to the game. Sets the topPane (buttons) Top, gamePane Center and bottomPane Bottom. GamePane and
     * bottomPane are passed in the game's constructor's parameters. Sizes the Screen and Labels
     * @param game
     */

    private void startGame(Games game){
        this.currentGame = game.startGame(this.gamePane, this.bottomPane);
        KeyFrame kf1 = new KeyFrame(Duration.seconds(this.currentGame.setDuration()), (ActionEvent e) ->
            this.updateGameAndCheckForGameOver());
        this.timeline = new Timeline(kf1);
        this.currentGame.setTimeline(this.timeline);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
        this.root.setCenter(this.gamePane);
        this.root.setBottom(this.bottomPane);
        this.root.setTop(this.topPane);
        this.stage.sizeToScene();
        this.setLabelsCenter();

    }

    /**
     * sets up bottomPane which is used for each individual games' scores
     */

    private void setUpBotPane(){
        this.bottomPane = new VBox();
        this.bottomPane.setStyle(Constants.LIGHT_BLUE);
        this.bottomPane.setAlignment(Pos.CENTER);
        this.bottomPane.setSpacing(Constants.TEXT_SPACING);
        this.bottomPane.setFocusTraversable(false);
    }

    /**
     * Sets up the topPane which has the Back button which calls this.back to the arcade, quit button, and Restart which
     * calls this.restart and restarts the game.
     */

    private void setUpTopPane(){
        this.topPane = new HBox();

        Button back = new Button("Back");
        back.setOnAction(ActionEvent -> this.back());
        back.setFocusTraversable(false);

        Button quit = new Button("Quit");
        quit.setOnAction(ActionEvent -> System.exit(0));
        quit.setFocusTraversable(false);

        Button restart = new Button("Restart");
        restart.setOnAction(ActionEvent -> this.restart());
        restart.setFocusTraversable(false);

        this.topPane.setFocusTraversable(false);
        this.topPane.setStyle(Constants.LIGHT_BLUE);
        this.topPane.setAlignment(Pos.CENTER);
        this.topPane.setSpacing(Constants.TEXT_SPACING);
        this.topPane.getChildren().addAll(back, quit, restart);
    }

    /**
     * resets pause, stops timeline, and clear everything logically and graphically
     * then sets the arcade screen back up and resizes to it
     */

    private void back(){
        this.pause = false;
        this.currentGame = null;
        this.timeline.stop();
        this.clearEverything();
        this.root.setCenter(this.middlePane);
        this.stage.sizeToScene();
    }

    /**
     * clears everything graphically
     */

    private void clearEverything(){
        this.gamePane.getChildren().clear();
        this.bottomPane.getChildren().clear();
        this.root.getChildren().clear();
    }

    /**
     * is called every iteration of timeline to see if it is game Over. If not, update the game.
     */

    private void updateGameAndCheckForGameOver(){
        if(this.currentGame.checkForGameOver()){
            this.timeline.stop();
            this.gamePane.getChildren().add(this.gameOverLabel);
        }
        else {
            this.currentGame.updateGame();
        }
    }

    /**
     * checks if its gameOver or Pause to remove their labels and resets pause to false calls the game to restart
     * their individual game logic and sets timeline rate back to normal and plays it
     */

    private void restart(){
        if(this.currentGame.checkForGameOver()){
            this.gamePane.getChildren().remove(this.gameOverLabel);
        }
        if (this.pause) {
            this.pause = false;
            this.gamePane.getChildren().remove(pauseLabel);
        }

        this.currentGame.restart();
        this.timeline.setRate(1);
        this.timeline.play();
    }

    /**
     * Keyhandler is called in the Constructor for pausing and if its not paused, let it use the currentGame
     * keyhandler
     * @param e
     */

    private void keyHandler(KeyEvent e) {
        KeyCode keyPressed = e.getCode();
        if(!this.currentGame.checkForGameOver()) {
            if (keyPressed == P) {
                this.pause = !this.pause;
                if (this.pause == true) {
                    this.timeline.stop();
                    this.gamePane.getChildren().add(this.pauseLabel);
                }
                else {
                    this.gamePane.getChildren().remove(this.pauseLabel);
                    this.timeline.play();
                }
                e.consume();
            }
            else if (this.pause == false) {
                this.currentGame.keyHandler(e);
            }
        }
    }

    /**
     * Getter for App
     * @return
     */

    public Pane getRoot() {
        return this.root;
    }
}
