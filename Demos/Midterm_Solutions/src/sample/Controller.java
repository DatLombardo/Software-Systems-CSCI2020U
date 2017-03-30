package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;

import java.io.*;

public class Controller {
    @FXML private TreeView<ProjectFile> projectTreeView;
    @FXML private TextArea editor;

    private File selectedFile;
    private FileModifiedListener listener;
    private boolean currentFileModified = false;

    public void initialize() {
        editor.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                currentFileModified = true;
                if (listener != null) {
                    listener.onFileModified(selectedFile, true);
                }
            }
        });

        projectTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                // identify which file has been selected
                TreeItem<ProjectFile> selectedItem = (TreeItem<ProjectFile>) newValue;
                if (selectedItem != null) {
                    selectedFile = selectedItem.getValue().getFile();
                    System.out.println(selectedFile);

                    // load the contents of the file into the editor
                    String content = readFileContents(selectedFile);
                    editor.setText(content);

                    // update the window title
                    if (listener != null) {
                        listener.onFileModified(selectedFile, false);
                    }
                }
            }
        });

        // initially, load the current directory
        try {
            File initialDirectory = new File(".").getCanonicalFile();
            TreeItem<ProjectFile> rootItem = new TreeItem<>(new ProjectFile(initialDirectory));
            populateDirectory(initialDirectory, rootItem);
            rootItem.setExpanded(true);
            projectTreeView.setRoot(rootItem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFileModifiedListener(FileModifiedListener listener) {
        this.listener = listener;
    }

    private String readFileContents(File inputFile) {
        StringBuffer buffer = new StringBuffer();

        try {
            String line;
            BufferedReader input = new BufferedReader(new FileReader(inputFile));
            while ((line = input.readLine()) != null) {
                buffer.append(line + "\n");
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public void onOpenFolder(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(projectTreeView.getScene().getWindow());
        TreeItem<ProjectFile> rootItem = new TreeItem<>(new ProjectFile(mainDirectory));
        rootItem.setExpanded(true);

        // create a tree for this directory
        populateDirectory(mainDirectory, rootItem);

        // set this item as the new root
        projectTreeView.setRoot(rootItem);
    }

    private void populateDirectory(File dir, TreeItem<ProjectFile> parentItem) {
        File[] files = dir.listFiles();
        for (File file : files) {
            TreeItem<ProjectFile> fileItem = new TreeItem<>(new ProjectFile(file));
            parentItem.getChildren().add(fileItem);
            if (file.isDirectory()) {
                populateDirectory(file, fileItem);
            }
        }
    }

    public void onSaveFile(ActionEvent event) {
        saveCurrentFile();
    }

    private void saveCurrentFile() {
        String content = editor.getText();

        try {
            PrintWriter output = new PrintWriter(selectedFile);
            output.write(content);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // update the window title
        if (listener != null) {
            listener.onFileModified(selectedFile, false);
        }
    }

    public void onExit(ActionEvent event) {
        if (currentFileModified) {
            saveCurrentFile();
        }
        System.exit(0);
    }
}
