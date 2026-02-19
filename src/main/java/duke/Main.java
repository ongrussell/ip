package duke;

import java.io.IOException;

import duke.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A GUI for Suu using FXML.
 */
public class Main extends Application {
    private final Suu suu = new Suu();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Suu");

            MainWindow controller = fxmlLoader.getController();
            controller.setSuu(suu);

            stage.setMinHeight(220);
            stage.setMinWidth(417);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
