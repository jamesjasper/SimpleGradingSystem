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
 * @author USER
 */
public class FinalCalculatorController {

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
            attendanceScoreTextField.setText(df.format(grade.getFinalAttendanceScore()));
            attendanceTotalTextField.setText(df.format(grade.getFinalAttendanceTotal()));
            assignmentScoreTextField.setText(df.format(grade.getFinalAssignmentScore()));
            assignmentTotalTextField.setText(df.format(grade.getFinalAssignmentTotal()));
            quizScoreTextField.setText(df.format(grade.getFinalQuizScore()));
            quizTotalTextField.setText(df.format(grade.getFinalQuizTotal()));
            examScoreTextField.setText(df.format(grade.getFinalExamScore()));
            examTotalTextField.setText(df.format(grade.getFinalExamTotal()));
            totalTextField.setText(df.format(grade.getFinalTotal()));
            equivalentTextField.setText(df.format(grade.getFinalEquivalent()));
        } else {
            this.gradeIndatabase = false;
            attendanceScoreTextField.setText(df.format(grade.getFinalAttendanceScore()));
            attendanceTotalTextField.setText(df.format(grade.getFinalAttendanceTotal()));
            assignmentScoreTextField.setText(df.format(grade.getFinalAssignmentScore()));
            assignmentTotalTextField.setText(df.format(grade.getFinalAssignmentTotal()));
            quizScoreTextField.setText(df.format(grade.getFinalQuizScore()));
            quizTotalTextField.setText(df.format(grade.getFinalQuizTotal()));
            examScoreTextField.setText(df.format(grade.getFinalExamScore()));
            examTotalTextField.setText(df.format(grade.getFinalExamTotal()));
            totalTextField.setText(df.format((grade.getFinalTotal())));
            equivalentTextField.setText(df.format(grade.getFinalEquivalent()));
        }
    }
    
    @FXML
    void calculate() {
        GradeCalculator calculator = new GradeCalculator();
        this.grade.setFinalAttendanceScore(Double.valueOf(attendanceScoreTextField.getText()));
        this.grade.setFinalAttendanceTotal(Double.valueOf(attendanceTotalTextField.getText()));
        this.grade.setFinalAttendancePercentage(calculator.calculatePercentage(grade.getFinalAttendanceScore(), grade.getFinalAttendanceTotal(), 10));
        
        this.grade.setFinalQuizScore(Double.valueOf(quizScoreTextField.getText()));
        this.grade.setFinalQuizTotal(Double.valueOf(quizTotalTextField.getText()));
        this.grade.setFinalQuizPercentage(calculator.calculatePercentage(grade.getFinalQuizScore(), grade.getFinalQuizTotal(), 30));
        
        this.grade.setFinalAssignmentScore(Double.valueOf(assignmentScoreTextField.getText()));
        this.grade.setFinalAssignmentTotal(Double.valueOf(assignmentTotalTextField.getText()));
        this.grade.setFinalAssignmentPercentage(calculator.calculatePercentage(grade.getFinalAssignmentScore(), grade.getFinalAssignmentTotal(), 20));
        
        this.grade.setFinalExamScore(Double.valueOf(examScoreTextField.getText()));
        this.grade.setFinalExamTotal(Double.valueOf(examTotalTextField.getText()));
        this.grade.setFinalExamPercentage(calculator.calculatePercentage( grade.getFinalExamScore(), grade.getFinalExamTotal(), 40));
        
        this.grade.setFinalTotal(calculator.calculateTotal(grade.getFinalAttendancePercentage(), grade.getFinalQuizPercentage(), 
                grade.getFinalAssignmentPercentage(), grade.getFinalExamPercentage()));
        
        this.grade.setFinalEquivalent(calculator.calculateGradeEquivalent(grade.getFinalTotal()));
        
        totalTextField.setText(df.format(grade.getFinalTotal()));
        equivalentTextField.setText(df.format(grade.getFinalEquivalent()));   
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
