package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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

        // do something with this data

        System.out.println("Register:");
        System.out.println("\tUsername:   " + username);
        System.out.println("\tPassword:  " + password);
        System.out.println("\tFullName:   " + fullName);
        System.out.println("\tE-Mail:     " + email);
        System.out.println("\tPhone #:   " + phone);
        System.out.println("\tDate of Birth:   " + dob);
    }
}
