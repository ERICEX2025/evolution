package evolution.Arcade;

import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;

public interface Game {



    void updateGame();

    double setDuration();

    boolean checkForGameOver();

    void restart();

    void keyHandler(KeyEvent e);

    void setTimeline(Timeline timeline);

}
