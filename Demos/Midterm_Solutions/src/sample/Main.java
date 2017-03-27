package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;

public class Main extends Application implements FileModifiedListener {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = (Parent)loader.load();
        Controller controller = (Controller)loader.getController();
        controller.setFileModifiedListener(this);

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

        this.primaryStage = primaryStage;
    }

    public void onFileModified(File modifiedFile, boolean contentsModified) {
        String ending = "";
        if (contentsModified) {
            ending = " *";
        }
        this.primaryStage.setTitle(modifiedFile.getName() + ending);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
