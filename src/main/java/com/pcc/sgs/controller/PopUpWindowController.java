package com.pcc.sgs.controller;

import com.pcc.sgs.helper.ScenePath;
import com.pcc.sgs.model.Grade;
import com.pcc.sgs.model.MyClass;
import com.pcc.sgs.model.Student;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author James Jasper D. Villamor
 */
public class PopUpWindowController {
    
    static double x;
    static double y;

    public static void getCreateClassWindow() throws IOException {
        getPopUpWindow(ScenePath.ADD_CLASS.getPath());
    }

    public static void getCreateStudentWindow() throws IOException {
        getPopUpWindow(ScenePath.ADD_STUDENT.getPath());    }

    public static void getEditClassWindow(MyClass myClass) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(PopUpWindowController.class.getResource(ScenePath.EDIT_CLASS.getPath()));
            Pane main = loader.load();
            EditClassController controller = loader.getController();
            controller.init(myClass);
            setStage(main);
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    public static void getEditStudentWindow(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(PopUpWindowController.class.getResource(ScenePath.EDIT_STUDENT.getPath()));
            Pane main = loader.load();
            EditStudentController controller = loader.getController();
            controller.init(student);
            setStage(main);
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    public static void getGradeCalculatorWindow(MyClass aClass, Student student) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(PopUpWindowController.class.getResource(ScenePath.SET_GRADE.getPath()));
            Pane main = loader.load();
            SetGradeController controller = loader.getController();
            controller.init(aClass, student);
            Stage stage = new Stage();
            controlDrag((Pane) main, stage);
            setStage(main);
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    public static void getPrelimGradeCalculatorWindow(Grade grade, Student student, MyClass myClass) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(PopUpWindowController.class.getResource(ScenePath.PRELIM_PATH.getPath()));
            Pane main = loader.load();
            PrelimCalculatorController controller = loader.getController();
            controller.init(grade, student, myClass);
            setStage(main);
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    public static void getMidtermGradeCalculatorWindow(Grade grade, Student student, MyClass myClass) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(PopUpWindowController.class.getResource(ScenePath.MIDTERM_PATH.getPath()));
            Pane main = loader.load();
            MidtermCalculatorController controller = loader.getController();
            controller.init(grade, student, myClass);
            setStage(main);
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    public static void getSemiFinalGradeCalculatorWindow(Grade grade, Student student, MyClass myClass) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(PopUpWindowController.class.getResource(ScenePath.SEMIFINAL_PATH.getPath()));
            Pane main = loader.load();
            SemiFinalCalculatorController controller = loader.getController();
            controller.init(grade, student, myClass);
            setStage(main);
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    public static void getFinalGradeCalculatorWindow(Grade grade, Student student, MyClass myClass) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(PopUpWindowController.class.getResource(ScenePath.FINAL_PATH.getPath()));
            Pane main = loader.load();
            FinalCalculatorController controller = loader.getController();
            controller.init(grade, student, myClass);
            setStage(main);
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    public static void getAssignStudentWindow(Student student) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(PopUpWindowController.class.getResource(ScenePath.ASSIGN_STUDENT.getPath()));
            Pane main = loader.load();
            AssignStudentController ctrl = loader.getController();
            ctrl.init(student);
            setStage(main);
            } catch (IOException e) {
                e.printStackTrace();
        }
    }

    public static void getPopUpWindow(String path) throws IOException {
        Stage stage = new Stage();
        Pane main = FXMLLoader.load(PopUpWindowController.class.getResource(path));
        controlDrag(main, stage);
        setStage(main);
    }
    
    
    
    private static void setStage(Pane main) {
        Stage stage = new Stage();
        controlDrag(main, stage);
        stage.setScene(new Scene(main));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Class Management and Grading System");
        stage.getScene();
        stage.showAndWait();
    }

    public static void controlDrag(Pane main, Stage stage) {
        main.setOnMousePressed((MouseEvent event) -> {
            x = stage.getX() - event.getScreenX();
            y = stage.getY() - event.getScreenY();
        });
        main.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() + x);
            stage.setY(event.getScreenY() + y);
        });
    }

}
