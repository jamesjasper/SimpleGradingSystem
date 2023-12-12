package com.pcc.sgs.controller;

import com.pcc.sgs.dao.GradeDao;
import com.pcc.sgs.helper.GradeCalculator;
import com.pcc.sgs.model.Grade;
import com.pcc.sgs.model.MyClass;
import com.pcc.sgs.model.Student;
import java.io.IOException;
import java.text.DecimalFormat;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

/**
 *
 * @author James Jasper D. Villamor
 */
public class SetGradeController {
    
    @FXML private Label myClassLabel;
    @FXML private TextField studentIdTextField;
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField courseTextField;
    @FXML private TextField yearLevelTextField;
    @FXML private TextField prelimTotalTextField;
    @FXML private TextField midTermTotalTextField;
    @FXML private TextField semiFinalTotalTextField;
    @FXML private TextField finalTotalTextField;
    @FXML private TextField gwaTotalTextField;
    @FXML private TextField prelimEquivalentTextField;
    @FXML private TextField midTermEquivalentTextField;
    @FXML private TextField semiFinalEquivalentTextField;
    @FXML private TextField finalEquivalentTextField;
    @FXML private TextField gwaEquivalentTextField;
    
    @FXML private Button saveOrUpdateButton;
    
    DecimalFormat df = new DecimalFormat("0.00");
    
    private Student student;
    private MyClass myClass;
    private Grade grade;
    private Boolean isFromDatabase = true;
    
    @FXML
    void init(MyClass selectedClass, Student selectedStudent) {
        this.student = selectedStudent;
        this.myClass = selectedClass;
        System.out.println(selectedClass.getCourseName() + " " + selectedStudent.getFirstName());
        this.grade = new GradeDao().getStudentGradeInClass(selectedStudent, selectedClass);
        if (this.grade == null) {
            isFromDatabase = false;
            this.grade = new Grade();
        }
        myClassLabel.setText(selectedClass.getCourseName());
        setStudentInfo();
        setGradeInfo();
        
        if(this.grade.getPrelimTotal() != 0.00 && this.grade.getMidtermTotal() != 0.00 && this.grade.getSemifinalTotal() != 0.00 && this.grade.getFinalTotal() != 0.00) {
            GradeCalculator calculator = new GradeCalculator();
            this.grade.setFinalGradeGWA(calculator.calculateFinalGWA(this.grade.getPrelimTotal(), this.grade.getMidtermTotal(),
                    this.grade.getSemifinalTotal(), this.grade.getFinalTotal()));
            this.grade.setFinalGradeEquivalent(calculator.calculateGradeEquivalent(this.grade.getFinalGradeGWA()));
            gwaTotalTextField.setText(this.grade.getFinalGradeGWA().toString());
            gwaEquivalentTextField.setText(this.grade.getFinalGradeEquivalent().toString());
            saveOrUpdateButton.setDisable(false);
        }
    }
    
    private void setStudentInfo() {
        studentIdTextField.setText(student.getStudentId());
        firstNameTextField.setText(student.getFirstName());
        lastNameTextField.setText(student.getLastName());
        addressTextField.setText(student.getAddress());
        courseTextField.setText(student.getCourse());
        yearLevelTextField.setText(student.getYearLevel());
    }

    private void setGradeInfo() {
        prelimTotalTextField.setText(df.format(grade.getPrelimTotal()));
        prelimEquivalentTextField.setText(df.format(grade.getPrelimEquivalent()));
        midTermTotalTextField.setText(df.format(grade.getMidtermTotal()));
        midTermEquivalentTextField.setText(df.format(grade.getMidtermEquivalent()));
        semiFinalTotalTextField.setText(df.format(grade.getSemifinalTotal()));
        semiFinalEquivalentTextField.setText(df.format(grade.getSemifinalEquivalent()));
        finalTotalTextField.setText(df.format(grade.getFinalTotal()));
        finalEquivalentTextField.setText(df.format(grade.getFinalEquivalent()));
        gwaTotalTextField.setText(df.format(grade.getFinalGradeGWA()));
        gwaEquivalentTextField.setText(df.format(grade.getFinalGradeEquivalent()));
    }
    
    @FXML 
    private void showPrelimWindow() throws IOException {
        PopUpWindowController.getPrelimGradeCalculatorWindow(grade, student, myClass);
        init(this.myClass,this.student);
    }
    
    @FXML 
    private void showMidtermWindow() throws IOException {
        PopUpWindowController.getMidtermGradeCalculatorWindow(grade, student, myClass);
        init(this.myClass,this.student);
    }
    
    @FXML 
    private void showSemiFinalWindow() throws IOException {
        PopUpWindowController.getSemiFinalGradeCalculatorWindow(grade, student, myClass);
        init(this.myClass,this.student);
    }
    
    @FXML 
    private void showFinalWindow() throws IOException {
        PopUpWindowController.getFinalGradeCalculatorWindow(grade, student, myClass);
        init(this.myClass,this.student);
    }
    
    @FXML
    private void saveOrUpdateGrade(ActionEvent event) throws IOException{
        if(this.isFromDatabase) {
            GradeDao gradeDao = new GradeDao();
            gradeDao.updateGrade(this.grade);
            delayWindowClose(event);
        }
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
