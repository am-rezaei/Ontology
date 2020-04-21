package com.amin.ontology.Model;

public class ResultDTO {
    String studentName;
    String action;
    String studentType;
    public ResultDTO(String studentName,String studentType, String action) {
        this.studentName = studentName;
        this.studentType=studentType;
        this.action = action;
    }

    public String getStudentType() {
        return studentType;
    }

    public void setStudentType(String studentType) {
        this.studentType = studentType;
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
