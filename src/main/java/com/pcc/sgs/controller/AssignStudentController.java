package com.pcc.sgs.controller;

import com.pcc.sgs.dao.MyClassDao;
import com.pcc.sgs.dao.StudentDao;
import com.pcc.sgs.helper.CurrentUser;
import com.pcc.sgs.helper.UpdateStatus;
import com.pcc.sgs.model.MyClass;
import com.pcc.sgs.model.Student;
import java.io.IOException;
import java.util.Set;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

/**
 *
 * @author James Jasper D. Villamor
 */
public class AssignStudentController {
    @FXML private TextField studentIdTextField;
    @FXML private TextField firstNameTextField;
    @FXML private TextField middleNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField courseTextField;
    @FXML private Label alertText;
    
    @FXML private TableView<MyClass> classTable;
    @FXML private TableColumn<MyClass, String> classCodeColumn;
    @FXML private TableColumn<MyClass, String> classNameColumn;
    @FXML private TableColumn<MyClass, String> classScheduleColumn;
    
    private Student student;
    
    @FXML
    void init(Student student) {
        this.student = student;
        setStudentText();
        classCodeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        classScheduleColumn.setCellValueFactory(new PropertyValueFactory<>("schedule"));
        classTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        classTable.setItems(getClassObservableList());
    }

    private void setStudentText() {
        studentIdTextField.setText(student.getStudentId());
        firstNameTextField.setText(student.getFirstName());
        middleNameTextField.setText(student.getMiddleName());
        lastNameTextField.setText(student.getLastName());
        courseTextField.setText(student.getCourse());
    }
    
    private ObservableList<MyClass> getClassObservableList() {
        ObservableList<MyClass> classes = FXCollections.observableArrayList();
        classes.addAll(new MyClassDao().getMyClasses(CurrentUser.getCurrentUser()));
        return classes;
    }

    @FXML
    private void assign(ActionEvent event) throws IOException {
        MyClass selectedClass = classTable.getSelectionModel().getSelectedItem();
        Set<MyClass> classes = this.student.getClasses();
        classes.add(selectedClass);
        this.student.setClasses(classes);
        new StudentDao().updateStudent(student);
        UpdateStatus.setIsStudentAssigned(true);
        alertText.setText("Class " + selectedClass.getCourseName()+ " is added!");
        delayWindowClose(event);
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
