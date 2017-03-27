package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {
    public Stage primaryStage;

    @FXML private TreeView<File> projectTreeView;
    //@FXML private TextArea editor;
    public void initialize() {
    }

    public void onExit(ActionEvent event) {
        System.exit(0);
    }

    public void onOpenFolder(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(primaryStage);
        File fileSelected = new File(mainDirectory.getPath());
        File[] files = fileSelected.listFiles();

        TreeItem<File> root = new TreeItem<>(fileSelected);
        root.setExpanded(true);
        root.getChildren().add(new TreeItem<>(files[0]));
        root.getChildren().add(new TreeItem<>(files[1]));
        root.getChildren().add(new TreeItem<>(files[2]));
        projectTreeView.setRoot(root);
    }

    public void onSaveFile(ActionEvent event) {
        //Save it by writing to the same file, overwritting.
    }
}
