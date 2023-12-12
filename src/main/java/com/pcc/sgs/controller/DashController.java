package com.pcc.sgs.controller;


import com.pcc.sgs.dao.MyClassDao;
import com.pcc.sgs.dao.StudentDao;
import com.pcc.sgs.helper.CurrentUser;
import com.pcc.sgs.helper.SceneName;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;

/**
 * @author James Jasper D. Villamor
 */
public class DashController {

    @FXML private Label title;
    @FXML private Label date;
    @FXML private Label classesInfoBlockName;
    @FXML private Label classNumber;
    @FXML private Label studentsInfoBlockName;
    @FXML private Label studentNumber;
    @FXML private Label userInfo;
    
    @FXML
    private void initialize() {
        setTexts();
    }

    private void setTexts() {
        title.setText(SceneName.DASHBOARD.getName());
        date.setText(LocalDate.now().toString());
        classNumber.setText(getNumberOfClasses());
        studentNumber.setText(getNumberOfStudents());
        studentsInfoBlockName.setText("Students in DB");
        setUserInfo();
    }



    private String getNumberOfClasses() {
        return String.valueOf(new MyClassDao().getMyClassNumber(CurrentUser.getCurrentUser()));
    }

    private String getNumberOfStudents() {
        return String.valueOf(new StudentDao().getStudentNumber());
    }


    private void setUserInfo() {
        userInfo.setText(String.format("Hi! %s", CurrentUser.getCurrentUser().getFirstName()));
    }

    @FXML
    void showClassesScreen(ActionEvent event) throws IOException {
        SceneController.getMyClassesScene(event);
    }

    @FXML
    void showStudentsScreen(ActionEvent event) throws IOException {
        SceneController.getMyStudentsScene(event);
    }
    
    @FXML
    void showGradesScreen(ActionEvent event) throws IOException {
        SceneController.getGradesScene(event);
    }

    @FXML
    void refreshWindow(ActionEvent event) throws IOException {
        SceneController.getMainScene(event);
    }

    @FXML
    void exitProgram(ActionEvent event) {
        SceneController.close(event);
    }
}
