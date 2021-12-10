package evolution.cartoon;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Creates Rain,
 * moves rain and get rain y location methods
 */
public class Rain {
    private int rainStartY;
    private int rainEndY;
    private Line rain;

    /**
     * sets up rain with y length
     */
    public Rain (){

        this.rain = new Line();

        this.rainStartY = -30;
        this.rainEndY = -20;
        this.rain.setStartY(this.rainStartY);
        this.rain.setEndY(this.rainEndY);

        //makes every raindroplet with a random x position
        int random2 = (int)(Math.random() * 1281);
        this.rain.setStartX(random2);
        this.rain.setEndX(random2);

        this.rain.setStrokeWidth(5);
        this.rain.setStroke(Color.valueOf(Constants.RAIN_COLOR));
    }

    //returns line so raindrops can be added graphically in middlePane
    public Line getRain(){
        return this.rain;
    }

    //to move rain
    public void moveRain(){
        this.rain.setStartY(this.rainStartY += 1);
        this.rain.setEndY(this.rainEndY += 1);
    }

    /**
     * used to return y value of an instance of rain so that it can be compared and deleted once it reaches a
     * certain y value
     */
    public int getRainStartY(){
        return this.rainStartY;
    }



}
