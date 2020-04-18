package com.amin.ontology.Model;

public class ResultDTO {
    String studentName;
    String action;

    public ResultDTO(String studentName, String action) {
        this.studentName = studentName;
        this.action = action;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
