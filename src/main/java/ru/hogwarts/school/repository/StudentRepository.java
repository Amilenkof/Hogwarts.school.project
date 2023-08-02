package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {

    Collection<Student> findByAgeBetween(int min, int max);

    List<Student> findByFaculty_Id(Long id);

    //    Student findStudentById(Long id);
    @Query(value = "select id from student order by id desc limit 1;",nativeQuery = true)
    Long findLastID();

    Student findStudentById(Long id);
}
