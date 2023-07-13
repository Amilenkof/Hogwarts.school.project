package ru.hogwarts.school.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Scope("singleton")
public class StudentService {
    private final Map<Long, Student> repository;

    private static Long counter = 0L;


    public StudentService() {
        this.repository = new HashMap<>();
    }

    public Student create(Student student) {
        counter++;
        student.setId(counter);
        return repository.put(counter, student);

    }

    public Student read(Long id) {
        return repository.get(id);
    }

    public Student update(Student student) {
        return repository.put(student.getId(), student);
    }

    public Student delete(Long id) {
        return repository.remove(id);
    }
    public Map<Long,Student> getAll(){
        return repository;
    }

    public Collection <Student> findForAge(int age){
       return repository.values().stream()
                .filter(student -> student.getAge() == age)
                .toList();
    }
}
