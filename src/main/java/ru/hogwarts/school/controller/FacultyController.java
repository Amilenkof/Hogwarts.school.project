package ru.hogwarts.school.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @PostMapping
    public ResponseEntity<Faculty> create(@RequestBody Faculty faculty) {
        if (faculty != null) {
            facultyService.create(faculty);
            return ResponseEntity.ok(faculty);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Faculty>> read(@PathVariable Long id) {
        Optional<Faculty> faculty = facultyService.read(id);
        if (faculty.isPresent()) {
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
        Faculty faculty = facultyService.read(id).get();
        try {
            facultyService.delete(id);
        } catch (IllegalArgumentException e) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(faculty);
    }
    @GetMapping("/getall")
    public List<Faculty> getAll (){
        return facultyService.getAll();
    }
    @GetMapping("/color")
    public Collection<Faculty> findForColor (@RequestParam ("color") String color ){
        return facultyService.findForColor(color);
    }
    @GetMapping("/findByColorIgnoreCase")
    public ResponseEntity<Faculty> findByColorIgnoreCase(@RequestParam String color){
        Faculty result = facultyService.findByColorIgnoreCase(color);
        if (result==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok( facultyService.findByColorIgnoreCase(color));
    }
    @GetMapping("/findByNameIgnoreCase")
    public ResponseEntity<Faculty> findByNameIgnoreCase(@RequestParam String name){
        Faculty result = facultyService.findByNameIgnoreCase(name);
        if (result==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok( facultyService.findByNameIgnoreCase(name));
    }

    @GetMapping("/findByStudentId")
    public ResponseEntity<Faculty> findByStudent (@RequestParam Long id) {
        Faculty result = facultyService.findByStudent(id);
        return result == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(result);
    }

}
