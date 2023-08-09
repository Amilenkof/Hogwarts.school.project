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
    @Query(value = "SELECT count(student.id) as student_count FROM student", nativeQuery = true)
    int getCountStudents();

    @Query(value = "SELECT avg(age)as student_age_average FROM student",nativeQuery = true)
    double getAgeAverage();

    @Query(value = "SELECT id, name, age, faculty_id FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getFiveLastStudents();



}
