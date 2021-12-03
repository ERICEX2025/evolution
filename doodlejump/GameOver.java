package evolution.doodlejump;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * game over label class
 */
public class GameOver {


    /**
     * sets up everything associates with Pane
     * @param myPane
     */

    public GameOver(Pane myPane){
        Label gameOver = new Label("Game Over");
        Rectangle rect = new Rectangle(Constants.BACK_RECT_X,
                Constants.BACK_RECT_Y, Constants.BACK_RECT_WIDTH, Constants.BACK_RECT_HEIGHT);
        rect.setFill(Color.WHITE);
        gameOver.setLayoutX(Constants.BACK_LABEL_LAYOUT_X);
        gameOver.setLayoutY(Constants.BACK_LABEL_LAYOUT_Y);
        myPane.getChildren().addAll(rect, gameOver);

    }
}
