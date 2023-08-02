package ru.hogwarts.school;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.swagger.v3.core.util.Json;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchoolApplicationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private StudentService studentService;
    private final Long id = 2010L;
    private final Student student = new Student(id, "IVAN", 12);

    @Test
    void contextLoads() {
        Assertions.assertThat(studentController).isNotNull();
    }


    @Test
    public void createMethodTests() throws URISyntaxException {
        //test1 not null
        Assertions.assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
                .isNotNull();
        Long lastIndex = studentService.findLastID();
//удаление 1 вариант
//        testRestTemplate.delete("http://localhost:" + port + "/student/"+lastIndex);//удаляем студента, которого создали в тесте из БД\

        //удаление через exchange
        URI uri = new URI("http://localhost:" + port + "/student/" + lastIndex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Student> request = new HttpEntity<>(student, headers);
        RequestEntity<Student> requestEntity = new RequestEntity(HttpMethod.DELETE, uri);
        testRestTemplate.exchange(requestEntity, Student.class);


    }

    @Test
    public void createMethodSecondTest() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer();
        String json = ow.writeValueAsString(student);// получаем Json предствление обьекта
        String actual = testRestTemplate.postForObject("http://localhost:" + port + "/student", student, String.class);
        Assertions.assertThat(actual.equals(json)).isTrue();
        Long lastIndex = studentService.findLastID();
        testRestTemplate.delete("http://localhost:" + port + "/student/" + lastIndex);
    }

    @Test
    public void readMethodTests() {
        Long readId = studentService.findLastID();
        Student expected = studentService.findStudentByID(readId);
        //test1 not null
        Assertions.assertThat(testRestTemplate.getForObject("http://localhost:" + port + "/student/" + readId, Student.class))
                .isNotNull();
        // test 2 get student from db
        var actual = testRestTemplate.getForObject("http://localhost:" + port + "/student/" + readId, Student.class);
        Assertions.assertThat(actual.equals(expected)).isTrue();
    }

    @Test
    public void putMethodTests() {
        Long lastID = studentService.findLastID();
        Student expected = studentService.findStudentByID(lastID);
        student.setId(lastID);
        testRestTemplate.put("http://localhost:" + port + "/student", student);
        lastID = studentService.findLastID();
        Student actual = studentService.findStudentByID(lastID);
        Assertions.assertThat(!expected.equals(actual)).isTrue();
        testRestTemplate.put("http://localhost:" + port + "/student", expected);

    }

    @Test
    public void putMethodSecondTest() {

//        Assertions.assertThatThrownBy(()->testRestTemplate.put("http://localhost:" + port + "/student", null));// не получится , код возвращает статус а не эксепшн
        Student anyStudent = new Student(Long.MAX_VALUE, "test", Integer.MAX_VALUE);
        Long lastID = studentService.findLastID();
        Student expected = studentService.findStudentByID(lastID);
        anyStudent.setId(lastID);
        testRestTemplate.put("http://localhost:" + port + "/student", anyStudent);
        lastID = studentService.findLastID();
        Student actual = studentService.findStudentByID(lastID);
        Assertions.assertThat(expected.equals(actual)).isTrue();

    }

    @Test
    public void deleteMethodTests() {
        Long lastID = studentService.findLastID();
        testRestTemplate.postForObject("http://localhost:" + port + "/student", student, String.class);
        Long addedLastID = studentService.findLastID();
        URI uri = URI.create("http://localhost:" + port + "/student/" + addedLastID);
        testRestTemplate.delete(uri);
        Assertions.assertThat(studentService.findLastID() == lastID);

    }


    @Test
    public void findForAgeMethodTests() throws JsonProcessingException {
        Student expectedStudent = new Student(Long.MAX_VALUE, "Test", Integer.MAX_VALUE);
        testRestTemplate.postForObject("http://localhost:" + port + "/student", expectedStudent, Student.class);
        Long lastID = studentService.findLastID();
        String actual = testRestTemplate.getForObject("http://localhost:" + port + "/student/age?age=" + Integer.MAX_VALUE, String.class);
        expectedStudent.setId(lastID);
        ObjectWriter ow = new ObjectMapper().writer();
        String expectedJson = List.of(ow.writeValueAsString(expectedStudent)).toString();
        boolean equals = actual.equals(expectedJson);
        System.out.println("equals = " + equals);
        Assertions.assertThat(equals).isTrue();
        testRestTemplate.delete("http://localhost:" + port + "/student/" + lastID);

    }
    @Test
    public void findByAgeBetweenMethodTests() throws JsonProcessingException {
        Student expectedStudent = new Student(Long.MAX_VALUE, "Test", Integer.MAX_VALUE-1);
        testRestTemplate.postForObject("http://localhost:" + port + "/student", expectedStudent, Student.class);
        Long lastID = studentService.findLastID();
        String actual = testRestTemplate.getForObject("http://localhost:" + port + "/findByAgeBetween?min=" + (Integer.MAX_VALUE-2)+"&max="+Integer.MAX_VALUE, String.class);
        expectedStudent.setId(lastID);
        ObjectWriter ow = new ObjectMapper().writer();
        String expectedJson = List.of(ow.writeValueAsString(expectedStudent)).toString();
        boolean equals = actual.equals(expectedJson);
        System.out.println("equals = " + equals);
        Assertions.assertThat(equals).isTrue();
        testRestTemplate.delete("http://localhost:" + port + "/student/" + lastID);

    }
}
//    @GetMapping("/findByAgeBetween")
//    public Collection<Student> findByAgeBetween(@RequestParam int min,
//                                                @RequestParam int max){
//        return studentService.findByAgeBetween(min, max);
//    }