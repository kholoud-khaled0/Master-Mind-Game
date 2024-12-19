package master;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WelcomeScreen {
    public void display(Stage primaryStage) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("root");

        // Buttons for difficulty levels
        Button easyButton = new Button("Easy");
        Button mediumButton = new Button("Medium");
        Button hardButton = new Button("Hard");

        // Style buttons
        easyButton.getStyleClass().add("button");
        mediumButton.getStyleClass().add("button");
        hardButton.getStyleClass().add("button");

        // Button actions
        easyButton.setOnAction(e -> startGame(primaryStage, 12));
        mediumButton.setOnAction(e -> startGame(primaryStage, 8));
        hardButton.setOnAction(e -> startGame(primaryStage, 6));

        layout.getChildren().addAll(easyButton, mediumButton, hardButton);

        Scene scene = new Scene(layout, 900, 700);
        scene.getStylesheets().add(getClass().getResource("CSSMaster.css").toExternalForm());

        primaryStage.setTitle("Master Mind ⌛");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("pexels-magda-ehlers-pexels-2660262.jpg")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startGame(Stage primaryStage, int attempts) {
        GameController controller = new GameController(attempts);
        VBox gameLayout = controller.createGameLayout();

        Scene gameScene = new Scene(gameLayout, 900, 700);
        gameScene.getStylesheets().add(getClass().getResource("CSSMaster.css").toExternalForm());

        primaryStage.setScene(gameScene);
    }
}