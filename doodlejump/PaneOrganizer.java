package evolution.doodlejump;

import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * handles the high level graphics using borderpane
 * contains Game
 */
public class PaneOrganizer {

    private BorderPane root;
    private Pane gamePane;
    private Label score;

    /**
     * sets root as a image
     * adds score in gamePane which is set to the center
     * creates bottomPane and sets it to the bottom
     *
     */
    public PaneOrganizer() {
        this.root = new BorderPane();

        this.gamePane = new Pane();
        this.gamePane.setPrefSize(Constants.STAGE_WIDTH, Constants.STAGE_HEIGHT);
        this.score = new Label();
        this.gamePane.getChildren().add(score);
        this.root.setCenter(gamePane);


    }

    public Pane getGamePane(){
        return gamePane;
    }
    public Label getLabel(){
        return score;
    }


    public BorderPane getRoot(){
        return this.root;
    }
}