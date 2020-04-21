package com.amin.ontology.Controller;

import com.amin.ontology.Model.*;
import com.amin.ontology.Service.UniversityOntologyService;
import org.apache.jena.ontology.OntModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class UniversityOntologyController {

    private UniversityOntologyService ontologyService;
    private static Logger logger = LoggerFactory.getLogger(UniversityOntologyController.class);

    public UniversityOntologyController(UniversityOntologyService ontologyService) {
        this.ontologyService = ontologyService;
    }


     @PostConstruct
    void init() {
        CourseDTO courseDTO = new CourseDTO("SW_Eng");
        addGraduateCourse(courseDTO);
        StudentDTO studentDTO = new StudentDTO("Amin");
        addStudent(studentDTO);
        StudentDTO studentDTO2 = new StudentDTO("Jack");
        addStudent(studentDTO2);
        TeacherDTO teacherDTO1 = new TeacherDTO("GreatTeacher1");
        addTeacher(teacherDTO1);
        TeacherDTO teacherDTO2 = new TeacherDTO("GreatTeacher2");
        addTeacher(teacherDTO2);
        StudyDTO studyDTO = new StudyDTO("Amin", "SW_Eng");
        addStudy(studyDTO);
        EncourageDTO encourageDTO1 = new EncourageDTO("Jack", "GreatTeacher1");
        addEncouragedBy(encourageDTO1);
        EncourageDTO encourageDTO2 = new EncourageDTO("Jack", "GreatTeacher2");
        addEncouragedBy(encourageDTO2);
        logger.info(" POST CONSTRUCT MODELS FINISHED !");
    }

    @GetMapping("/resetModel")
    private void resetModel() {
        ontologyService.loadOntology();
    }

    @GetMapping("/getModel")
    private String getModel() {
        return ontologyService.getModel();
    }

    @PostMapping("addTeacher")
    private void addTeacher(@RequestBody TeacherDTO teacherDTO) {
        ontologyService.addTeacher(teacherDTO);
    }

    @PostMapping("query")
    private String queryModel(@RequestBody QueryDTO queryDTO) {
        queryDTO.setText(queryDTO.getText().replace("@@","\n"));
        return ontologyService.queryModel(queryDTO);
    }

    @PostMapping("addStudent")
    private void addStudent(@RequestBody StudentDTO studentDTO) {
        ontologyService.addStudent(studentDTO);
    }

    @PostMapping("addBachelorCourse")
    private void addBachelorCourse(@RequestBody CourseDTO courseDTO) {
        ontologyService.addBachelorCourse(courseDTO);
    }

    @PostMapping("addGraduateCourse")
    private void addGraduateCourse(@RequestBody CourseDTO courseDTO) {
        ontologyService.addGraduateCourse(courseDTO);
    }

    @PostMapping("addStudy")
    private void addStudy(@RequestBody StudyDTO studyDTO) {
        ontologyService.addStudy(studyDTO);
    }

    @PostMapping("addEncouragedBy")
    private void addEncouragedBy(@RequestBody EncourageDTO encourageDTO) {
        ontologyService.addEncourage(encourageDTO);
    }

    @GetMapping("/getResult")
    private List<ResultDTO> getResult() {
        return ontologyService.getResult();
    }


}
