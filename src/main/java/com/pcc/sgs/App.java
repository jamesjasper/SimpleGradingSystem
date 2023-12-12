package com.pcc.sgs;

import com.pcc.sgs.controller.SceneController;
import com.pcc.sgs.util.HibernateUtil;
import javafx.application.Application;
import javafx.stage.Stage;

import org.hibernate.Session;

/**
 * JavaFX App
 */
public class App extends Application {


    public static void main(String[] args) {
        launch();
    }
    
    @Override
    public void init() throws Exception {
        Session session =  HibernateUtil.getSessionFactory().openSession();
        session.close();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        SceneController.getInitialScene(stage);
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void stop() throws Exception {
        super.stop();;
        HibernateUtil.shutdown();
    }
    
    
}