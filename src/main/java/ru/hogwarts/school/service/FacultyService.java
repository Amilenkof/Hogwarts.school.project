package ru.hogwarts.school.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@Scope("singleton")
public class FacultyService {
    private final Map<Long, Faculty> repository;

    private static Long counter = 0L;


    public FacultyService() {
        this.repository = new HashMap<>();
    }

    public Faculty create(Faculty faculty) {
        if (faculty != null) {
            counter++;
            faculty.setId(counter);
            repository.put(counter, faculty);
            return faculty;
        }
        return null;
    }

    public Faculty read(Long id) {
        return repository.get(id);
    }

    public Faculty update(Faculty faculty) {
        return repository.put(faculty.getId(), faculty);
    }

    public Faculty delete(Long id) {
        return repository.remove(id);
    }

    public Map<Long, Faculty> getAll() {
        return repository;
    }

    public Collection<Faculty> findForColor(String color) {
        return repository.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .toList();
    }
}