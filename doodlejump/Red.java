package evolution.doodlejump;


import javafx.scene.paint.Color;

/**
 * subclass of platform inherits, platform's methods
 */
public class Red extends Platform {

    private Color color;

    /**
     * sets up red platform
     * @param PosX
     * @param PosY
     */
    public Red(double PosX, double PosY) {
        super(PosX, PosY);
        this.color = Color.RED;
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