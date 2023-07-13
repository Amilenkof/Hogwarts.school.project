package ru.hogwarts.school.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@Scope("singleton")
public class StudentService {
    private final Map<Long, Student> repository;

    private static Long counter = 0L;


    public StudentService() {
        this.repository = new HashMap<>();
    }

    public Student create(Student student) {
        if (student != null) {
            counter++;
            student.setId(counter);
            repository.put(counter, student);
            return student;
        }
        return null;

    }
//        public Faculty create(Faculty faculty) {
//        if (faculty != null) {
//            counter++;
//            faculty.setId(counter);
//            repository.put(counter, faculty);
//            return faculty;
//        }
//        return null;
//    }

    public Student read(Long id) {
        return repository.get(id);
    }

    public Student update(Student student) {
        return repository.put(student.getId(), student);
    }

    public Student delete(Long id) {
        return repository.remove(id);
    }

    public Map<Long, Student> getAll() {
        return repository;
    }

    public Collection<Student> findForAge(int age) {
        return repository.values().stream()
                .filter(student -> student.getAge() == age)
                .toList();
    }

    public static void setCounter(long l) {

        StudentService.counter = counter;
    }
}
