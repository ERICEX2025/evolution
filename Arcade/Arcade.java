package evolution.Arcade;

import evolution.doodlejump.Constants;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Arcade {
    private BorderPane root;
    private Timeline timeline;
    private Stage stage;
    private Game currentGame;
    private Pane gamePane;
    private VBox bottomPane;
    private HBox topPane;

    public Arcade(Stage stage){
        this.gamePane = new Pane();
        this.bottomPane = new VBox();
        this.topPane = new HBox();
        this.stage = stage;
        this.root = new BorderPane();
        this.setUpArcade();
    }


    private void setUpArcade(){
        this.gamePane.getChildren().clear();
        this.bottomPane.getChildren().clear();
        this.root.getChildren().clear();
        VBox middlePane = new VBox();

        for(Games game : Games.values()){
            Button button = new Button(game.getName());
            button.setOnAction(ActionEvent -> startGame(game));
            middlePane.getChildren().add(button);
        }

        Button quit = new Button("Quit");
        quit.setOnAction(ActionEvent -> System.exit(0));
        quit.setFocusTraversable(false);

        middlePane.setAlignment(Pos.CENTER);
        middlePane.setSpacing(10);
        middlePane.getChildren().add(quit);
        middlePane.setPrefSize(400, 500);
        this.root.setCenter(middlePane);

    }

    private void startGame(Games game){
        this.root.setCenter(this.gamePane);
        this.root.setBottom(this.bottomPane);
        this.setUpTopPane();
        this.currentGame = game.startGame(this.stage, this.gamePane, this.bottomPane);
        KeyFrame kf1 = new KeyFrame(Duration.seconds(this.currentGame.setDuration()), (ActionEvent e) -> {
            currentGame.updateGame(); this.gameOver();});
        this.timeline = new Timeline(kf1);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    private void gameOver(){
        if(this.currentGame.checkForGameOver()){
            this.timeline.stop();
            Label gameOver = new Label();
            gameOver.setTextFill(Color.RED);
            gameOver.setText("Game Over");
            gameOver.setLayoutX(gamePane.getHeight()/2);
            gameOver.setLayoutY(gamePane.getWidth()/2);
            this.gamePane.getChildren().add(gameOver);
        }
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
        this.root.setTop(this.topPane);
    }

    private void restart(){
        timeline.play();
        this.currentGame.restart();
    }
    private void back(){
        this.currentGame = null;
        this.timeline.stop();
        this.setUpArcade();
    }




    public Pane getRoot() {
        return this.root;
    }
}
