package ru.hogwarts.school.service;

import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

@Service
@Scope("singleton")
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty create(Faculty faculty) {
      return  facultyRepository.save(faculty);

    }

    public Optional<Faculty> read(Long id) {
        return facultyRepository.findById(id);
    }

    public Faculty update(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void delete(Long id) {
         facultyRepository.deleteById(id);
    }

    public List<Faculty> getAll(){
        return facultyRepository.findAll();
    }
    public Collection<Faculty> findForColor(String color){

       return facultyRepository.findAll(Sort.by(color));
    }
}