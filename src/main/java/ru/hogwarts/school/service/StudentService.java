package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Scope("singleton")
public class StudentService {
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student create(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Optional<Student> read(Long id) {
        logger.info("Was invoked method for read student");
        return studentRepository.findById(id);
    }

    public Student update(Student student) {
        logger.info("Was invoked method for update student");
        return studentRepository.save(student);
    }

    public void delete(Long id) {
        logger.info("Was invoked method for delete student");
        studentRepository.deleteById(id);
    }

    public List<Student> getAll() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }


    public Collection<Student> findForAge(int age) {
        logger.info("Was invoked method for find student with age={}",age);
        return getAll().stream()
                .filter(student -> student.getAge() == age)
                .toList();
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method for find all students with age between{} and {}",min,max);
        logger.warn("This method can return empty result");
        return studentRepository.findByAgeBetween(min, max);
    }

    public List<Student> findAllStudensByFaculty(Long id) {
        logger.info("Was invoked method for find all students on faculty with id={}",id );
        return studentRepository.findByFaculty_Id(id);
    }

    public int countStudents() {
        logger.info("Was invoked method for counting all students" );
        return studentRepository.getCountStudents();
    }
    public double getAgeAverage (){
        logger.info("Was invoked method for calculating average age about all students" );

        return studentRepository.getAgeAverage();
    }
    public List<Student> getFiveLastStudents(){
        logger.info("Was invoked method for find 5 last students" );

        return studentRepository.getFiveLastStudents();
    }

}


