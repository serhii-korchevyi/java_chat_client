package chat.client.chatclient;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

abstract class AbstractController {

    public void exit(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Do you want to logout?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage currentStage = (Stage) stage.getScene().getWindow();
            currentStage.close();
        }
    }
}
