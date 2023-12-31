
package com.pcc.sgs.controller;

import com.pcc.sgs.helper.ScenePath;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 *
 * @author James Jasper D. Villamor
 */
public class SceneController {
    
    private static double x;
    private static double y;

    private static Parent main;

    public static void getInitialScene(Stage stage) throws IOException {
        main = FXMLLoader.load((SceneController.class.getResource(ScenePath.LOGIN.getPath())));
        Scene scene = new Scene(main);
        controlDrag(stage);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Class Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void getMainScene(ActionEvent event) throws IOException {
        changeScreen(event, ScenePath.HOME.getPath());
    }

    public static void getMyClassesScene(ActionEvent event) throws IOException {
        changeScreen(event, ScenePath.MY_CLASSES.getPath());
    }

    public static void getMyStudentsScene(ActionEvent event) throws IOException {
        changeScreen(event, ScenePath.MY_STUDENTS.getPath());
    }
    
    public static void getGradesScene(ActionEvent event) throws IOException {
        changeScreen(event, ScenePath.GRADES.getPath());
    }

    private static void changeScreen(ActionEvent event, String path) throws IOException {
        main = FXMLLoader.load(SceneController.class.getResource(path));
        Scene visitScene = new Scene(main);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controlDrag(window);
        window.setScene(visitScene);
        window.show();
    }

    public static void controlDrag(Stage stage) {
        main.setOnMousePressed((MouseEvent event) -> {
            x = stage.getX() - event.getScreenX();
            y = stage.getY() - event.getScreenY();
        });
        main.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() + x);
            stage.setY(event.getScreenY() + y);
        });
    }

    public static void close(ActionEvent actionEvent) {
        Node  source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
}
