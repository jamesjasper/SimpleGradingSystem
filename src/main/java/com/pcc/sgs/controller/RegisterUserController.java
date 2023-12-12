package com.pcc.sgs.controller;

import com.pcc.sgs.dao.UserDao;
import com.pcc.sgs.helper.PasswordManager;
import com.pcc.sgs.model.User;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author James Jasper D. Villamor
 */
public class RegisterUserController {

    @FXML private Label alertText;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private TextField firstnameTextField;
    @FXML private TextField lastnameTextField;
    private User user;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
        
    UserDao userDao = new UserDao();
    PasswordManager passwordManager = new PasswordManager();
        
    @FXML
    private void register(ActionEvent event) {
        this.username = usernameTextField.getText();
        this.password = passwordTextField.getText();
        this.firstname = firstnameTextField.getText();
        this.lastname = lastnameTextField.getText();
        //check if username is already in database();
        this.user = userDao.getUser(username);
        if(user !=null) {
            alertText.setText("Username already exist!");
        } else {
            this. password = passwordManager.hashPassword(password);
            user = new User(username.toLowerCase(), password, firstname, lastname);
            userDao.createUser(user);
            delayWindowClose(event);
        }
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        SceneController.close(event);
    }
    
    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }
}
