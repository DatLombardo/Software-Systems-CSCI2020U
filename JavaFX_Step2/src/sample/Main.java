package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

<<<<<<< HEAD
        primaryStage.setTitle("Lab 04 Interface");
        primaryStage.setScene(new Scene(root, 400, 300));
=======
        primaryStage.setTitle("JavaFX - Demo 2");
        primaryStage.setScene(new Scene(root, 800, 600));
>>>>>>> f470139d263986286f1c2614aeb3f2fca9124b3b
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
