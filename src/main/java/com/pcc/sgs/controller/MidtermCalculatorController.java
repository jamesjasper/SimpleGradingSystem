/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
 * @author James Jasper D. Villamor
 */
public class MidtermCalculatorController {

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
            attendanceScoreTextField.setText(df.format(grade.getMidtermAttendanceScore()));
            attendanceTotalTextField.setText(df.format(grade.getMidtermAttendanceTotal()));
            assignmentScoreTextField.setText(df.format(grade.getMidtermAssignmentScore()));
            assignmentTotalTextField.setText(df.format(grade.getMidtermAssignmentTotal()));
            quizScoreTextField.setText(df.format(grade.getMidtermQuizScore()));
            quizTotalTextField.setText(df.format(grade.getMidtermQuizTotal()));
            examScoreTextField.setText(df.format(grade.getMidtermExamScore()));
            examTotalTextField.setText(df.format(grade.getMidtermExamTotal()));
            totalTextField.setText(df.format(grade.getMidtermTotal()));
            equivalentTextField.setText(df.format(grade.getMidtermEquivalent()));
        } else {
            this.gradeIndatabase = false;
            attendanceScoreTextField.setText(df.format(grade.getMidtermAttendanceScore()));
            attendanceTotalTextField.setText(df.format(grade.getMidtermAttendanceTotal()));
            assignmentScoreTextField.setText(df.format(grade.getMidtermAssignmentScore()));
            assignmentTotalTextField.setText(df.format(grade.getMidtermAssignmentTotal()));
            quizScoreTextField.setText(df.format(grade.getMidtermQuizScore()));
            quizTotalTextField.setText(df.format(grade.getMidtermQuizTotal()));
            examScoreTextField.setText(df.format(grade.getMidtermExamScore()));
            examTotalTextField.setText(df.format(grade.getMidtermExamTotal()));
            totalTextField.setText(df.format((grade.getMidtermTotal())));
            equivalentTextField.setText(df.format(grade.getMidtermEquivalent()));
        }
    }
    
    @FXML
    void calculate() {
        GradeCalculator calculator = new GradeCalculator();
        this.grade.setMidtermAttendanceScore(Double.valueOf(attendanceScoreTextField.getText()));
        this.grade.setMidtermAttendanceTotal(Double.valueOf(attendanceTotalTextField.getText()));
        this.grade.setMidtermAttendancePercentage(calculator.calculatePercentage(grade.getMidtermAttendanceScore(), grade.getMidtermAttendanceTotal(), 10));
        
        this.grade.setMidtermQuizScore(Double.valueOf(quizScoreTextField.getText()));
        this.grade.setMidtermQuizTotal(Double.valueOf(quizTotalTextField.getText()));
        this.grade.setMidtermQuizPercentage(calculator.calculatePercentage(grade.getMidtermQuizScore(), grade.getMidtermQuizTotal(), 30));
        
        this.grade.setMidtermAssignmentScore(Double.valueOf(assignmentScoreTextField.getText()));
        this.grade.setMidtermAssignmentTotal(Double.valueOf(assignmentTotalTextField.getText()));
        this.grade.setMidtermAssignmentPercentage(calculator.calculatePercentage(grade.getMidtermAssignmentScore(), grade.getMidtermAssignmentTotal(), 20));
        
        this.grade.setMidtermExamScore(Double.valueOf(examScoreTextField.getText()));
        this.grade.setMidtermExamTotal(Double.valueOf(examTotalTextField.getText()));
        this.grade.setMidtermExamPercentage(calculator.calculatePercentage( grade.getMidtermExamScore(), grade.getMidtermExamTotal(), 40));
        
        this.grade.setMidtermTotal(calculator.calculateTotal(grade.getMidtermAttendancePercentage(), grade.getMidtermQuizPercentage(), 
                grade.getMidtermAssignmentPercentage(), grade.getMidtermExamPercentage()));
        
        this.grade.setMidtermEquivalent(calculator.calculateGradeEquivalent(grade.getMidtermTotal()));
        
        totalTextField.setText(df.format(grade.getMidtermTotal()));
        equivalentTextField.setText(df.format(grade.getMidtermEquivalent()));   
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
