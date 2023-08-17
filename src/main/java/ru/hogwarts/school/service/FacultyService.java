package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Scope("singleton")
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty create(Faculty faculty) {
        return facultyRepository.save(faculty);

    }

    public Optional<Faculty> read(Long id) {
        logger.info("Was invoked method for read faculty with id={}",id );
        return facultyRepository.findById(id);
    }

    public Faculty update(Faculty faculty) {
        logger.info("Was invoked method for update faculty {}",faculty );

        return facultyRepository.save(faculty);
    }

    public void delete(Long id) {
        logger.info("Was invoked method for delete faculty with id= {}",id );

        facultyRepository.deleteById(id);
    }

    public List<Faculty> getAll() {
        logger.info("Was invoked method  for find all faculty");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findForColor(String color) {
        logger.info("Was invoked method  for find all faculty with color={}",color);
        logger.warn("This method can return empty result");

        return getAll().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .toList();


    }

    public Faculty findByColorIgnoreCase(String color) {
        logger.info("Was invoked method  for find any faculty with color={}",color);


        return facultyRepository.findByColorIgnoreCase(color);
    }

    public Faculty findByNameIgnoreCase(String name) {
        logger.info("Was invoked method  for find any faculty with name={}",name);

        return facultyRepository.findByNameIgnoreCase(name);
    }

    public Faculty findByStudent(Long id) {
        logger.info("Was invoked method  for find  faculty for student with id ={}",id);

        return facultyRepository.findByStudents_id(id);
    }

}