package com.pcc.sgs.dao;

import com.pcc.sgs.model.MyClass;
import com.pcc.sgs.model.Student;
import com.pcc.sgs.model.User;
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
public class MyClassDao {
    
    public Boolean createMyClass(MyClass myClass) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(myClass);
            transaction.commit();
            return transaction.getStatus() == TransactionStatus.COMMITTED;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return false;
    }
    
    public void updateMyClass(MyClass myClass) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(myClass);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    
    public void deleteMyClass(MyClass myClass) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            //get MyClass from database *important that it is queried from database and not just a passed object
            MyClass aClass = session.find(MyClass.class, myClass.getId());
            //remove each association to all associated Students
            for(Student student : aClass.getStudents()) {
                aClass.removeStudent(student);
            }
            //delete associated Grades
            Query q = session.createQuery("DELETE Grade g WHERE g.myClass = :myClass");
            q.setParameter("myClass", aClass);
            q.executeUpdate();
            //finally delete class after all associations are deleted or removed
            session.remove(aClass);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    
    public void deleteStudentFromClass(MyClass myClass, Student student) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            //get MyClass from database *important that it is queried from database and not just a passed object
            MyClass aClass = session.find(MyClass.class, myClass.getId());
            //remove each association to all associated Students
            Student aStudent = session.find(Student.class, student.getId());
            aClass.removeStudent(aStudent);
            //delete associated Grade for stundent in class
            Query q = session.createQuery("DELETE Grade g WHERE g.myClass = :myClass AND g.student = :student");
            q.setParameter("myClass", aClass);
            q.setParameter("student", aStudent);
            q.executeUpdate();
            //finally delete class after all associations are deleted or removed
//            session.remove(aClass);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public MyClass getMyClass(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(MyClass.class, id);
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MyClass> getMyClasses(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM MyClass u WHERE u.user = :user", MyClass.class).setParameter("user", user).list();
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public Long getMyClassNumber(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<?> query = session.createQuery("SELECT count(v) FROM MyClass v WHERE v.user = :user", MyClass.class).setParameter("user", user);
            return (Long) query.uniqueResult();
        } catch (Exception ex) {
            return 0L;
        }
    }
    
    public List<Student> getStudentsByClassId(Long classId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hqlQuery = "SELECT c.students from MyClass c WHERE c.id = :classId";
            Query<Student> query = session.createQuery(hqlQuery, Student.class);
            query.setParameter("id", classId);
            return query.getResultList();
        }catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }
}
