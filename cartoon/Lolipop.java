package evolution.cartoon;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 * makes a Lolipop appear
 */
public class Lolipop {
    private Circle lolipop;
    private Circle insideCircle1;
    private Circle insideCircle2;
    private Circle insideCircle3;
    private Line stick;

    /**
     * setsup Lolipop and the stick and adds graphically
     */
    public Lolipop(Pane root){
        this.setupLolipop();
        this.setupStick();
        root.getChildren().addAll(this.stick, this.lolipop, this.insideCircle1, this.insideCircle2, this.insideCircle3);
    }

    /**
     * sets up the lolipop with layers of different colored circles
     */
    private void setupLolipop(){
        this.lolipop = new Circle(Constants.LOLIPOP_X, Constants.LOLIPOP_Y, Constants.LOLIPOP_RADIUS,
                Color.valueOf(Constants.LOLIPOP_COLOR));
        this.insideCircle1 = new Circle(Constants.LOLIPOP_X, Constants.LOLIPOP_Y, Constants.LOLIPOP_RADIUS - 4,
                Color.valueOf(Constants.LOLIPOP1_COLOR));
        this.insideCircle2 = new Circle(Constants.LOLIPOP_X, Constants.LOLIPOP_Y, Constants.LOLIPOP_RADIUS - 8,
                Color.valueOf(Constants.LOLIPOP2_COLOR));
        this.insideCircle3 = new Circle(Constants.LOLIPOP_X, Constants.LOLIPOP_Y, Constants.LOLIPOP_RADIUS - 12,
                Color.valueOf(Constants.LOLIPOP3_COLOR));

    }

    /**
     * sets up stick
     */
    private void setupStick(){
        this.stick = new Line(Constants.LOLIPOP_X,  Constants.LOLIPOP_Y, Constants.LOLIPOP_X + 26,
                Constants.LOLIPOP_Y + 26);
        this.stick.setStrokeWidth(4);
        this.stick.setStroke(Color.valueOf(Constants.STICK_COLOR));
    }

}
