package com.pcc.sgs.controller;

import com.pcc.sgs.dao.MyClassDao;
import com.pcc.sgs.helper.CurrentUser;
import com.pcc.sgs.helper.UpdateStatus;
import com.pcc.sgs.model.MyClass;
import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

/**
 *
 * @author James Jasper D. Villamor
 */
public class AddClassController {
    
    @FXML private TextField courseCodeTextField;
    @FXML private TextField courseNameTextField;
    @FXML private TextField courseScheduleTextField;
    @FXML private Label alertText;

    @FXML
    private void save(ActionEvent event) throws IOException {
        if (validateInputs()) {
            MyClass newClass = createClassFromInput();
            boolean isSaved = new MyClassDao().createMyClass(newClass);
            if (isSaved) {
                UpdateStatus.setIsClassAdded(true);
                alertText.setText("Student" + newClass.getCourseName()+ " is added!");
                delayWindowClose(event);
            }
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

    private MyClass createClassFromInput() {
        MyClass newClass = new MyClass();
        newClass.setCourseCode(courseCodeTextField.getText());
        newClass.setCourseName(courseNameTextField.getText());
        newClass.setSchedule(courseScheduleTextField.getText());
        newClass.setUser(CurrentUser.getCurrentUser());
        return newClass;
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
