package duke.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * A custom control consisting of an ImageView to represent the speaker and a label containing text.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        java.util.List<javafx.scene.Node> tmp = new java.util.ArrayList<>(getChildren());
        java.util.Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(javafx.geometry.Pos.TOP_LEFT);
    }

    /**
     * Returns a DialogBox representing the user (user on the right).
     *
     * @param text User text.
     * @param img User image.
     * @return DialogBox for user.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Returns a DialogBox representing Suu (Suu on the left).
     *
     * @param text Suu text.
     * @param img Suu image.
     * @return DialogBox for Suu.
     */
    public static DialogBox getSuuDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}

