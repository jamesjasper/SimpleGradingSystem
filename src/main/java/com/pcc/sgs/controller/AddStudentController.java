package com.pcc.sgs.controller;

import com.pcc.sgs.dao.StudentDao;
import com.pcc.sgs.helper.UpdateStatus;
import com.pcc.sgs.model.Student;
import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

/**
 *
 * @author James Jasper D. Villamor
 */
public class AddStudentController {
    
    @FXML private TextField studentIdTextField;
    @FXML private TextField firstNameTextField;
    @FXML private TextField middleNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField addressTextField;
    @FXML private ChoiceBox<String> courseChoiceBox;
    @FXML private ChoiceBox<String> yearLevelChoiceBox;
    @FXML private Label alertText;
    
    @FXML
    private void initialize() {
        initializeCourseChoiceBox();
        setYearLevelChoiceBox();
    }

    private void setYearLevelChoiceBox() {
        yearLevelChoiceBox.setItems(FXCollections.observableArrayList("I", "II", "III", "IV"));
        yearLevelChoiceBox.getSelectionModel().selectFirst();
    }

    private void initializeCourseChoiceBox() {
        courseChoiceBox.setItems(FXCollections.observableArrayList(
                "Bachelor of Science in Information Technology",
                "Bachelor of Science in Business Administration", 
                "Bachelor of Science in Criminology"));
        
        courseChoiceBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void save(ActionEvent event) throws IOException {
        if (validateInputs()) {
            Student newStudent = createStudentFromInput();
            boolean isSaved = new StudentDao().createStudent(newStudent);
            if (isSaved) {
                UpdateStatus.setIsStudentAdded(true);
                alertText.setText("Student" + newStudent.getFirstName() + " " + newStudent.getLastName() + " is added!");
                delayWindowClose(event);
            }
        }
    }
    
    private boolean validateInputs() {
        if (studentIdTextField.getText().equals("")) {
            alertText.setText("*Student Id can't be blank!");
            return false;
        }

        if (firstNameTextField.getText().equals("")) {
            alertText.setText("*First Name can't be blank!");
            return false;
        }

        if (lastNameTextField.getText().equals("")) {
            alertText.setText("*Last Name can't be blank!");
            return false;
        }
        
        if (addressTextField.getText().equals("")) {
            alertText.setText("*Address can't be blank!");
            return false;
        }

        return true;
    }

    private Student createStudentFromInput() {
        Student newStudent = new Student();
        newStudent.setStudentId(studentIdTextField.getText());
        newStudent.setFirstName(firstNameTextField.getText());
        newStudent.setMiddleName(middleNameTextField.getText());
        newStudent.setLastName(lastNameTextField.getText());
        newStudent.setPhoneNumber(phoneNumberTextField.getText());
        newStudent.setCourse(courseChoiceBox.getSelectionModel().getSelectedItem());
        newStudent.setAddress(addressTextField.getText());
        newStudent.setYearLevel(yearLevelChoiceBox.getSelectionModel().getSelectedItem());
        return newStudent;
    }

    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        SceneController.close(event);
    }
}
