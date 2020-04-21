package com.amin.ontology.Model;

public class CourseDTO {
    String name;

    public CourseDTO()
    {

    }
    public CourseDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
