package chat.client.chatclient;

import java.io.*;
import java.net.Socket;

public class SocketService extends Thread {

    public Socket clientSocket;
    public BufferedReader in;
    public BufferedWriter out;
    public String message;
    private MessageListener listener;

    public void setMessageListener(MessageListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            this.clientSocket = new Socket("localhost", 9234);
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            while (!this.clientSocket.isClosed()) {
                this.getMessage();
            }
        } catch (Exception e) {
            this.close();
        }
    }

    public void sendMessage(String message) throws IOException {
        try {
            this.out.write(message + "\n");
            this.out.flush();
            System.out.println("Send message: " + message);
            if (message.equals("exit")) {
                this.close();
            }
        } catch (IOException e) {
            this.close();
        }
    }

    private void getMessage() throws IOException {
        System.out.println("render");
        String serverMessage = this.in.readLine();
        System.out.println("New message read: " + serverMessage);
        if (serverMessage != null && listener != null) {
            listener.onMessageReceived(serverMessage);
        }
    }

    public void close() {
        try {
            if (this.clientSocket != null) {
                this.clientSocket.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
