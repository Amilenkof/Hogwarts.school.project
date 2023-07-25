package ru.hogwarts.school.repository;

import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {

    Collection<Student> findByAgeBetween(int min, int max);

    List<Student> findByFaculty_Id(Long id);

//    Student findStudentById(Long id);

}
