package evolution.doodlejump;

/**
 * This is your Constants class. It defines some constants you will need
 * in DoodleJump, using the default values from the demo--you shouldn't
 * need to change any of these values unless you want to experiment. Feel
 * free to add more constants to this class!
 *
 * A NOTE ON THE GRAVITY CONSTANT:
 *   Because our y-position is in pixels rather than meters, we'll need our
 *   gravity to be in units of pixels/sec^2 rather than the usual 9.8m/sec^2.
 *   There's not an exact conversion from pixels to meters since different
 *   monitors have varying numbers of pixels per inch, but assuming a fairly
 *   standard 72 pixels per inch, that means that one meter is roughly 2800
 *   pixels. However, a gravity of 2800 pixels/sec2 might feel a bit fast. We
 *   suggest you use a gravity of about 1000 pixels/sec2. Feel free to change
 *   this value, but make sure your game is playable with the value you choose.
 */
public class Constants {

    /**
     * These are the constants necessary for the stage
     */
    public static final int STAGE_WIDTH = 350;
    public static final int STAGE_HEIGHT = 500;

    /**
     * These are the constants for the quit pane.
     */
    public static final double QUIT_PANE_HEIGHT = 60;
    public static final String QUIT_PANE_COLOR = "-fx-background-color: grey";
    public static final String SCORE_LABEL_TEXT = "Score: ";


    /**
     * These are the constants for the score counter label.
     */
    public static final int SCORE_LABEL_X = STAGE_WIDTH - 50;
    public static final int SCORE_LABEL_Y = 5;

    /**
     * These are the constants for the gravity simulation and the timeline.
     */

    public static final int GRAVITY = 1000; // acceleration constant (UNITS: pixels/s^2)
    public static final int REBOUND_VELOCITY = -600; // initial jump velocity (UNITS: pixels/s)
    public static final int BOUNCY_REBOUND_VELOCITY = -1200; //bouncy platform initial jump velocity (UNITS: pixels/s)
    public static final double DURATION = 0.016; // KeyFrame duration (UNITS: s)

    /**
     * These are the constants for the creation of the platform.
     */
    public static final int PLATFORM_WIDTH = 40; // (UNITS: pixels)
    public static final int PLATFORM_HEIGHT = 10; // (UNITS: pixels)

    /**
     * These are the constants for the creation of the doodle.
     */
    public static final int DOODLE_WIDTH = 20; // (UNITS: pixels)
    public static final int DOODLE_HEIGHT = 40; // (UNITS: pixels)
    public static final double DOODLE_INITIAL_X = STAGE_WIDTH/2 - DOODLE_WIDTH/2;
    public static final double DOODLE_INITIAL_Y = STAGE_HEIGHT - QUIT_PANE_HEIGHT - PLATFORM_HEIGHT - DOODLE_HEIGHT;
    public static final double MOVE = PLATFORM_WIDTH/2;


    /**
     * These are the constants necessary for the generation of the platforms.
     */
    public static final double TOPMOST_X = STAGE_WIDTH/2 - PLATFORM_WIDTH/2;
    public static final double TOPMOST_Y = STAGE_HEIGHT - QUIT_PANE_HEIGHT - PLATFORM_HEIGHT;
    public static final double BUFFER = 30;
    public static final double MAX_Y_BETWEEN_PLATFORMS = 180;

    /**
     * These are the constants for the scrolling mechanism
     */
    public static final double MIDPOINT = (Constants.STAGE_HEIGHT - Constants.QUIT_PANE_HEIGHT)/2;

    /**
     * These are the constants for setting up the drop shadow on the platforms
     */
    public static final double SHADOW_OFFSET_X = 10.0;
    public static final double SHADOW_OFFSET_Y = 10.0;
    public static final double SHADOW_RED = 0.135;
    public static final double SHADOW_GREEN = 0.135;
    public static final double SHADOW_BLUE = 0.135;

    /**
     * These are the constants for setting up the game over message.
     */

    public static final int BACK_RECT_X = Constants.STAGE_WIDTH / 2 - 30;
    public static final double BACK_RECT_Y = (Constants.STAGE_HEIGHT - Constants.QUIT_PANE_HEIGHT) / 2 - 3;
    public static final int BACK_RECT_WIDTH = 72;
    public static final int BACK_RECT_HEIGHT = 25;
    public static final int BACK_LABEL_LAYOUT_X = Constants.STAGE_WIDTH / 2 - 25;
    public static final double BACK_LABEL_LAYOUT_Y = (Constants.STAGE_HEIGHT - Constants.QUIT_PANE_HEIGHT) / 2;
}

