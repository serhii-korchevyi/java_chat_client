package chat.client.chatclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class SignInController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInBtn;

    @FXML
    private Button signUpBtn;

    @FXML
    void initialize() {
        signUpBtn.setOnAction(ActionEvent -> {
            signInBtn.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
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
            stage.show();
        });

        signInBtn.setOnAction(ActionEvent -> {
            HashMap<String, String> credentials = new HashMap<>();

            credentials.put("login", loginField.getText().trim());
            credentials.put("password", passwordField.getText().trim());

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String jsonString = objectMapper.writeValueAsString(credentials);

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8111/sign-in"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                        .build();

                try {
                    HttpResponse response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                    if (response.statusCode() == 200) {
                        signInBtn.getScene().getWindow().hide();

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatApp.fxml"));
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
                        stage.show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

}