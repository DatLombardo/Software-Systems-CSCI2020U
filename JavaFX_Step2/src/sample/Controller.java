package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
<<<<<<< HEAD
import java.lang.String;
import java.time.LocalDate;

public class Controller {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private DatePicker datePicker;

    @FXML private TextArea editor;


    public void register(ActionEvent e) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String dob = datePicker.getEditor().getText();
=======

public class Controller {
    @FXML private TextField usernameField;
    @FXML private PasswordField password1Field;
    @FXML private PasswordField password2Field;
    @FXML private TextField emailField;

    @FXML private TreeView<String> projectTreeView;
    @FXML private TextArea editor;

    // the initialize method is automatically invoked by the FXMLLoader - it's magic
    public void initialize() {
        TreeItem<String> rootItem = new TreeItem<>("Project");
        rootItem.setExpanded(true);

        TreeItem<String> src = new TreeItem<>("src");
        src.setExpanded(true);
        rootItem.getChildren().add(src);

        TreeItem<String> main = new TreeItem<>("main");
        main.setExpanded(true);
        src.getChildren().add(main);

        TreeItem<String> java = new TreeItem<>("java");
        java.setExpanded(true);
        main.getChildren().add(java);

        TreeItem<String> helloWorld = new TreeItem<>("HelloWorld.java");
        java.getChildren().add(helloWorld);

        TreeItem<String> gradle = new TreeItem<>("build.gradle");
        rootItem.getChildren().add(gradle);

        projectTreeView.setRoot(rootItem);
        projectTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>)newValue;
                if (selectedItem.getValue().equals("HelloWorld.java")) {
                    editor.setText("public class HelloWorld {\n"+
                            "    public static void main(String[] args) {\n"+
                            "        System.out.println(\"Hello, world!\");"+
                            "    }\n"+
                            "}\n");
                } else if (selectedItem.getValue().equals("build.gradle")) {
                    editor.setText("apply plugin: 'java'");
                }
            }

        });
    }

    public void register(ActionEvent e) {
        String username = usernameField.getText();
        String password1 = password1Field.getText();
        String password2 = password2Field.getText();
        String email = emailField.getText();
>>>>>>> f470139d263986286f1c2614aeb3f2fca9124b3b

        // do something with this data

        System.out.println("Register:");
        System.out.println("\tUsername:   " + username);
<<<<<<< HEAD
        System.out.println("\tPassword:  " + password);
        System.out.println("\tFullName:   " + fullName);
        System.out.println("\tE-Mail:     " + email);
        System.out.println("\tPhone #:   " + phone);
        System.out.println("\tDate of Birth:   " + dob);
=======
        System.out.println("\tPassword1:  " + password1);
        System.out.println("\tPassword2:  " + password2);
        System.out.println("\tE-Mail:     " + email);
>>>>>>> f470139d263986286f1c2614aeb3f2fca9124b3b
    }
}
