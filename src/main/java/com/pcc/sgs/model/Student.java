package com.pcc.sgs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author James Jasper D. Villamor
 */

@Entity
@Getter @Setter @NoArgsConstructor
public class Student {
    
    @Id
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "studentId", unique = true)
    private String studentId;
    
    @Column(name = "firstName")
    private String firstName;
    
    @Column(name = "middleName")
    private String middleName;
    
    @Column(name = "lastName")
    private String lastName;
    
    @Column(name = "course")
    private String course;
    
    @Column(name = "yearLevel")
    private String yearLevel;
    
    @Column(name = "phoneNumber")
    private String phoneNumber;
    
    @Column(name = "address")
    private String address;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_class",
            joinColumns = { @JoinColumn(name = "student_id") },
            inverseJoinColumns = { @JoinColumn(name = "class_id"),
            }
    )
    private Set<MyClass> classes = new HashSet<>();
    
    public Student(String studentId, String firstName, String middleName, 
            String lastName, String course, String yearLevel, String phoneNumber, 
            String address) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.course = course;
        this.yearLevel = yearLevel;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    
    public void removeClass(MyClass myClass) {
        this.classes.remove(myClass);
        myClass.getStudents().remove(this);
    }
}
