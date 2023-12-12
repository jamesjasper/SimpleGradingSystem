package com.pcc.sgs.dao;

import com.pcc.sgs.model.Grade;
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
public class GradeDao {
    
    public Boolean createGrade(Grade grade) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(grade);
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
    
    public void updateGrade(Grade grade) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(grade);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    
    public void deleteGrade(Grade grade) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(grade);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public Grade getGradeById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Grade.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Grade getStudentGradeInClass(Student student, MyClass myClass) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Grade g WHERE g.student= :student AND g.myClass = :myClass", Grade.class);
            query.setParameter("student", student);
            query.setParameter("myClass", myClass);
            List<Grade> gradeList = (List<Grade>) query.getResultList();
            if (gradeList.isEmpty())
                    return null;
            else
                return gradeList.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Grade> getGradesByStudent(Student student) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Grade g WHERE g.student= :student", Grade.class);
            query.setParameter("student", student);
            List<Grade> gradeList = (List) query.getResultList();
            if (gradeList.isEmpty())
                return new ArrayList<>();
            else
                return gradeList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }
}
