
package com.pcc.sgs.controller;

import com.pcc.sgs.dao.GradeDao;
import com.pcc.sgs.dao.MyClassDao;
import com.pcc.sgs.dao.StudentDao;
import com.pcc.sgs.helper.CurrentUser;
import com.pcc.sgs.helper.SceneName;
import com.pcc.sgs.model.Grade;
import com.pcc.sgs.model.Student;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class GradeDashController {

    @FXML
    private Label title;
    @FXML
    private Label date;
    @FXML
    private Label userInfo;
    @FXML
    private Label classNumber;
    @FXML
    private Label studentNumber;
    @FXML
    private TableView<Student> studentTableView;
    @FXML
    private TableColumn<Student, Long> studentIdColumn;
    @FXML
    private TableColumn<Student, String> studentFirstNameColumn;
    @FXML
    private TableColumn<Student, String> studentMiddleNameColumn;
    @FXML
    private TableColumn<Student, String> studentLastNameColumn;
    @FXML
    private TableColumn<Student, String> studentCourseNameColumn;
    @FXML
    private TableColumn<Student, String> studentYearLevelColumn;
    @FXML
    private TableView<Grade> gradeTableView;
    @FXML
    private TableColumn<Grade, String> gradeIdColumn;
    @FXML
    private TableColumn<Grade, String> gradeClassCodeColumn;
    @FXML
    private TableColumn<Grade, String> gradeClassTitleColumn;
    @FXML
    private TableColumn<Grade, String> gradeClassSchedule;
    @FXML
    private TableColumn<Grade, String> prelimGradeColumn;
    @FXML
    private TableColumn<Grade, String> midtermGradeColumn;
    @FXML
    private TableColumn<Grade, String> semiFinalGradeColumn;
    @FXML
    private TableColumn<Grade, String> finalGradeColumn;
    @FXML
    private TableColumn<Grade, String> finalEquivalentColumn;
    
    MyClassDao myClassDao = new MyClassDao();
    StudentDao studentDao = new StudentDao();
    private Student selectedStudent;
    private GradeDao gradeDao = new GradeDao();

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        setTexts();
        fillTableWithData();
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
            gradeTableView.getItems().clear();
            // Add students from the selected class to Student TableView
            if (newValue != null) {
                List<Grade> gradeList = this.gradeDao.getGradesByStudent(newValue);
                ObservableList<Grade> grades = FXCollections.observableArrayList();
                grades.addAll(gradeList);
                gradeTableView.getItems().addAll(grades);
                updateGradeTable(grades);
            }
        });
    }
    
    private void updateGradeTable(ObservableList<Grade> grades) {
        if (studentTableView.getSelectionModel().getSelectedItem() != null) {
            this.selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
            gradeIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            gradeClassCodeColumn.setCellValueFactory((CellDataFeatures<Grade, String> p) -> new SimpleStringProperty(p.getValue().getMyClass().getCourseCode()));
            gradeClassTitleColumn.setCellValueFactory((CellDataFeatures<Grade, String> p) -> new SimpleStringProperty(p.getValue().getMyClass().getCourseName()));
            gradeClassSchedule.setCellValueFactory((CellDataFeatures<Grade, String> p) -> new SimpleStringProperty(p.getValue().getMyClass().getSchedule()));
            prelimGradeColumn.setCellValueFactory(new PropertyValueFactory<>("prelimEquivalent"));
            midtermGradeColumn.setCellValueFactory(new PropertyValueFactory<>("midtermEquivalent"));
            semiFinalGradeColumn.setCellValueFactory(new PropertyValueFactory<>("semifinalEquivalent"));
            finalGradeColumn.setCellValueFactory(new PropertyValueFactory<>("finalEquivalent"));
            finalEquivalentColumn.setCellValueFactory(new PropertyValueFactory<>("finalGradeEquivalent"));
            gradeTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            gradeTableView.setItems(grades);
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
        SceneController.getGradesScene(event);
    }

    @FXML
    void exitProgram(ActionEvent event) {
        SceneController.close(event);
    }
    
    void refreshScreen(ActionEvent event) throws IOException {
        SceneController.getMyStudentsScene(event);
    }
    
}
