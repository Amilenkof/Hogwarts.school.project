package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Optional<Student>> read(@PathVariable Long id) {
        Optional<Student> result = studentService.read(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result);
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
//        @DeleteMapping("{id}")
//    public ResponseEntity<Faculty> delete(@PathVariable Long id) {
//        try {
//            facultyService.delete(id);
//        } catch (IllegalArgumentException e) {
//         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        return ResponseEntity.ok().build();
//    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> delete(@PathVariable Long id) {
        Student student = studentService.read(id).get();
        try {
            studentService.delete(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/getall")
    public List<Student> getAll() {
        return studentService.getAll();
    }

    @GetMapping("/age")
    public Collection<Student> findForAge(@RequestParam("age") int age) {
        return studentService.findForAge(age);
    }
    @GetMapping("/findByAgeBetween")
    public Collection<Student> findByAgeBetween(@RequestParam int min,
                                                @RequestParam int max){
        return studentService.findByAgeBetween(min, max);
    }
    @GetMapping("/findAllStudensByFaculty")
    public List <Student>  findAllStudensByFaculty (@RequestParam Long id) {
        return studentService.findAllStudensByFaculty(id);
    }

}
