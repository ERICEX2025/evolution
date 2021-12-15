package evolution.arcade;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the main class where our Arcade starts. The main method of this application calls launch, a
 * JavaFX method which eventually calls the start method below. In the App class, we also instantiate our
 * gameOrganizer, create, set, and show the stage.
 */

public class App extends Application {

    /**
     * This is the start method, which is called automatically by JavaFX to launch the program. The start method
     * also takes in a stage that is created automatically by Java. Here, we instantiated the gameOrganizer and
     * create, set, and show the stage
     */
    @Override
    public void start(Stage stage) {
        Arcade gameOrganizer = new Arcade(stage);
        Scene scene = new Scene(gameOrganizer.getRoot());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Arcade");
        stage.show();
    }

    /**
     * This is the mainline, which was given by the stencil code.
     */
    public static void main(String[] argv) {
        launch(argv);
    }
}
