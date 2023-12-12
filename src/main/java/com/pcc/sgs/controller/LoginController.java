package com.pcc.sgs.controller;

import com.pcc.sgs.dao.UserDao;
import com.pcc.sgs.helper.CurrentUser;
import com.pcc.sgs.helper.PasswordManager;
import com.pcc.sgs.helper.ScenePath;
import com.pcc.sgs.model.User;
import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

/**
 * @author James Jasper D. Villamor
 */
public class LoginController {

    @FXML private TextField username;

    @FXML private PasswordField password;

    @FXML private Label infoLine;

    @FXML private Label welcome;

    @FXML private Button exitBtn;

    UserDao userDao = new UserDao();

    @FXML
    private void loginUser(ActionEvent event) throws IOException, InterruptedException {

        if(!validFields()) {
            infoLine.setText("Username and password can't be empty!");
            return;
        }

        if (!validateLogin()) {
            infoLine.setText("Invalid username or password not found!");
            return;
        }

        welcome.setText("Welcome, " + CurrentUser.getCurrentUser().getUsername() + "!");
        infoLine.setText("Redirecting to main dashboard...");

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished( event2 -> {
            try {
                SceneController.getMainScene(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delay.play();
    }
    
    @FXML
    void showRegisterWindow (ActionEvent event) throws IOException {
        PopUpWindowController.getPopUpWindow(ScenePath.REGISTER.getPath());
    }
    
//    private void

    boolean validFields() {
        return !username.getText().isEmpty() && !password.getText().isEmpty();
    }

    private boolean validateLogin() {     
        User user = userDao.getConnectedUser(username.getText(), password.getText());
        if (user == null) {
            return false;
        }
        CurrentUser.setCurrentUser(user);
        return true;
    }
    
    @FXML
    private void close() {
        exitBtn.setOnAction(SceneController::close);
    }


}
