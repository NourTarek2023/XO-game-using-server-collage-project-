package com.example.demo4;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
public class HelloApplication extends Application{
    static Client client;
    //private Text winnerText;
    private int playerTurn = 0;
    static Button b1 ;
    static Button b2;
    static Button b3;
    static Button b4;
    static Button b5;
    static Button b6;
    static Button b7;
    static Button b8;
    static Button b9;
    static Button restbtn;
    static Text Start ;
    static ArrayList<Button> buttons;
    static TextField textField;
    static String vch;
    static String ch="x";
    @Override
    public void start(Stage stage) throws IOException {
        Start = new Text("Tic Tac Toe");
        // Start.setFont(Font.font("Cougrette"));
        b1 = new Button();
        b2 = new Button();
        b3 = new Button();
        b4 = new Button();
        b5 = new Button();
        b6 = new Button();
        b7 = new Button();
        b8 = new Button();
        b9 = new Button();
        restbtn = new Button("Restart");
        ///size btns/
        b1.setPrefWidth(75);
        b1.setPrefHeight(75);
        b2.setPrefWidth(75);
        b2.setPrefHeight(75);
        b3.setPrefWidth(75);
        b3.setPrefHeight(75);
        b4.setPrefWidth(75);
        b4.setPrefHeight(75);
        b5.setPrefWidth(75);
        b5.setPrefHeight(75);
        b6.setPrefWidth(75);
        b6.setPrefHeight(75);
        b7.setPrefWidth(75);
        b7.setPrefHeight(75);
        b8.setPrefWidth(75);
        b8.setPrefHeight(75);
        b9.setPrefWidth(75);
        b9.setPrefHeight(75);
        restbtn.setPrefWidth(120);
        restbtn.setPrefHeight(40);
        textField = new TextField();
        //////////////////////////////////
        GridPane gridPane = new GridPane();
        //gridPane.add(Start , 0 , 0 , 3 , 1);
        gridPane.add(textField,0,0,3,1);
        gridPane.add(b1 , 0 , 1 );
        gridPane.add(b2 , 1 , 1 );
        gridPane.add(b3 , 2 , 1 );
        gridPane.add(b4 , 0 , 2 );
        gridPane.add(b5 , 1 , 2 );
        gridPane.add(b6 , 2 , 2 );

        gridPane.add(b7 , 0 , 3 );
        gridPane.add(b8 , 1 , 3 );
        gridPane.add(b9 , 2 , 3 );
        VBox vBox = new VBox(Start,gridPane,restbtn);
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(1);
        gridPane.setHgap(1);
        Scene scene = new Scene(vBox , 500 , 500);
        stage.setTitle("XO Game");
        stage.setScene(scene);
        // initialize();
        stage.show();
        //////applying css/////////////
        scene.getStylesheets().add("styles.css");
        restbtn.getStyleClass().add("rest");
        Start.getStyleClass().add("title");
        //vBox.getStyleClass().add("gridpane");
        //////////////////////////////
        restbtn.setOnAction(this::restartGame);

        initialize(null, null);
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons = new ArrayList<>(Arrays.asList(b1,b2,b3,b4,b5,b6,b7,b8,b9));
        int i=1;
        for (Button button : buttons) {
            setupButton(button, i);
            button.setFocusTraversable(false);
            i++;
        }
    }
    private void setupButton(Button button,int i) {
        button.setOnMouseClicked(mouseEvent -> {
            ch=textField.getText();
            setPlayerSymbol(button);
            button.setDisable(true);
            checkIfGameIsOver();
            try {
                client.out.writeObject(i+"");
                System.out.println(i+"i");
            } catch (IOException e) {
            }
        });
    }
    void restartGame(ActionEvent event) {
        buttons.forEach(this::resetButton);
        Start.setText("Tic-Tac-Toe");
    }

    public void resetButton(Button button) {
        button.setDisable(false);
        button.setText("");
    }
    public void setPlayerSymbol(Button button){
        button.setText(textField.getText());
//        if (playerTurn % 2 == 0) {
//            button.setText("X");
//            playerTurn = 1;
//        } else {
//            button.setText("O");
//            playerTurn = 0;
//        }
    }

    public void checkIfGameIsOver () {
        for (int a = 0; a < 8; a++) {
            String line = switch (a) {
                case 0 -> b1.getText() + b2.getText() + b3.getText();
                case 1 -> b4.getText() + b5.getText() + b6.getText();
                case 2 -> b7.getText() + b8.getText() + b9.getText();
                case 3 -> b1.getText() + b5.getText() + b9.getText();
                case 4 -> b3.getText() + b5.getText() + b7.getText();
                case 5 -> b1.getText() + b4.getText() + b7.getText();
                case 6 -> b2.getText() + b5.getText() + b8.getText();
                case 7 -> b3.getText() + b6.getText() + b9.getText();
                default -> null;
            };
            //X winner
            if (line.equals("XXX")) {
                Start.setText("X won!");
                buttons.forEach(button -> button.setDisable(true));
            }
            //O winner
            else if (line.equals("OOO")) {
                Start.setText("O won!");
                buttons.forEach(button -> button.setDisable(true));
            }
            if (checkTie()){
                Start.setText("Draw");
            }
        }
    }
    public static boolean checkTie() {
        for (Button button : buttons) {
            if (button.getText().isEmpty()) {
                return false;
            }
        }

        return true;
    }
    public static void main(String[] args) {
        HelloApplication h = new HelloApplication();
        client = new Client();

        new Thread(new Runnable() {

            @Override
            public void run() {

                System.out.println("msg");
                if (ch.equals("x")){
                    vch="o";
                }else{
                    vch="x";
                }
                while (true) {
                    try {
                        String pos = (String) client.in.readObject();
                        System.out.println(pos + "msg");
                        Platform.runLater(() -> {
                            switch (pos) {
                                case "1":
                                    b1.setText(vch);
                                    break;
                                case "2":
                                    b2.setText(vch);
                                    break;
                                case "3":
                                    b3.setText(vch);
                                    break;
                                case "4":
                                    b4.setText(vch);
                                    break;
                                case "5":
                                    b5.setText(vch);
                                    break;
                                case "6":
                                    b6.setText(vch);
                                    break;
                                case "7":
                                    b7.setText(vch);
                                    break;
                                case "8":
                                    b8.setText(vch);
                                    break;
                                case "9":
                                    b9.setText(vch);
                                    break;

                            }
                        });
                    } catch (IOException e) {
                        System.out.println(e);
                    } catch (ClassNotFoundException e) {

                    }
                }}
        }).start();
        launch();

        // h.initialize();
    }



}