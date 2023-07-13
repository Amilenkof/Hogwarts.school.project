package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.net.http.HttpRequest;
import java.util.Collection;
import java.util.Map;

@RequestMapping("student")
@RestController

public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        if (student != null) {
            studentService.create(student);
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> read(@PathVariable Long id) {
        Student student = studentService.read(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping
    public ResponseEntity<Student> update(@RequestBody Student student) {
        Student result = studentService.update(student);
        if (result != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> delete(@PathVariable Long id) {
        Student student = studentService.delete(id);
        return student != null ? ResponseEntity.ok(student) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/getall")
    public Map<Long, Student> getAll() {
        return studentService.getAll();
    }
    @GetMapping("/age")
    public Collection<Student> findForAge( @RequestParam ("age") int age ){
       return studentService.findForAge(age);
    }
}
