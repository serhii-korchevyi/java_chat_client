package chat.client.chatclient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.imageio.IIOParam;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class SignUpController extends AbstractController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private Button signInBtn;

    @FXML
    private Button signUpBtn;

    @FXML
    void initialize() {

        signInBtn.setOnAction(ActionEvent -> {
            signUpBtn.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignIn.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Stage stage = new Stage();
            stage.setMinWidth(400);
            stage.setMinHeight(400);
            stage.setScene(scene);

            stage.setOnCloseRequest(event -> {
                event.consume();
                AbstractController controller = loader.getController();
                controller.exit(stage);
            });

            stage.show();
        });

        signUpBtn.setOnAction(ActionEvent -> {

            HashMap <String, String> credentials = new HashMap<>();

            credentials.put("login", loginField.getText().trim());
            credentials.put("password", passwordField.getText().trim());
            credentials.put("first_name", firstNameField.getText().trim());
            credentials.put("last_name", lastNameField.getText().trim());

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String jsonString = objectMapper.writeValueAsString(credentials);

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8111/sign-up"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                        .build();

                HttpResponse response = null;
                try {
                    response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

                    signUpBtn.getScene().getWindow().hide();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("SignIn.fxml"));
                    Scene scene = null;
                    try {
                        scene = new Scene(loader.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Stage stage = new Stage();
                    stage.setMinWidth(400);
                    stage.setMinHeight(400);
                    stage.setScene(scene);

                    stage.setOnCloseRequest(event -> {
                        event.consume();
                        AbstractController controller = loader.getController();
                        controller.exit(stage);
                    });

                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(response.body());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
