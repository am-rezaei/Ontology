package com.amin.ontology.Service;

import com.amin.ontology.Const;
import com.amin.ontology.Model.*;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UniversityOntologyService {

    private static Logger logger = LoggerFactory.getLogger(UniversityOntologyService.class);
    static OntModel model;

    @PostConstruct
    public void loadOntology() {
        model = ModelFactory.createOntologyModel();
        model.read(Const.ONTOLOGY_FILE_PATH);
    }

    public String getModel() {
        return model.getRawModel().toString();
    }

    public boolean addTeacher(TeacherDTO teacherDTO) {
        Individual newTeacher = model.createIndividual(Const.NS + teacherDTO.getName(), model.getOntResource(Const.UNIVERSITY_TEACHER));
        logger.info("teacher " + teacherDTO.getName() + " added to the model");
        return false;
    }

    public boolean addStudent(StudentDTO studentDTO) {
        Individual newStudent = model.createIndividual(Const.NS + studentDTO.getName(), model.getOntResource(Const.UNIVERSITY_STUDENT));
        logger.info("student " + studentDTO.getName() + " added to the model");
        return false;
    }

    public boolean addBachelorCourse(CourseDTO courseDTO) {
        Individual newCourse = model.createIndividual(Const.NS + courseDTO.getName(), model.getOntResource(Const.BACHELOR_COURSE));
        logger.info("course " + courseDTO.getName() + " added to the model");
        return false;
    }

    public boolean addGraduateCourse(CourseDTO courseDTO) {
        Individual newCourse = model.createIndividual(Const.NS + courseDTO.getName(), model.getOntResource(Const.GRADUATE_COURSE));
        logger.info("course " + courseDTO.getName() + " added to the model");
        return false;
    }

    public boolean addStudy(StudyDTO studyDTO) {
        Individual student = model.getIndividual(Const.NS + studyDTO.getStudent());
        Individual course = model.getIndividual(Const.NS + studyDTO.getCourse());
        model.add(student, model.getProperty(Const.STUDY), course);
        logger.info("object property study (student: " + studyDTO.getStudent() + ", course: " + studyDTO.getCourse() + ")" + " added to the model");
        return false;
    }

    public boolean addEncourage(EncourageDTO encourageDTO) {
        Individual student = model.getIndividual(Const.NS + encourageDTO.getStudent());
        Individual teacher = model.getIndividual(Const.NS + encourageDTO.getTeacher());
        model.add(student, model.getProperty(Const.ENCOURAGE_BY), teacher);
        logger.info("object property encouraged_by (student: " + encourageDTO.getStudent() + ", teacher: " + encourageDTO.getTeacher() + ")" + " added to the model");
        return false;
    }


    public String getOntology() {
        return model.toString();
    }


    public String createAllDifference() {
        return model.createAllDifferent().getModel().toString();
    }

    public String queryModel(QueryDTO queryDTO) {
        String res = "";
        model = (model.createAllDifferent().getOntModel());
        Model inf = ModelFactory.createOntologyModel(
                OntModelSpec.OWL_DL_MEM_RULE_INF, model);
        Query query = QueryFactory.create(queryDTO.getText());
        QueryExecution qexec = QueryExecutionFactory.create(query, inf);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                 if (Arrays.asList(queryDTO.getFilter().split(",")).stream().anyMatch(p -> soln.toString().toLowerCase().contains(p.toLowerCase())))
                res += "\n" + soln.toString();
            }
        } catch (Exception x) {
            System.out.println(x.getMessage());
        } finally {
            qexec.close();
        }
        return res;
    }

    public String getInferred() {
        String res = "";
        model = (model.createAllDifferent().getOntModel());
        Model inf = ModelFactory.createOntologyModel(
                OntModelSpec.OWL_DL_MEM_RULE_INF, model);

        ExtendedIterator<Statement> stmts = inf.listStatements().filterDrop(new Filter<Statement>() {
            @Override
            public boolean accept(Statement o) {
                return model.contains(o);
            }
        });
        List<Statement> lst = stmts.toList();
        for (Statement s:lst) {
            res+=s+"\n";
        }
        return res;
    }

    public List<ResultDTO> getResult() {
        List<ResultDTO> result = new ArrayList<>();
        model.add(model.createAllDifferent().getModel());

        Model inf = ModelFactory.createOntologyModel(
                OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

        // try to get a list of excellent students to send a cart postal

        String queryString =
                "PREFIX NS: <" + Const.NS + "> " +
                        " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "SELECT  * WHERE { " +
                        "    ?A rdf:type NS:ExcellentStudent ." +
                        "}";

//        queryString =  "SELECT  * WHERE { ?A ?B ?C }";
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, inf);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                //System.out.println(soln);
                result.add(new ResultDTO(soln.toString(), "SEND_CARD_POSTAL"));
            }
        } catch (Exception x) {
            System.out.println(x.getMessage());
        } finally {
            qexec.close();
        }


        return result;
    }

}
