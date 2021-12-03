package evolution.doodlejump;


import javafx.scene.paint.Color;

/**
 * subclass of platform inherits, platform's methods
 */
public class Black extends Platform {

    private Color color;

    /**
     * sets up black platform
     * @param PosX
     * @param PosY
     */
    public Black(double PosX, double PosY) {
        super(PosX, PosY);
        this.color = Color.BLACK;
        this.getPlatform().setFill(this.color);

    }

    /**
     * return color, used to see which color the platform is in the arrays
     * @return
     */
    @Override
    public Color getColor(){
        return this.color;
    }

}


