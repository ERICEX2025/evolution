package evolution.Arcade;

import evolution.Arcade.Game;
import evolution.doodlejump.DoodleJump;
import evolution.doodlejump.PaneOrganizer;
import evolution.flappybird.FlappyBird;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public enum Games {
    FLAPPYBIRD, MULTIPLAYERFLAPPYBIRD, SMARTFLAPPYBIRD, DOODLEJUMP, CARTOON;


    public String getName() {
        switch (this) {
            case DOODLEJUMP:
                return "DoodleJump";

            case FLAPPYBIRD:
                return "FlappyBird";

            default:
                return null;
        }
    }
    public Game startGame(Stage stage, Pane gamePane, VBox bottomPane){
        switch (this) {
            case DOODLEJUMP:
                DoodleJump doodleGame = new DoodleJump(stage, gamePane, bottomPane);
                return doodleGame;

            case FLAPPYBIRD:
                FlappyBird flappyGame = new FlappyBird(stage, gamePane, bottomPane);
                return flappyGame;

            default:
                return null;
        }

    }

//    public Game setUp(Stage stage, BorderPane root) {
//        switch (this) {
//            case DOODLEJUMP:
////                PaneOrganizer organizer = new PaneOrganizer();
////                root.setCenter(organizer.getRoot());
////                stage.sizeToScene();
////                Game doodleJump = new DoodleJump(organizer.getGamePane(), organizer.getLabel());
//                return doodleJump;
//
//            default:
//                return null;
//        }
//    }

    }

