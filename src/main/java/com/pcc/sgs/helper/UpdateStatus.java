package com.pcc.sgs.helper;

/**
 * Code created by Andrius on 2020-10-06
 */
public final class UpdateStatus {

    private UpdateStatus() {

    }

    private static boolean isClassAdded;
    private static boolean isStudentAdded;
    private static boolean isStudentAssigned;

    public static boolean isClassAdded() {
        return isClassAdded;
    }

    public static void setIsClassAdded(boolean isClassAdded) {
        UpdateStatus.isClassAdded = isClassAdded;
    }

    public static boolean isStudentAdded() {
        return isStudentAdded;
    }

    public static void setIsStudentAdded(boolean isStudentAdded) {
        UpdateStatus.isStudentAdded = isStudentAdded;
    }
    
    public static void setIsStudentAssigned(boolean isStudentAssigned) {
        UpdateStatus.isStudentAssigned = isStudentAssigned;    
    }

    public static boolean isStudentAssigned() {
        return isStudentAssigned;
    }

}
