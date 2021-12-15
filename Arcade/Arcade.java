package evolution.Arcade;

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
    private VBox middlePane;
    private HBox topPane;
    private Pane gamePane;
    private VBox bottomPane;

    private Stage stage;
    private Timeline timeline;
    private Game currentGame;

    private Boolean pause;
    private Label pauseLabel;
    private Label gameOverLabel;

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

    private void setUpGameOverLabel(){
        this.gameOverLabel = new Label();
        this.gameOverLabel.setTextFill(Color.RED);
        this.gameOverLabel.setText("Game Over");
    }

    private void setUpPauseLabel() {
        this.pauseLabel = new Label();
        this.pauseLabel.setTextFill(Color.PURPLE);
        this.pauseLabel.setText("Pause");
    }

    private void setLabelsCenter(){
        this.gameOverLabel.setAlignment(Pos.CENTER);
        this.gameOverLabel.setPrefSize(this.gamePane.getWidth(), this.gamePane.getHeight());
        this.pauseLabel.setAlignment(Pos.CENTER);
        this.pauseLabel.setPrefSize(this.gamePane.getWidth(), this.gamePane.getHeight());
    }
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

    private void setUpBotPane(){
        this.bottomPane = new VBox();
        this.bottomPane.setStyle(Constants.LIGHT_BLUE);
        this.bottomPane.setAlignment(Pos.CENTER);
        this.bottomPane.setSpacing(Constants.TEXT_SPACING);
        this.bottomPane.setFocusTraversable(false);
    }

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

    private void back(){
        this.pause = false;
        this.currentGame = null;
        this.timeline.stop();
        this.clearEverything();
        this.root.setCenter(this.middlePane);
        this.stage.sizeToScene();
    }

    private void clearEverything(){
        this.gamePane.getChildren().clear();
        this.bottomPane.getChildren().clear();
        this.root.getChildren().clear();
    }

    private void updateGameAndCheckForGameOver(){
        if(this.currentGame.checkForGameOver()){
            this.timeline.stop();
            this.gamePane.getChildren().add(this.gameOverLabel);
        }
        else {
            this.currentGame.updateGame();
        }
    }

    private void restart(){
        if(this.currentGame.checkForGameOver()){
            this.gamePane.getChildren().remove(this.gameOverLabel);
        }
        if (this.pause == true) {
            this.gamePane.getChildren().remove(pauseLabel);
        }

        this.currentGame.restart();
        this.timeline.setRate(1);
        this.timeline.play();
    }

    public void keyHandler(KeyEvent e) {
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

    public Pane getRoot() {
        return this.root;
    }
}
