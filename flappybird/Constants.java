package evolution.flappybird;

/**
 * CONSTANTS used for the flappyBird parent, and its children games
 */
public class Constants {


    public static final int GAMEPANE_WIDTH = 500;
    public static final int GAMEPANE_HEIGHT = 500;

    // to take into account 50 pixels from the top
    public static final int PIPE_GAP_POSITION_MAX = GAMEPANE_HEIGHT -450;
    // to take into account 50 pixels from the bottom
    public static final int PIPE_GAP_POSITION_MIN = GAMEPANE_HEIGHT -170;
    public static final int PIPE_GAP = 120;

    public static final int BIRDINITIAL_Y = 100;
    public static final int BIRDINITIAL_X = 100;
    public static final int BIRD_RADIUS = 10;

    public static final int EYEOFFSET_X = 4;
    public static final int EYEOFFSET_Y = 2;
    public static final int EYE_RADIUS = 2;

    public static final double GRAVITY = 1000; // acceleration constant (UNITS: pixels/s^2)
    public static final double REBOUND_VELOCITY = -350; // initial jump velocity (UNITS: pixels/s)
    public static final double DURATION = 0.016; // KeyFrame duration (UNITS: s)

    //max velocity calculated from when a bird falls from the top of screen
    public static final int VELOCITY_NORMALIZER = 994;

    public static final int PIPE_WIDTH = 50;
    public static final int PIPE_INITIAL_X = 400;
    public static final int PIPE_TOP_INITIAL_Y = 0;

    public static final int DISTANCE_BETWEEN_PIPES = 250;

    public static final double MUTATION_RATE = 0.95; //this is a 5% mutation rate
}
