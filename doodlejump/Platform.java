package evolution.doodlejump;

import javafx.geometry.Bounds;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * parent class of red green blue and black
 */
public class Platform {


    private Rectangle platform;
    private Color color;

    /**
     * creates rectangle and sets up dropshadow
     * takes in random PosX and Y
     * @param PosX
     * @param PosY
     */
    public Platform(double PosX, double PosY) {
        this.platform = new Rectangle(PosX, PosY, Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT);
        this.setupDropShadow();

    }

    /**
     * getter method for bounds used to check if this platform is being intersectted
     * @return
     */
    public Bounds getBounds() {
        return this.platform.getLayoutBounds();
    }

    /**
     * getter used for spawning relative to the platform before
     * @return
     */
    public int getPosX() {
        return (int) this.platform.getX();
    }

    /**
     * getter used for spawning relative to the platform before
     * @return
     */
    public int getPosY() {
        return (int) this.platform.getY();
    }

    /**
     * getter so rectangles can be deleted
     * @return
     */
    public Rectangle getPlatform(){
        return this.platform;
    }

    /**
     * moves down the amount the doodle is above midpoint
     * @param total
     */
    public void moveDown(double total){
        this.platform.setY(this.platform.getY() + total);
    }

    /**
     * used to check what color platform it is
     * @return
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * used for blue since blue extends it and the declared type being spawned in the array is platform
     *
     */
    public void move(){

    }

    /**
     * sets up dropShadow
     */
    public void setupDropShadow() {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.color(Constants.SHADOW_RED, Constants.SHADOW_GREEN, Constants.SHADOW_BLUE));
        dropShadow.setOffsetX(Constants.SHADOW_OFFSET_X);
        dropShadow.setOffsetY(Constants.SHADOW_OFFSET_Y);
        this.platform.setEffect(dropShadow);
    }
}
