package evolution.arcade;

/**
 * interface for each individual Game and has all the generic methods all the games may use.
 */

import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;

public interface Game {

    void updateGame();

    double setDuration(); //if the game has its own keyframe duration

    boolean checkForGameOver();

    void restart();

    void keyHandler(KeyEvent e); //if the game needs a keyhandler

    void setTimeline(Timeline timeline); //if the game needs to set the timeline rate

}
