package com.pcc.sgs.dao;

import com.pcc.sgs.model.MyClass;
import com.pcc.sgs.model.Student;
import com.pcc.sgs.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;


/**
 *
 * @author James Jasper D. Villamor
 */


public class StudentDao {
    
    public Boolean createStudent(Student student) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(student);
            transaction.commit();
            return transaction.getStatus() == TransactionStatus.COMMITTED;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return false;
    }
    
    public void updateStudent(Student student) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(student);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    
    public void deleteStudent(Student student) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            //get student from database *important that it is queried from database and not just a passed object
            Student aStudent = session.find(Student.class, student.getId());
            //remove each association to all associated Classes
            for(MyClass myClass : student.getClasses()) {
                student.removeClass(myClass);
            }
            //delete associated Grades
            Query q = session.createQuery("DELETE Grade g WHERE g.student = :student");
            q.setParameter("student", aStudent);
            q.executeUpdate();
            //finally delete class after all associations are deleted or removed
            session.remove(aStudent);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public Student getStudent(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Student.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Student> getStudents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Student", Student.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Long getStudentNumber() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<?> query = session.createQuery("SELECT count(v) FROM Student v");
            return (Long) query.uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }
    
}
