package duke.ui;

import java.io.InputStream;
import java.util.Objects;

import duke.Suu;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    private static final Duration EXIT_DELAY = Duration.seconds(2);

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Suu suu;

    private final Image userImage = loadImage("/images/DaUser.png");
    private final Image suuImage = loadImage("/images/DaSuu.png");

    /**
     * Initialises the UI components after the FXML is loaded.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Suu instance.
     *
     * @param s Suu logic instance.
     */
    public void setSuu(Suu s) {
        suu = s;
        dialogContainer.getChildren().add(DialogBox.getSuuDialog(suu.getWelcome(), suuImage));
    }

    /**
     * Creates two dialog boxes (user input + Suu response) and appends them to the dialog container.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        if (input == null || input.trim().isEmpty()) {
            userInput.clear();
            return;
        }

        String response = suu.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getSuuDialog(response, suuImage)
        );

        userInput.clear();

        if (suu.isExit()) {
            userInput.setDisable(true);
            sendButton.setDisable(true);

            PauseTransition delay = new PauseTransition(EXIT_DELAY);
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }

    /**
     * Loads an image resource from the classpath.
     *
     * @param resourcePath Resource path (e.g. "/images/DaUser.png").
     * @return Loaded Image.
     */
    private Image loadImage(String resourcePath) {
        InputStream stream = Objects.requireNonNull(
                getClass().getResourceAsStream(resourcePath),
                "Missing image resource: " + resourcePath
        );
        return new Image(stream);
    }
}
