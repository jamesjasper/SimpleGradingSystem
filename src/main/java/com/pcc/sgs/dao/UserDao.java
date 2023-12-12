package com.pcc.sgs.dao;

import com.pcc.sgs.helper.PasswordManager;
import com.pcc.sgs.model.User;
import com.pcc.sgs.util.HibernateUtil;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author James Jasper D. Villamor
 */
public class UserDao {
    
    public void createUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null)
                transaction.rollback();
        }
    }
    
    public User getConnectedUser(String username, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            PasswordManager pm = new PasswordManager();
            TypedQuery<User> query = session.createQuery("FROM User u WHERE username = :username", User.class);
            query.setParameter("username", username);
            User user = query.getSingleResult();
            boolean isPasswordMatch = pm.checkPassword(password, user.getPassword());
            if (user != null && isPasswordMatch)
                return user;
            else
                return null;
        } catch (NoResultException ex) {
            System.err.println("User not found");
            return null;
        }
    }

    public User getUser(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public User getUser(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM User u WHERE u.username = :username", User.class); 
            query.setParameter("username", username);
            return (User) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}