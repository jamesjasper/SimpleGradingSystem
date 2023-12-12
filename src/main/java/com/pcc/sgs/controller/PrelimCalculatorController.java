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
 *
 * @author James Jasper D. Villamor
 */
public class PrelimCalculatorController {
    
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
    Boolean isGradeFromDatabase = false;
      
    void init (Grade grade, Student student, MyClass myClass) {
        this.grade = grade;
        this.student = student;
        this.myClass = myClass;
        
        if(this.grade.getStudent() != null && this.grade.getMyClass() != null) {
            this.isGradeFromDatabase = true;
            attendanceScoreTextField.setText(df.format(grade.getPrelimAttendanceScore()));
            attendanceTotalTextField.setText(df.format(grade.getPrelimAttendanceTotal()));
            assignmentScoreTextField.setText(df.format(grade.getPrelimAssignmentScore()));
            assignmentTotalTextField.setText(df.format(grade.getPrelimAssignmentTotal()));
            quizScoreTextField.setText(df.format(grade.getPrelimQuizScore()));
            quizTotalTextField.setText(df.format(grade.getPrelimQuizTotal()));
            examScoreTextField.setText(df.format(grade.getPrelimExamScore()));
            examTotalTextField.setText(df.format(grade.getPrelimExamTotal()));
            totalTextField.setText(df.format(grade.getPrelimTotal()));
            equivalentTextField.setText(df.format(grade.getPrelimEquivalent()));
        } else {
            this.isGradeFromDatabase = false;
            attendanceScoreTextField.setText(df.format(grade.getPrelimAttendanceScore()));
            attendanceTotalTextField.setText(df.format(grade.getPrelimAttendanceTotal()));
            assignmentScoreTextField.setText(df.format(grade.getPrelimAssignmentScore()));
            assignmentTotalTextField.setText(df.format(grade.getPrelimAssignmentTotal()));
            quizScoreTextField.setText(df.format(grade.getPrelimQuizScore()));
            quizTotalTextField.setText(df.format(grade.getPrelimQuizTotal()));
            examScoreTextField.setText(df.format(grade.getPrelimExamScore()));
            examTotalTextField.setText(df.format(grade.getPrelimExamTotal()));
            totalTextField.setText(df.format((grade.getPrelimTotal())));
            equivalentTextField.setText(df.format(grade.getPrelimEquivalent()));
            this.grade.setStudent(student);
            this.grade.setMyClass(myClass);
        }
    }
    
    @FXML
    void calculate() {
        GradeCalculator calculator = new GradeCalculator();
        this.grade.setPrelimAttendanceScore(Double.valueOf(attendanceScoreTextField.getText()));
        this.grade.setPrelimAttendanceTotal(Double.valueOf(attendanceTotalTextField.getText()));
        this.grade.setPrelimAttendancePercentage(calculator.calculatePercentage(grade.getPrelimAttendanceScore(), grade.getPrelimAttendanceTotal(), 10));
        
        this.grade.setPrelimQuizScore(Double.valueOf(quizScoreTextField.getText()));
        this.grade.setPrelimQuizTotal(Double.valueOf(quizTotalTextField.getText()));
        this.grade.setPrelimQuizPercentage(calculator.calculatePercentage(grade.getPrelimQuizScore(), grade.getPrelimQuizTotal(), 30));
        
        this.grade.setPrelimAssignmentScore(Double.valueOf(assignmentScoreTextField.getText()));
        this.grade.setPrelimAssignmentTotal(Double.valueOf(assignmentTotalTextField.getText()));
        this.grade.setPrelimAssignmentPercentage(calculator.calculatePercentage(grade.getPrelimAssignmentScore(), grade.getPrelimAssignmentTotal(), 20));
        
        this.grade.setPrelimExamScore(Double.valueOf(examScoreTextField.getText()));
        this.grade.setPrelimExamTotal(Double.valueOf(examTotalTextField.getText()));
        this.grade.setPrelimExamPercentage(calculator.calculatePercentage( grade.getPrelimExamScore(), grade.getPrelimExamTotal(), 40));
        
        this.grade.setPrelimTotal(calculator.calculateTotal(grade.getPrelimAttendancePercentage(), grade.getPrelimQuizPercentage(), 
                grade.getPrelimAssignmentPercentage(), grade.getPrelimExamPercentage()));
        
        this.grade.setPrelimEquivalent(calculator.calculateGradeEquivalent(grade.getPrelimTotal()));
        
        totalTextField.setText(df.format(grade.getPrelimTotal()));
        equivalentTextField.setText(df.format(grade.getPrelimEquivalent()));   
    }
    
    @FXML
    private void saveOrUpdate(ActionEvent event) throws IOException {
        System.out.println("Grade from Database? " + isGradeFromDatabase);
        if (this.isGradeFromDatabase == false) {
            boolean isSaved = gradeDao.createGrade(grade);
            if(isSaved)
                delayWindowClose(event);
        }else {
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
