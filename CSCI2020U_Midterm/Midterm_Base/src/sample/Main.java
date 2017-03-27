package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application implements FileModifiedListener {
    public Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        //Parent root = loader.load();
        //Controller controller = loader.getController();
        //primaryStage.setScene(new Scene(root, 800,800));

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Midterm");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
