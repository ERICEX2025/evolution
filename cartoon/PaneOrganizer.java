package evolution.cartoon;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * top level graphics class that has root BorderPane and manages the main Panes
 * Root BorderPane contains turtlePane at the center and topPane at the top
 * Contains Cartoon
 */
public class PaneOrganizer {

    private BorderPane root;

    /**
     * instantiates root BorderPane
     * calls the methods setupBackground, createTopPane
     * creates turtlePane sets it to the center of root
     * creates cartoon passing in turtlePane
     */
    public PaneOrganizer(Stage stage, BorderPane rootPane) {
        this.root = rootPane;

        //background image method
        this.setupBackgroundPic();

        //topPane
        this.createTopPane();

        //centerPane
        Pane turtlePane = new Pane();
        this.root.setCenter(turtlePane);

        //delegates Pane in the center of root to the cartoon class
        new Cartoon(turtlePane);
        stage.sizeToScene();
    }


    /**
     * adds an image to the root BorderPane
     */
    private void setupBackgroundPic()
    {
        Image image = new Image(this.getClass().getResourceAsStream("VioletScene.jpg"));
        ImageView iv = new ImageView(image);
        iv.setFitWidth(Constants.APP_WIDTH);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);
        this.root.getChildren().add(iv);
    }

    /**
     * creates Hbox to put in the top Pane
     * creates and puts quit button into Hbox
     */
    private void createTopPane(){
        HBox topPane = new HBox();
        Button button = new Button("Quit");
        topPane.getChildren().add(button);
        button.setOnAction((ActionEvent e) -> System.exit(0));

        //so key inputs work
        button.setFocusTraversable(false);
        topPane.setFocusTraversable(false);

        this.root.setTop(topPane);
    }

    //for App to get root and put in the argument of scene
    public Pane getRoot(){
        return this.root;
    }
}
