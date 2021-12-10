package evolution.flappybird;

public class Constants {


    public static final int STAGE_WIDTH = 500;
    public static final int STAGE_HEIGHT = 500;

    public static final int PIPE_GAP_POSITION_MAX = STAGE_HEIGHT-450;
    public static final int PIPE_GAP_POSITION_MIN = STAGE_HEIGHT-150;

    public static final int BIRDINITIAL_Y = 100;
    public static final int BIRDINITIAL_X = 100;
    public static final int BIRD_RADIUS = 10;

    public static final int EYEOFFSET_X = 4;
    public static final int EYEOFFSET_Y = 4;
    public static final int EYE_RADIUS = 2;

    public static final double GRAVITY = 1000; // acceleration constant (UNITS: pixels/s^2)
    public static final double REBOUND_VELOCITY = -350; // initial jump velocity (UNITS: pixels/s)
    public static final double DURATION = 0.016; // KeyFrame duration (UNITS: s)
    public static final double MAX_VELOCITY = 978.0 + 350;

    public static final int PIPE_WIDTH = 50;
    public static final int PIPE_INITIAL_X = 400;
    public static final int PIPE_TOP_INITIAL_Y = 0;

    public static final double SELECTION_RATE = 0;
    public static final double MUTATION_RATE = 0;
}
