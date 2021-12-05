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
    private HBox topPane;
    private Pane gamePane;
    private VBox bottomPane;
    private VBox middlePane;

    private Stage stage;
    private Timeline timeline;
    private Game currentGame;

    private Boolean pause;
    private Label pauseLabel;
    private Label gameOver;

    public Arcade(Stage stage){
        this.root = new BorderPane();
        this.topPane = new HBox();
        this.gamePane = new Pane();
        this.bottomPane = new VBox();

        this.stage = stage;
        this.pause = false;

        this.setUpArcade();
        this.setUpTopPane();
        this.setUpGameOverLabel();
        this.setUpPauseLabel();
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

        this.middlePane.setAlignment(Pos.CENTER);
        this.middlePane.setSpacing(10);
        this.middlePane.getChildren().add(quit);
        this.middlePane.setPrefSize(400, 500);
        this.root.setCenter(this.middlePane);

    }


    private void startGame(Games game){
        this.root.setTop(this.topPane);

        this.currentGame = game.startGame(this.stage, this.gamePane, this.bottomPane);
        KeyFrame kf1 = new KeyFrame(Duration.seconds(this.currentGame.setDuration()), (ActionEvent e) -> {
            currentGame.updateGame(); this.gameOver();});
        this.timeline = new Timeline(kf1);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();

        this.root.setCenter(this.gamePane);
        this.root.setBottom(this.bottomPane);
    }

    private void setUpTopPane(){
        this.topPane = new HBox();
        Button back = new Button("Back");
        back.setOnAction(Action -> this.back());
        back.setFocusTraversable(false);

        Button quit = new Button("Quit");
        quit.setOnAction(ActionEvent -> System.exit(0));
        quit.setFocusTraversable(false);

        Button restart = new Button("Restart");
        restart.setOnAction(Action -> this.restart());
        restart.setFocusTraversable(false);

        this.topPane.setAlignment(Pos.CENTER);
        this.topPane.setSpacing(10);
        this.topPane.getChildren().addAll(back, quit, restart);
    }

    private void back(){
        this.currentGame = null;
        this.timeline.stop();
        this.clearEverything();
        this.root.setCenter(this.middlePane);
    }

    private void clearEverything(){
        this.gamePane.getChildren().clear();
        this.bottomPane.getChildren().clear();
        this.root.getChildren().clear();
    }

    private void restart(){
        if(this.currentGame.checkForGameOver()){
            this.gamePane.getChildren().remove(this.gameOver);
        }
        if (this.pause == true) {
            this.gamePane.getChildren().remove(pauseLabel);
        }
        this.timeline.play();
        this.currentGame.restart();
    }

    private void gameOver(){
        if(this.currentGame.checkForGameOver()){
            this.timeline.stop();
            this.gamePane.getChildren().add(this.gameOver);
        }
    }

    private void setUpGameOverLabel(){
        this.gameOver = new Label();
        this.gameOver.setTextFill(Color.RED);
        this.gameOver.setText("Game Over");
//        this.gameOver.setLayoutX(gamePane.getHeight()/2);
//        this.gameOver.setLayoutY(gamePane.getWidth()/2);
    }

    private void setUpPauseLabel() {
        this.pauseLabel = new Label();
        this.pauseLabel.setTextFill(Color.RED);
        this.pauseLabel.setText("Pause");
//        this.pauseLabel.setLayoutX(gamePane.getHeight()/2);
//        this.pauseLabel.setLayoutY(gamePane.getWidth()/2);
    }



    public void keyHandler(KeyEvent e) {
        System.out.println("hi");
        KeyCode keyPressed = e.getCode();
        if(!this.currentGame.checkForGameOver()) {
            if (keyPressed == P) {
                this.pause = !this.pause;
                if (this.pause == true) {
                    this.timeline.stop();
                    this.gamePane.getChildren().add(this.pauseLabel);
                } else {
                    this.gamePane.getChildren().remove(pauseLabel);
                    this.timeline.play();
                }
                e.consume();
            }
            if (this.pause == false) {
                this.currentGame.keyHandler(e);
            }
        }
    }

    public Pane getRoot() {
        return this.root;
    }
}
