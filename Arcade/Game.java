package evolution.Arcade;

import javafx.scene.input.KeyEvent;

public interface Game {



    void updateGame();

    double setDuration();

    boolean checkForGameOver();

    void restart();

    void keyHandler(KeyEvent e);

}
