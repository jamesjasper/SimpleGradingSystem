package com.pcc.sgs.helper;

public enum ScenePath {

    HOME("/view/dashboard.fxml"),
    REGISTER("/view/register.fxml"),
    SET_GRADE("/view/setGrade.fxml"),
    MY_CLASSES("/view/myclassesDash.fxml"),
    EDIT_CLASS("/view/editClass.fxml"),
    MY_STUDENTS("/view/studentDash.fxml"),
    EDIT_STUDENT("/view/editStudent.fxml"),
    GRADES("/view/gradesDash.fxml"),
    SEARCH("/view/search.fxml"),
    ADD_STUDENT("/view/addStudent.fxml"),
    ADD_CLASS("/view/addClass.fxml"),
    ASSIGN_STUDENT("/view/assignStudent.fxml"),
    LOGIN("/view/login.fxml"), 
    PRELIM_PATH("/view/prelimGradeDialog.fxml"),
    MIDTERM_PATH("/view/midtermGradeDialog.fxml"),
    SEMIFINAL_PATH("/view/semifinalGradeDialog.fxml"),
    FINAL_PATH("/view/finalGradeDialog.fxml");

    private final String path;

    private ScenePath(String path) {
        this.path = path;
    }

    public String getPath() {
        System.out.println("Path to Resource:" +path );
        return path;
    }

    public static class GRADE_CALC {

        public GRADE_CALC() {
        }
    }
}
