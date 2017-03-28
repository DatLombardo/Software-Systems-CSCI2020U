package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

/**
 * Created by michael on 27/03/17.
 */
public class BullitenClient extends Application{
    private static Socket sock;
    private static String fileName;
    private BorderPane layout;
    private static BufferedReader stdin;
    private static PrintWriter os;
    public String messageSent;


    public void start(Stage primaryStage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Bulletin Board Client");
        TextArea textArea = new TextArea();

        Button buttonSend = new Button("Send");
        buttonSend.setMinWidth(50);

        Button buttonExit = new Button("Exit");
        buttonExit.setMinWidth(50);
        buttonExit.setOnAction(action -> {
            try {
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        });

        GridPane editArea = new GridPane();
        editArea.setPadding(new Insets(10, 10, 10, 10));
        editArea.setVgap(10);
        editArea.setHgap(10);
        textArea.setMinHeight(350);

        Label username = new Label("Username:");
        editArea.add(username, 0, 0);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        editArea.add(usernameField, 1, 0);

        Label message = new Label("Message:");
        editArea.add(message, 0, 1);
        TextField messageField = new TextField();
        messageField.setPromptText("Message");
        editArea.add(messageField, 1, 1);

        buttonSend.setOnAction(action ->{
                String uName = usernameField.getText();
                String mess = messageField.getText();

                messageSent = (uName + mess);

                usernameField.setText("");
                messageField.setText("");
                //DataOutputStream dos = new DataOutputStream();
                os.write(messageSent);
                os.flush();
            });

        layout = new BorderPane();
        layout.setTop(editArea);
        layout.setLeft(buttonSend);
        layout.setBottom(buttonExit);
        Scene scene = new Scene(layout, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        try {
            sock = new Socket("localhost", 3333);
            System.out.println("Connected");
            stdin = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintWriter(sock.getOutputStream());
            launch(args);
        } catch (Exception e) {
            System.err.println("Cannot connect to the server, try again later.");
            sock.close();
            System.exit(1);
        }

        sock.close();
    }


}
