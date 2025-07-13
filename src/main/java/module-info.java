module chat.client.chatclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;


    opens chat.client.chatclient to javafx.fxml;
    exports chat.client.chatclient;
}