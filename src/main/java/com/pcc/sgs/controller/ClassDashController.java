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
public class ClassDashController {
    @FXML private Label title;
    @FXML private Label date;
    @FXML private Label classNumber;
    @FXML private Label studentNumber;
    @FXML private Label userInfo;
    
    @FXML private TableView<MyClass> classTableView;
    @FXML private TableColumn<MyClass, Long> classIdColumn;
    @FXML private TableColumn<MyClass, String> courseCodeColumn;
    @FXML private TableColumn<MyClass, String> courseNameColumn;
    @FXML private TableColumn<MyClass, String> scheduleColumn;
    
    @FXML private TableView<Student> studentTableView;
    @FXML private TableColumn<Student, String> studentIdColumn;
    @FXML private TableColumn<Student, String> studentFirstNameColumn;
    @FXML private TableColumn<Student, String> studentLastNameColumn;
    @FXML private TableColumn<Student, String> studentCourseNameColumn;
    @FXML private TableColumn<Student, String> studentYearLevelColumn;
    
    @FXML private Button editClassButton;
    @FXML private Button deleteClassButton;
    @FXML private Button removeStudentButton;
    
    MyClassDao myClassDao = new MyClassDao();
    StudentDao studentDao = new StudentDao();
    
    private Student selectedStudent;
    private MyClass selectedClass;
    
    @FXML
    private void initialize() {
        setTexts();
        fillTableWithData();
        setButtons();
        
    }

    private void setButtons() {
        editClassButton.disableProperty().bind(Bindings.isEmpty(classTableView.getSelectionModel().getSelectedItems()));
        deleteClassButton.disableProperty().bind(Bindings.isEmpty(classTableView.getSelectionModel().getSelectedItems()));
        removeStudentButton.disableProperty().bind(Bindings.isEmpty(studentTableView.getSelectionModel().getSelectedItems()));
    }
    
    private void setTexts() {
        title.setText(SceneName.CLASSES.getName());
        date.setText(LocalDate.now().toString());
        classNumber.setText(getNumberOfClasses());
        studentNumber.setText(getNumberOfStudents());
        setUserInfo();
    }

    private void fillTableWithData() {
        classIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        scheduleColumn.setCellValueFactory(new PropertyValueFactory<>("schedule"));
        classTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        classTableView.setItems(getClassObservableList());
        
        classTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        classTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends MyClass> observable, MyClass oldValue, MyClass newValue) -> {
            // Clear existing items in Student TableView
            studentTableView.getItems().clear();
            // Add students from the selected class to Student TableView
            if (newValue != null) {
                studentTableView.getItems().addAll(newValue.getStudents());
                updateStudentTable();
            }
        });
    }
    
    private void updateStudentTable() {
        if (classTableView.getSelectionModel().getSelectedItem() != null) {
            this.selectedClass = classTableView.getSelectionModel().getSelectedItem();
            ObservableList<Student> students = FXCollections.observableArrayList();
            students.addAll(this.selectedClass.getStudents());
            studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
            studentFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            studentLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            studentCourseNameColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
            studentYearLevelColumn.setCellValueFactory(new PropertyValueFactory<>("yearLevel"));
            studentTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            studentTableView.setItems(students);
        }
    }

    private ObservableList<MyClass> getClassObservableList() {
        ObservableList<MyClass> classes = FXCollections.observableArrayList();
        classes.addAll(myClassDao.getMyClasses(CurrentUser.getCurrentUser()));
        System.out.println("Number of classes: " + classes.size());
        return classes;
    }
    
    private ObservableList<Student> getStudentObservableList() {
        ObservableList<Student> students = FXCollections.observableArrayList();
        students.addAll(new StudentDao().getStudents());
        return students;
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
        System.out.println("Changed to Student Scene");
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
    
    @FXML
    void createClassWindow(ActionEvent event) throws IOException {
        PopUpWindowController.getCreateClassWindow();
        if(UpdateStatus.isClassAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsClassAdded(false);
        }    
    }
    
    @FXML
    void showEditClassWindow(ActionEvent event) throws IOException {
        PopUpWindowController.getEditClassWindow(selectedClass);
        refreshScreen(event);
    }
    
    @FXML
    void deleteClass(ActionEvent event) throws IOException {
        this.selectedClass = classTableView.getSelectionModel().getSelectedItem();
        if(selectedClass != null) {
            String msg = "Are you sure you want to delete " + this.selectedClass.getCourseCode() + " - " + this.selectedClass.getCourseName() + " class?";
            
            Alert alert = new Alert(Alert.AlertType.WARNING, msg);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                this.myClassDao.deleteMyClass(selectedClass);
                refreshScreen(event);
            }
        }
    }
    
    @FXML
    void removeStudentFromClass(ActionEvent event) throws IOException {
        this.selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
        if(this.selectedStudent != null) {
            String msg = "Are you sure you want to delete " + this.selectedStudent.getFirstName() + " " + this.selectedStudent.getLastName()+ " from class?";
            Alert alert = new Alert(Alert.AlertType.WARNING, msg);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                this.myClassDao.deleteStudentFromClass(this.selectedClass, this.selectedStudent);
                refreshScreen(event);
            }
        }
    }
    
    @FXML
    void refreshScreen(ActionEvent event) throws IOException {
        SceneController.getMyClassesScene(event);
    }
    

    
}
