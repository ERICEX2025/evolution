package evolution.arcade;

/**
 * Arcade delegates to this enum class and it handles setting up the buttons and instantiating the games
 */

import evolution.cartoon.Cartoon;
import evolution.doodlejump.DoodleJump;
import evolution.flappybird.MultiplayerBird;
import evolution.flappybird.RegularBird;
import evolution.flappybird.SmartBird;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public enum Games {
    FLAPPYBIRD, MULTIPLAYERFLAPPYBIRD, SMARTFLAPPYBIRD, DOODLEJUMP, CARTOON;

    /**
     * for Creating buttons to return the String for the Button Text
     * @return
     */

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

    /**
     * Handles instantiating each individual game Passing in gamePane and bottompane when the button is pressed for
     * their specific Game and returns it for currentGame
     * @param gamePane
     * @param bottomPane
     * @return
     */

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

