package com.amin.ontology.Model;

public class EncourageDTO {
    String student;
    String teacher;

    public EncourageDTO() {
    }
    public EncourageDTO(String student, String teacher) {
        this.student = student;
        this.teacher = teacher;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
