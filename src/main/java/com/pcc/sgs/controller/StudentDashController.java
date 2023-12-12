
package com.pcc.sgs.controller;

import com.pcc.sgs.dao.MyClassDao;
import com.pcc.sgs.dao.StudentDao;
import com.pcc.sgs.helper.CurrentUser;
import com.pcc.sgs.helper.SceneName;
import com.pcc.sgs.helper.UpdateStatus;
import com.pcc.sgs.model.MyClass;
import com.pcc.sgs.model.Student;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author James Jasper D. Villamor
 */
public class StudentDashController {
    //Labels
    @FXML private Label title;
    @FXML private Label date;
    @FXML private Label classNumber;
    @FXML private Label studentNumber;
    @FXML private Label userInfo;
    //Student Table
    @FXML private TableView<Student> studentTableView;
    @FXML private TableColumn<Student, String> studentIdColumn;
    @FXML private TableColumn<Student, String> studentFirstNameColumn;
    @FXML private TableColumn<Student, String> studentMiddleNameColumn;
    @FXML private TableColumn<Student, String> studentLastNameColumn;
    @FXML private TableColumn<Student, String> studentCourseNameColumn;
    @FXML private TableColumn<Student, String> studentYearLevelColumn;
    //Class Table
    @FXML private TableView<MyClass> classTableView;
    @FXML private TableColumn<MyClass, Long> classIdColumn;
    @FXML private TableColumn<MyClass, String> courseCodeColumn;
    @FXML private TableColumn<MyClass, String> courseNameColumn;
    @FXML private TableColumn<MyClass, String> scheduleColumn;
    //Buttons
    @FXML private Button editStudentButton;
    @FXML private Button deleteStudentButton;
    @FXML private Button assignClassButton;
    @FXML private Button setGradeButton;
    
    private Student selectedStudent;
    private MyClass selectedClass;
    
    MyClassDao myClassDao = new MyClassDao();
    StudentDao studentDao =  new StudentDao();

    @FXML
    private void initialize() {
        setTexts();
        fillTableWithData();
        editStudentButton.disableProperty().bind(Bindings.isEmpty(studentTableView.getSelectionModel().getSelectedItems()));
        deleteStudentButton.disableProperty().bind(Bindings.isEmpty(studentTableView.getSelectionModel().getSelectedItems()));
        assignClassButton.disableProperty().bind(Bindings.isEmpty(studentTableView.getSelectionModel().getSelectedItems()));
        setGradeButton.disableProperty().bind(Bindings.isEmpty(classTableView.getSelectionModel().getSelectedItems()));
    }
    
    private void setTexts() {
        title.setText(SceneName.STUDENTS.getName());
        date.setText(LocalDate.now().toString());
        classNumber.setText(getNumberOfClasses());
        studentNumber.setText(getNumberOfStudents());
        setUserInfo();
    }

    private void fillTableWithData() {
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        studentMiddleNameColumn.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        studentLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        studentCourseNameColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
        studentYearLevelColumn.setCellValueFactory(new PropertyValueFactory<>("yearLevel"));
        studentTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        studentTableView.setItems(getStudentObservableList());
        
        studentTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        studentTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Student> observable, Student oldValue, Student newValue) -> {
            // Clear existing items in Student TableView
            classTableView.getItems().clear();
            // Add students from the selected class to Student TableView
            if (newValue != null) {
                classTableView.getItems().addAll(newValue.getClasses());
                updateClassTable();
            }
        });
    }
    
    private void updateClassTable() {
        if (studentTableView.getSelectionModel().getSelectedItem() != null) {
            this.selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
            ObservableList<MyClass> classes = FXCollections.observableArrayList();
            classes.addAll(this.selectedStudent.getClasses());
            classIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
            courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
            scheduleColumn.setCellValueFactory(new PropertyValueFactory<>("schedule"));
            classTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            classTableView.setItems(classes);
        }
    }
    
    private ObservableList<Student> getStudentObservableList() {
        ObservableList<Student> students = FXCollections.observableArrayList();
        students.addAll(this.studentDao.getStudents());
        
        System.out.println("Student number in database: " + students.size());
        return students;
    }
    
    private String getNumberOfClasses() {
        return String.valueOf( this.myClassDao.getMyClassNumber(CurrentUser.getCurrentUser()));
    }

    private String getNumberOfStudents() {
        return String.valueOf(this.studentDao.getStudentNumber());
    }


    private void setUserInfo() {
        userInfo.setText(String.format("Hi! %s", CurrentUser.getCurrentUser().getFirstName()));
    }
    
    @FXML
    void showCreateStudentWindow(ActionEvent event) throws IOException {
        PopUpWindowController.getCreateStudentWindow();
        if(UpdateStatus.isStudentAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsStudentAdded(false);
        }    
    }
    
    @FXML
    void showEditStudentWindow(ActionEvent event) throws IOException {
        PopUpWindowController.getEditStudentWindow(this.selectedStudent);
        refreshScreen(event);
    }
    
    @FXML
    void deleteStudent(ActionEvent event) throws IOException {
        this.selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
        if(selectedStudent != null) {
            String msg = "Are you sure you want to delete " + this.selectedStudent.getFirstName() + " " + this.selectedStudent.getLastName() + "?";
            
            Alert alert = new Alert(Alert.AlertType.WARNING, msg);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                this.studentDao.deleteStudent(selectedStudent);
                refreshScreen(event);
            }
        }
    }
    
    @FXML
    void showAssignStudentWindow(ActionEvent event) throws IOException {
        this.selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
        
        if (selectedStudent != null) {
            PopUpWindowController.getAssignStudentWindow(selectedStudent);
            if(UpdateStatus.isStudentAssigned()) {
                refreshScreen(event);
                UpdateStatus.setIsStudentAssigned(false);
            }
        }
    }
    
    @FXML
    void showSetGradeWindow(ActionEvent event) throws IOException {
        this.selectedClass = classTableView.getSelectionModel().getSelectedItem();
        if(selectedClass != null)
            PopUpWindowController.getGradeCalculatorWindow(selectedClass, selectedStudent);
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
        SceneController.getMyStudentsScene(event);
    }

    @FXML
    void exitProgram(ActionEvent event) {
        SceneController.close(event);
    }
    
    void refreshScreen(ActionEvent event) throws IOException {
        SceneController.getMyStudentsScene(event);
    }
    
}
