package com.example.demo4;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final int PORT = 1024;
    private static final String HOST = "localhost";
    private Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    private void showAlert(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setHeaderText(null);
        dialog.setContentText(message);
        dialog.showAndWait();
    }
    public Client() {
        try {
            socket = new Socket(HOST, PORT);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (
                IOException e) {
            showAlert("Could not connect to server");
            Platform.exit();
        }

    }

}
