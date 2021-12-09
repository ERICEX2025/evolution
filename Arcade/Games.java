package evolution.Arcade;

import evolution.flappybird.MultiplayerBird;
import evolution.flappybird.RegularBird;
import evolution.flappybird.SmartBird;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public enum Games {
    FLAPPYBIRD, MULTIPLAYERFLAPPYBIRD, SMARTFLAPPYBIRD, DOODLEJUMP, CARTOON;


    public String getName() {
        switch (this) {
            case FLAPPYBIRD:
                return "FlappyBird";

            case MULTIPLAYERFLAPPYBIRD:
                return "MultiPlayerFlappyBird";

            case SMARTFLAPPYBIRD:
                return "SmartFlappyBird";

            default:
                return null;
        }
    }
    public Game startGame(Timeline timeline, Stage stage, Pane gamePane, VBox bottomPane){
        switch (this) {
            case FLAPPYBIRD:
                RegularBird flappyGame = new RegularBird(stage, gamePane, bottomPane);
                return flappyGame;

            case MULTIPLAYERFLAPPYBIRD:
                MultiplayerBird multiFlap = new MultiplayerBird(stage, gamePane, bottomPane);
                return multiFlap;

            case SMARTFLAPPYBIRD:
                SmartBird smartFlap = new SmartBird(timeline, stage, gamePane, bottomPane);
                return smartFlap;

            default:
                return null;
        }
    }


    }

