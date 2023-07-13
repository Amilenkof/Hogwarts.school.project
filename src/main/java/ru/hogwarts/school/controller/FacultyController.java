package ru.hogwarts.school.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @PostMapping
    public ResponseEntity<Faculty> create(@RequestBody Faculty faculty) {
        var result = facultyService.create(faculty);
        return result != null ?
                ResponseEntity.ok(faculty) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> read(@PathVariable Long id) {
        Faculty faculty = facultyService.read(id);
        if (faculty != null) {
            return ResponseEntity.ok(faculty);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping
    public ResponseEntity<Faculty> update(@RequestBody Faculty faculty) {
        Faculty result = facultyService.update(faculty);
        if (result != null) {
            return ResponseEntity.ok(faculty);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> delete(@PathVariable Long id) {
        Faculty faculty = facultyService.delete(id);
        return faculty != null ?
                ResponseEntity.ok(faculty) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/getall")
    public Map<Long, Faculty> getAll() {
        return facultyService.getAll();
    }

    @GetMapping("/color")
    public Collection<Faculty> findForAge(@RequestParam("color") String color) {
        return facultyService.findForColor(color);
    }
}
