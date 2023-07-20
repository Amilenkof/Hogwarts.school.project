package ru.hogwarts.school.service;

import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Scope("singleton")
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> read(Long id) {
        return studentRepository.findById(id);
    }

    public Student update(Student student) {
        return studentRepository.save(student);
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }


    public Collection<Student> findForAge(int age) {
        return getAll().stream()
                .filter(student -> student.getAge() == age)
                .toList();
    }
    public Collection<Student> findByAgeBetween(int min ,int max){
        return studentRepository.findByAgeBetween(min, max);
    }
    public  List<Student> findAllStudensByFaculty (Long id) {
        return studentRepository.findByFaculty_Id(id);
    }

    }


