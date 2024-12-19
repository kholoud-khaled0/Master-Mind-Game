package master;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        welcomeScreen.display(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
