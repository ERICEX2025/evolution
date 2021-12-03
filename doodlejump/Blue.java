package evolution.doodlejump;


import javafx.scene.paint.Color;

/**
 * subclass of platform inherits, platform's methods
 */
public class Blue extends Platform {

    private double PosX;
    private double PosY;
    private Color color;
    private boolean right;

    /**
     * sets up blue platform
     * @param PosX
     * @param PosY
     */
    public Blue(double PosX, double PosY) {
        super(PosX, PosY);
        this.PosX = PosX;
        this.PosY = PosY;
        this.right = true;


        this.color = Color.BLUE;
        this.getPlatform().setFill(this.color);

    }

    /**
     * return color, used to see which color the platform is in the arrays
     * @return
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * move method
     * blue starts moving right, if it will go off screen, make move in the other direction
     */
    @Override
    public void move() {
        if (this.right == true) {
            if (this.getPosX() + 1 > Constants.STAGE_WIDTH - Constants.PLATFORM_WIDTH) {
                this.right = false;
            }
            if (this.right == true) {
                this.getPlatform().setX(this.PosX++);
            }

        }

        if (this.right == false) {
            if (this.getPosX() - 1 < 0) {
                this.right = true;
            }
            if (this.right == false) {
                this.getPlatform().setX(this.PosX--);
            }

        }
    }

}
