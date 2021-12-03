//package evolution.flappybird;
//
//import javafx.scene.control.Label;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.Pane;
//
//public class PaneOrganizer {
//    private BorderPane root;
//    private Pane gamePane;
//    private Label score;
//
//    /**
//     * sets root as a image
//     * adds score in gamePane which is set to the center
//     * creates bottomPane and sets it to the bottom
//     *
//     */
//    public PaneOrganizer() {
//        this.root = new BorderPane();
//
//        this.gamePane = new Pane();
//        this.gamePane.setPrefSize(Constants.STAGE_WIDTH, Constants.STAGE_HEIGHT);
//
//        this.root.setCenter(gamePane);
//        new FlappyBird(gamePane);
//
//    }
//
//
//    public BorderPane getRoot(){
//        return this.root;
//    }
//}
