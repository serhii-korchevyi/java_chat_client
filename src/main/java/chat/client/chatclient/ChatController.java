package chat.client.chatclient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatController {

    SocketService socketService;

    @FXML
    private AnchorPane chatScene;

    @FXML
    private TextField inputMessageField;

    @FXML
    private TextArea messagesArea;

    @FXML
    private Button sendBtn;

    @FXML
    void initialize() throws IOException, InterruptedException {
        this.socketService = new SocketService();
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
        this.socketService.close();
        stage.close();
    }

}
