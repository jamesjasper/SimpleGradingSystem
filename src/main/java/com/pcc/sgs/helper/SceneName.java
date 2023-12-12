package com.pcc.sgs.helper;

/**
 * Code created by Andrius on 2020-09-28
 */
public enum SceneName {
    DASHBOARD("DASHBOARD"),
    CLASSES("MY CLASSES"),
    STUDENTS("MY STUDENTS"),
    GRADES("GRADES"),
    SEARCH ("SEARCH RESULTS")
    ;

    private final String name;

    private SceneName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
