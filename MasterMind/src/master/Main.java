package master;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
public class Main extends Application {
    public void start(Stage primaryStage) {
GameController controller = new GameController();
primaryStage.setTitle("Masrter Mind ⌛");
primaryStage.setResizable(false);
controller.startTimer();
VBox layout = controller.createGameLayout();
 Scene scene = new Scene(layout,900,700);
 scene.getStylesheets().add(getClass().getResource("CSSMaster.css").toExternalForm());
 primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("master.png")));
 primaryStage.setScene(scene);
 primaryStage.show();


    }
    public static void main(String[] args) {
launch(args);

    }
}