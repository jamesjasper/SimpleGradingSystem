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
import javafx.scene.control.TextField;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class SemiFinalCalculatorController {

    @FXML private TextField attendanceScoreTextField;
    @FXML private TextField attendanceTotalTextField;
    @FXML private TextField quizScoreTextField;
    @FXML private TextField quizTotalTextField;
    @FXML private TextField assignmentScoreTextField;
    @FXML private TextField assignmentTotalTextField;
    @FXML private TextField examScoreTextField;
    @FXML private TextField examTotalTextField;
    @FXML private TextField totalTextField;
    @FXML private TextField equivalentTextField;
    
    private Grade grade = new Grade();
    private Student student;
    private MyClass myClass;
    
    DecimalFormat df = new DecimalFormat("0.00");
    GradeDao gradeDao = new GradeDao();
    Boolean gradeIndatabase = false;
    
    void init (Grade grade, Student student, MyClass myClass) {
        this.grade = grade;
        this.student = student;
        this.myClass = myClass;
        
        if(this.grade.getStudent() != null && this.grade.getMyClass() != null) {
            this.gradeIndatabase = true;
            attendanceScoreTextField.setText(df.format(grade.getSemifinalAttendanceScore()));
            attendanceTotalTextField.setText(df.format(grade.getSemifinalAttendanceTotal()));
            assignmentScoreTextField.setText(df.format(grade.getSemifinalAssignmentScore()));
            assignmentTotalTextField.setText(df.format(grade.getSemifinalAssignmentTotal()));
            quizScoreTextField.setText(df.format(grade.getSemifinalQuizScore()));
            quizTotalTextField.setText(df.format(grade.getSemifinalQuizTotal()));
            examScoreTextField.setText(df.format(grade.getSemifinalExamScore()));
            examTotalTextField.setText(df.format(grade.getSemifinalExamTotal()));
            totalTextField.setText(df.format(grade.getSemifinalTotal()));
            equivalentTextField.setText(df.format(grade.getSemifinalEquivalent()));
        } else {
            this.gradeIndatabase = false;
            attendanceScoreTextField.setText(df.format(grade.getSemifinalAttendanceScore()));
            attendanceTotalTextField.setText(df.format(grade.getSemifinalAttendanceTotal()));
            assignmentScoreTextField.setText(df.format(grade.getSemifinalAssignmentScore()));
            assignmentTotalTextField.setText(df.format(grade.getSemifinalAssignmentTotal()));
            quizScoreTextField.setText(df.format(grade.getSemifinalQuizScore()));
            quizTotalTextField.setText(df.format(grade.getSemifinalQuizTotal()));
            examScoreTextField.setText(df.format(grade.getSemifinalExamScore()));
            examTotalTextField.setText(df.format(grade.getSemifinalExamTotal()));
            totalTextField.setText(df.format((grade.getSemifinalTotal())));
            equivalentTextField.setText(df.format(grade.getSemifinalEquivalent()));
        }
    }
    
    @FXML
    void calculate() {
        GradeCalculator calculator = new GradeCalculator();
        this.grade.setSemifinalAttendanceScore(Double.valueOf(attendanceScoreTextField.getText()));
        this.grade.setSemifinalAttendanceTotal(Double.valueOf(attendanceTotalTextField.getText()));
        this.grade.setSemifinalAttendancePercentage(calculator.calculatePercentage(grade.getSemifinalAttendanceScore(), grade.getSemifinalAttendanceTotal(), 10));
        
        this.grade.setSemifinalQuizScore(Double.valueOf(quizScoreTextField.getText()));
        this.grade.setSemifinalQuizTotal(Double.valueOf(quizTotalTextField.getText()));
        this.grade.setSemifinalQuizPercentage(calculator.calculatePercentage(grade.getSemifinalQuizScore(), grade.getSemifinalQuizTotal(), 30));
        
        this.grade.setSemifinalAssignmentScore(Double.valueOf(assignmentScoreTextField.getText()));
        this.grade.setSemifinalAssignmentTotal(Double.valueOf(assignmentTotalTextField.getText()));
        this.grade.setSemifinalAssignmentPercentage(calculator.calculatePercentage(grade.getSemifinalAssignmentScore(), grade.getSemifinalAssignmentTotal(), 20));
        
        this.grade.setSemifinalExamScore(Double.valueOf(examScoreTextField.getText()));
        this.grade.setSemifinalExamTotal(Double.valueOf(examTotalTextField.getText()));
        this.grade.setSemifinalExamPercentage(calculator.calculatePercentage( grade.getSemifinalExamScore(), grade.getSemifinalExamTotal(), 40));
        
        this.grade.setSemifinalTotal(calculator.calculateTotal(grade.getSemifinalAttendancePercentage(), grade.getSemifinalQuizPercentage(), 
                grade.getSemifinalAssignmentPercentage(), grade.getSemifinalExamPercentage()));
        
        this.grade.setSemifinalEquivalent(calculator.calculateGradeEquivalent(grade.getSemifinalTotal()));
        
        totalTextField.setText(df.format(grade.getSemifinalTotal()));
        equivalentTextField.setText(df.format(grade.getSemifinalEquivalent()));   
    }
    
    @FXML
    private void saveOrUpdate(ActionEvent event) throws IOException {
        if (this.gradeIndatabase == false){
            save(event);
        } else {
            update(event);
        }
    }

    private void update(ActionEvent event) {
        gradeDao.updateGrade(grade);
        delayWindowClose(event);
    }

    private void save(ActionEvent event) {
        this.grade.setStudent(student);
        this.grade.setMyClass(myClass);
        boolean isSaved = gradeDao.createGrade(grade);
        if(isSaved) {
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
