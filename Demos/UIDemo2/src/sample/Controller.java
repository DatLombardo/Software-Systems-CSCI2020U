package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private 

    public void initialize() {

    }

    public void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println("login(), username=" + username+
                           ", password="+password);
    }
}
