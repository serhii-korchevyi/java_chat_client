package chat.client.chatclient;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatController extends AbstractController {

    SocketService socketService;

    @FXML
    private AnchorPane chatScene;

    @FXML
    private TextField inputMessageField;

    @FXML
    private VBox messagesArea;

    @FXML
    private Button sendBtn;


    @FXML
    void initialize() {
        this.socketService = new SocketService();

        this.socketService.setMessageListener(message -> {
            System.out.println("Controller received message: " + message);

            // Обновляем UI — нужно выполнять в JavaFX Application Thread
            javafx.application.Platform.runLater(() -> {
                TextArea messageBox = new TextArea(message);
                messageBox.setWrapText(true);
                messageBox.setEditable(false);
                this.messagesArea.getChildren().add(messageBox);
            });
        });

        socketService.start();

        sendBtn.setOnAction(actionEvent -> {
            String inputMessage = inputMessageField.getText().trim();
            System.out.println(inputMessage);
            try {
                this.socketService.sendMessage(inputMessage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void exit(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Do you want to logout?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            this.socketService.close();
            Stage currentStage = (Stage) stage.getScene().getWindow();
            currentStage.close();
        }
    }

}
