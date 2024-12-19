package master;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameController {
    private static final int CODE_LENGTH = 4;
    private int MAX_ATTEMPTS;
    private List<Color> colors = Arrays.asList(
            Color.CORNFLOWERBLUE, Color.GREEN, Color.PINK, Color.PURPLE, Color.BLUE, Color.RED
    );
    private List<Color> secretCode;
    private int attempts;
    private Color[] guesses = new Color[CODE_LENGTH];
    private VBox mainLayout;
    private GridPane guessGrid;
    private Label feedbackLabel;
    private int seconds = 0;
    private int minutes = 0;
    private Timeline timer;
    private Label timerLabel;
    private Label resultLabel;

    public GameController(int maxAttempts) {
        this.attempts = 0;
        this.MAX_ATTEMPTS = maxAttempts;
        generateSecretCode();
        startTimer(); // Start the timer when the game controller is initialized
    }

    public VBox createGameLayout() {
        mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        timerLabel = new Label("00:00");
        timerLabel.setId("timerLabel");
        resultLabel = new Label();
        resultLabel.setFont(new javafx.scene.text.Font("Verdana", 18));
        resultLabel.setTextFill(Color.BLACK);
        Label titleLabel = new Label("✨ Welcome to MasterMind ✨");
        titleLabel.setFont(new javafx.scene.text.Font("Arial", 24));
        titleLabel.setTextFill(Color.BLACK);
        Label instructionsLabel = new Label("Select 4 colors to uncover the secret code.\nYou have " + MAX_ATTEMPTS + " attempts to guess correctly.");
        instructionsLabel.setFont(new javafx.scene.text.Font("Verdana", 14));
        instructionsLabel.setTextFill(Color.BLACK);
        guessGrid = new GridPane();
        guessGrid.setHgap(19);
        guessGrid.setVgap(19);
        guessGrid.setAlignment(Pos.CENTER);
        for (int i = 0; i < CODE_LENGTH; i++) {
            Button colorButton = new Button("Pick Color");
            colorButton.setPrefSize(125, 55);
            colorButton.setId("colorButton");
            int index = i;
            colorButton.setOnAction(e -> pickColor(colorButton, index));
            guessGrid.add(colorButton, i, 0);
        }
        Button submitButton = new Button("Submit Guess");
        submitButton.setId("submitButton");
        submitButton.setOnAction(e -> submitGuess());
        feedbackLabel = new Label();
        feedbackLabel.setFont(new javafx.scene.text.Font("Verdana", 14));
        feedbackLabel.setTextFill(Color.BLACK);
        HBox timerBox = new HBox(10);
        timerBox.setAlignment(Pos.TOP_LEFT);
        timerBox.getChildren().add(timerLabel);

        mainLayout.getChildren().addAll(titleLabel, instructionsLabel, timerBox, guessGrid, submitButton, feedbackLabel, resultLabel);
        return mainLayout;
    }

    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            seconds++;
            if (seconds == 60) {
                seconds = 0;
                minutes++;
            }
            updateTimerLabel();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void updateTimerLabel() {
        String time = String.format("%02d:%02d", minutes, seconds);
        timerLabel.setText(time);
    }

    private void generateSecretCode() {
        secretCode = new ArrayList<>(colors);
        Collections.shuffle(secretCode);
        secretCode = secretCode.subList(0, CODE_LENGTH);
    }

    private void pickColor(Button button, int index) {
        Color chosenColor = chooseRandomColor();
        button.setStyle("-fx-background-color: " + toHexString(chosenColor) + ";");
        guesses[index] = chosenColor;
    }

    private Color chooseRandomColor() {
        Random random = new Random();
        return colors.get(random.nextInt(colors.size()));
    }

    private String toHexString(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);
        return String.format("#%02X%02X%02X", r, g, b);
    }

    private void submitGuess() {
        if (attempts < MAX_ATTEMPTS) {
            boolean allGuessesFilled = true;
            for (Color guess : guesses) {
                if (guess == null) {
                    allGuessesFilled = false;
                    break;
                }
            }
            if (!allGuessesFilled) {
                feedbackLabel.setText("Hmm, not quite (●'◡'●) ! Give it another shot!");
                feedbackLabel.setTextFill(Color.RED);
                return;
            }
            String feedback = giveFeedback(guesses);
            feedbackLabel.setText(feedback);
            attempts++;
            if (isCorrectGuess(guesses)) {
                feedbackLabel.setText("🎊Well done! You've cracked the code!🎊");
                feedbackLabel.setTextFill(Color.GREEN);
                endGame(true);
            } else if (attempts == MAX_ATTEMPTS) {
                String secretColors = getSecretCodeAsString(secretCode);
                feedbackLabel.setText("Oops😓! You’ve run out of attempts. The code was: \n " + secretColors);
                feedbackLabel.setTextFill(Color.web("#FF0000"));
                endGame(false);
            }
        }
    }

    private void endGame(boolean isWin) {
        timer.stop();
        if (isWin) {
            resultLabel.setText("You won in " + String.format("%02d:%02d", minutes, seconds));
            resultLabel.setTextFill(Color.BLACK);
        } else {
            resultLabel.setText("You lost after " + String.format("%02d:%02d", minutes, seconds));
            resultLabel.setTextFill(Color.web("#FF0000"));
        }
    }

    private String giveFeedback(Color[] guessColors) {
        int correctPosition = 0;
        int correctColor = 0;
        for (int i = 0; i < CODE_LENGTH; i++) {
            if (guessColors[i] != null && guessColors[i].equals(secretCode.get(i))) {
                correctPosition++;
            } else if (guessColors[i] != null && secretCode.contains(guessColors[i])) {
                correctColor++;
            }
        }

        return "Correct position: " + correctPosition + ", Correct color but wrong position: " + correctColor;
    }

    private boolean isCorrectGuess(Color[] guessColors) {
        return Arrays.equals(guessColors, secretCode.toArray());
    }

    private String getSecretCodeAsString(List<Color> secretCode) {
        StringBuilder sb = new StringBuilder();
        for (Color color : secretCode) {
            if (color == Color.CORNFLOWERBLUE) {
                sb.append("Cornflower Blue, ");
            } else if (color == Color.GREEN) {
                sb.append("Green, ");
            } else if (color == Color.PINK) {
                sb.append("Pink, ");
            } else if (color == Color.PURPLE) {
                sb.append("Purple, ");
            } else if (color == Color.BLUE) {
                sb.append("Blue, ");
            } else if (color == Color.RED) {
                sb.append("Red, ");
            }
        }
        return sb.substring(0, sb.length() - 2);  // Remove trailing comma and space
    }
}
