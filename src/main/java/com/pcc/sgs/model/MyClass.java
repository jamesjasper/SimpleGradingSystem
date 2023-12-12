package com.pcc.sgs.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author James Jasper D. Villamor
 */

@Entity
@Getter @Setter @NoArgsConstructor
public class MyClass {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "courseCode")
    private String courseCode;
    
    @Column(name = "courseName")
    private String courseName;
    
    @Column(name = "schedule")
    private String schedule;
    
    @ManyToMany(mappedBy = "classes", fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Student> students = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany(mappedBy = "myClass", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Set<Grade> grades;
    
    public MyClass(String courseCode, String courseName, String schedule, User currentUser) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.schedule = schedule;
        this.user = currentUser;
    }
    
    public void addStudent(Student student) {
        this.students.add(student);
        student.getClasses().add(this);
    }
    
    public void removeStudent(Student student) {
        this.students.remove(student);
        student.getClasses().remove(this);
    }
 
}
