package evolution.Arcade;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
  *
  */

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Arcade gameOrganizer = new Arcade(stage);
        Scene scene = new Scene(gameOrganizer.getRoot());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Arcade");
        stage.show();
    }

    public static void main(String[] argv) {
        launch(argv);
    }
}
