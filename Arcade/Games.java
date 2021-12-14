package evolution.Arcade;

import evolution.cartoon.Cartoon;
import evolution.doodlejump.DoodleJump;
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

            case DOODLEJUMP:
                return "DoodleJump";

            case CARTOON:
                return "Turtoon";

            default:
                return null;
        }
    }
    public Game startGame(Pane gamePane, VBox bottomPane){
        switch (this) {
            case FLAPPYBIRD:
                RegularBird flappyGame = new RegularBird(gamePane, bottomPane);
                return flappyGame;

            case MULTIPLAYERFLAPPYBIRD:
                MultiplayerBird multiFlap = new MultiplayerBird(gamePane, bottomPane);
                return multiFlap;

            case SMARTFLAPPYBIRD:
                SmartBird smartFlap = new SmartBird(gamePane, bottomPane);
                return smartFlap;

            case DOODLEJUMP:
                DoodleJump doodleJump = new DoodleJump(gamePane, bottomPane);
                return doodleJump;

            case CARTOON:
                Cartoon turtoon = new Cartoon(gamePane, bottomPane);
                return turtoon;

            default:
                return null;
        }
    }


    }

