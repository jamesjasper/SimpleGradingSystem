package com.pcc.sgs.controller;

import com.pcc.sgs.dao.MyClassDao;
import com.pcc.sgs.helper.CurrentUser;
import com.pcc.sgs.model.MyClass;
import java.io.IOException;
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
public class EditClassController {

    @FXML private Label alertText;
    @FXML private TextField courseCodeTextField;
    @FXML private TextField courseNameTextField;
    @FXML private TextField courseScheduleTextField;
    
    private MyClass selectedClass;
    private MyClassDao myClassDao =  new MyClassDao();

    @FXML 
    void init(MyClass selectedClass) {
        this.selectedClass = selectedClass;
        initializeFields();
    }
    
    @FXML
    void initializeFields() {
        courseCodeTextField.setText(this.selectedClass.getCourseCode());
        courseNameTextField.setText(this.selectedClass.getCourseName());
        courseScheduleTextField.setText(this.selectedClass.getSchedule());
    }
    
    @FXML
    private void update(ActionEvent event) throws IOException {
        if (validateInputs()) {
            updateClassDetailsFromInput();
            this.myClassDao.updateMyClass(this.selectedClass);
            alertText.setText("Student" + this.selectedClass.getCourseName()+ " is updated!");
            delayWindowClose(event);
        }
    }
    
    private boolean validateInputs() {
        if (courseCodeTextField.getText().equals("")) {
            alertText.setText("*Course code can't be blank!");
            return false;
        }

        if (courseNameTextField.getText().equals("")) {
            alertText.setText("*Course name can't be blank!");
            return false;
        }

        if (courseScheduleTextField.getText().equals("")) {
            alertText.setText("*Schedule can't be blank!");
            return false;
        }
        return true;
    }

    private void updateClassDetailsFromInput() {
        this.selectedClass.setCourseCode(courseCodeTextField.getText());
        this.selectedClass.setCourseName(courseNameTextField.getText());
        this.selectedClass.setSchedule(courseScheduleTextField.getText());
        this.selectedClass.setUser(CurrentUser.getCurrentUser());
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
