package com.pcc.sgs.controller;

import com.pcc.sgs.dao.StudentDao;
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
public class EditStudentController {
    
    @FXML private TextField studentIdTextField;
    @FXML private TextField firstNameTextField;
    @FXML private TextField middleNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField addressTextField;
    @FXML private ChoiceBox<String> courseChoiceBox;
    @FXML private ChoiceBox<String> yearLevelChoiceBox;
    @FXML private Label alertText;

    private Student selectedStudent;
    private final StudentDao studentDao = new StudentDao();
    
    @FXML
    void init(Student student) {
        this.selectedStudent = student;
        initializeCourseChoiceBox();
        setYearLevelChoiceBox();
        initializeFields();
    }
    
    private void setYearLevelChoiceBox() {
        yearLevelChoiceBox.setItems(FXCollections.observableArrayList("I", "II", "III", "IV"));
        yearLevelChoiceBox.getSelectionModel().select(this.selectedStudent.getYearLevel());
    }

    private void initializeCourseChoiceBox() {
        courseChoiceBox.setItems(FXCollections.observableArrayList(
                "Bachelor of Science in Information Technology",
                "Bachelor of Science in Business Administration", 
                "Bachelor of Science in Criminology"));
        
        courseChoiceBox.getSelectionModel().select(this.selectedStudent.getCourse());
    }
    
    @FXML
    void initializeFields() {
        studentIdTextField.setText(this.selectedStudent.getStudentId());
        firstNameTextField.setText(this.selectedStudent.getFirstName());
        middleNameTextField.setText(this.selectedStudent.getMiddleName());
        lastNameTextField.setText(this.selectedStudent.getLastName());
        phoneNumberTextField.setText(this.selectedStudent.getPhoneNumber());
        addressTextField.setText(this.selectedStudent.getAddress());
    }
    
    @FXML
    private void update(ActionEvent event) throws IOException {
        if (validateInputs()) {
            assignValuesFromInput();
            studentDao.updateStudent(selectedStudent);
            alertText.setText("Student" + this.selectedStudent.getFirstName() + " " + this.selectedStudent.getLastName() + " is updated!");
            delayWindowClose(event);
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

    private void assignValuesFromInput() {
        this.selectedStudent.setStudentId(studentIdTextField.getText());
        this.selectedStudent.setFirstName(firstNameTextField.getText());
        this.selectedStudent.setMiddleName(middleNameTextField.getText());
        this.selectedStudent.setLastName(lastNameTextField.getText());
        this.selectedStudent.setPhoneNumber(phoneNumberTextField.getText());
        this.selectedStudent.setCourse(courseChoiceBox.getSelectionModel().getSelectedItem());
        this.selectedStudent.setAddress(addressTextField.getText());
        this.selectedStudent.setYearLevel(yearLevelChoiceBox.getSelectionModel().getSelectedItem());
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
