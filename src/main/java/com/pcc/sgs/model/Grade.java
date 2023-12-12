
package com.pcc.sgs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author James Jasper D. Villamor
 * @email jamesjasper.villamor@gmail.com
 */
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Grade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //Prelim
    private Double prelimAttendanceScore = 0d;
    private Double prelimAttendanceTotal = 0d;
    private Double prelimAttendancePercentage = 0d;
    private Double prelimQuizScore = 0d;
    private Double prelimQuizTotal = 0d;
    private Double prelimQuizPercentage = 0d;
    private Double prelimAssignmentScore = 0d;
    private Double prelimAssignmentTotal = 0d;
    private Double prelimAssignmentPercentage = 0d;
    private Double prelimExamScore = 0d;
    private Double prelimExamTotal = 0d;
    private Double prelimExamPercentage = 0d;
    private Double prelimTotal = 0d;
    private Double prelimEquivalent = 0d;
    //Midterm
    private Double midtermAttendanceScore = 0d;
    private Double midtermAttendanceTotal = 0d;
    private Double midtermAttendancePercentage = 0d;
    private Double midtermQuizScore = 0d;
    private Double midtermQuizTotal = 0d;
    private Double midtermQuizPercentage = 0d;
    private Double midtermAssignmentScore = 0d;
    private Double midtermAssignmentTotal = 0d;
    private Double midtermAssignmentPercentage = 0d;
    private Double midtermExamScore = 0d;
    private Double midtermExamTotal = 0d;
    private Double midtermExamPercentage = 0d;
    private Double midtermTotal = 0d;
    private Double midtermEquivalent = 0d;
    
    //SemiFinal
    private Double semifinalAttendanceScore = 0d;
    private Double semifinalAttendanceTotal = 0d;
    private Double semifinalAttendancePercentage = 0d;
    private Double semifinalQuizScore = 0d;
    private Double semifinalQuizTotal = 0d;
    private Double semifinalQuizPercentage = 0d;
    private Double semifinalAssignmentScore = 0d;
    private Double semifinalAssignmentTotal = 0d;
    private Double semifinalAssignmentPercentage = 0d;
    private Double semifinalExamScore = 0d;
    private Double semifinalExamTotal = 0d;
    private Double semifinalExamPercentage = 0d;
    private Double semifinalTotal = 0d;
    private Double semifinalEquivalent = 0d;
    
    //Final
    private Double finalAttendanceScore = 0d;
    private Double finalAttendanceTotal = 0d;
    private Double finalAttendancePercentage = 0d;
    private Double finalQuizScore = 0d;
    private Double finalQuizTotal = 0d;
    private Double finalQuizPercentage = 0d;
    private Double finalAssignmentScore = 0d;
    private Double finalAssignmentTotal = 0d;
    private Double finalAssignmentPercentage = 0d;
    private Double finalExamScore = 0d;
    private Double finalExamTotal = 0d;
    private Double finalExamPercentage = 0d;
    private Double finalTotal = 0d;
    private Double finalEquivalent = 0d;
    
    private Double finalGradeGWA = 0d;
    private Double finalGradeEquivalent = 0d;
    
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = true, updatable = true)
    private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private MyClass myClass;
}
