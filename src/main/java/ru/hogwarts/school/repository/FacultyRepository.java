package ru.hogwarts.school.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {


    Faculty findByColorIgnoreCase(String color);
    Faculty findByNameIgnoreCase(String name);

    Faculty findByStudents_id(Long id);




}
