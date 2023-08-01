package ru.hogwarts.school;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerSchoolApplicationTests {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private FacultyService facultyService;
    private final Long id = 2010L;
    private final Faculty faculty = new Faculty(id,"TestFaculty","black");

    @Test
    void contextLoads() {
        Assertions.assertThat(facultyController).isNotNull();
    }


    @Test
    public void createMethodTests() throws URISyntaxException {
        //test1 not null
        Assertions.assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, String.class))
                .isNotNull();
        Long lastIndex = facultyService.findLastID();
//удаление 1 вариант
        testRestTemplate.delete("http://localhost:" + port + "/faculty/"+lastIndex);//удаляем студента, которого создали в тесте из БД\

        //удаление через exchange
//        URI uri = new URI("http://localhost:" + port + "/faculty/" + lastIndex);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<Faculty> request = new HttpEntity<>(faculty, headers);
//        RequestEntity<Faculty> requestEntity = new RequestEntity(HttpMethod.DELETE, uri);
//        testRestTemplate.exchange(requestEntity, Student.class);


    }

    @Test
    public void createMethodSecondTest() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer();
        String json = ow.writeValueAsString(faculty);// получаем Json предствление обьекта
        String actual = testRestTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, String.class);
        Assertions.assertThat(actual.equals(json)).isTrue();
        Long lastIndex = facultyService.findLastID();
        testRestTemplate.delete("http://localhost:" + port + "/faculty/" + lastIndex);
    }

    @Test
    public void readMethodTests() {
        Long readId = facultyService.findLastID();
       Optional< Faculty> expected = facultyService.read(readId);
        //test1 not null
        Assertions.assertThat(testRestTemplate.getForObject("http://localhost:" + port + "/faculty/" + readId, Faculty.class))
                .isNotNull();
        // test 2 get student from db
        var actual = testRestTemplate.getForObject("http://localhost:" + port + "/faculty/" + readId, Faculty.class);
        Assertions.assertThat(actual.equals(expected.get())).isTrue();
    }

    @Test
    public void putMethodTests() {
        Long lastID = facultyService.findLastID();
        Optional<Faculty> expected = facultyService.read(lastID);
        faculty.setId(lastID);
        testRestTemplate.put("http://localhost:" + port + "/faculty", faculty);
        lastID = facultyService.findLastID();
        Optional<Faculty> actual = facultyService.read(lastID);
        Assertions.assertThat(!expected.equals(actual)).isTrue();
        testRestTemplate.put("http://localhost:" + port + "/faculty", expected);

    }

    @Test

    public void putMethodSecondTest() {

//        Assertions.assertThatThrownBy(()->testRestTemplate.put("http://localhost:" + port + "/student", null));// не получится , код возвращает статус а не эксепшн
        Faculty anyfaculty = new Faculty(Long.MAX_VALUE, "test", "black");
        Long lastID = facultyService.findLastID();
        Optional<Faculty> savedFaculty = facultyService.read(lastID);
        anyfaculty.setId(lastID);
        testRestTemplate.put("http://localhost:" + port + "/faculty", anyfaculty);
        lastID = facultyService.findLastID();
        Optional<Faculty> actual = facultyService.read(lastID);
        Assertions.assertThat(Optional.of(anyfaculty).equals(actual)).isTrue();

        testRestTemplate.put("http://localhost:" + port + "/faculty", savedFaculty);
    }


    @Test
    public void deleteMethodTests() {
        Long lastID = facultyService.findLastID();
        testRestTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, String.class);
        Long addedLastID = facultyService.findLastID();
        URI uri = URI.create("http://localhost:" + port + "/faculty/" + addedLastID);
        testRestTemplate.delete(uri);
        Assertions.assertThat(Objects.equals(facultyService.findLastID(), lastID)).isTrue();

    }


    @Test
    public void findForColorMethodTests() throws JsonProcessingException {
        Faculty expectedFaculty = new Faculty(Long.MAX_VALUE, "Test", "black");
        testRestTemplate.postForObject("http://localhost:" + port + "/faculty", expectedFaculty, Faculty.class);
        Long lastID = facultyService.findLastID();
        String  actual = testRestTemplate.getForObject("http://localhost:" + port + "/faculty/color?color=black", String.class);
        expectedFaculty.setId(lastID);
        ObjectWriter ow = new ObjectMapper().writer();
        String expectedJson = List.of(ow.writeValueAsString(expectedFaculty)).toString();
        boolean equals = actual.equals(expectedJson);
        System.out.println("equals = " + equals);
        Assertions.assertThat(equals).isTrue();
        testRestTemplate.delete("http://localhost:" + port + "/faculty/" + lastID);

    }
//    @Test
//    public void findByAgeBetweenMethodTests() throws JsonProcessingException {
//        Student expectedStudent = new Student(Long.MAX_VALUE, "Test", Integer.MAX_VALUE-1);
//        testRestTemplate.postForObject("http://localhost:" + port + "/student", expectedStudent, Student.class);
//        Long lastID = facultyService.findLastID();
//        String actual = testRestTemplate.getForObject("http://localhost:" + port + "/findByAgeBetween?min=" + (Integer.MAX_VALUE-2)+"&max="+Integer.MAX_VALUE, String.class);
//        expectedStudent.setId(lastID);
//        ObjectWriter ow = new ObjectMapper().writer();
//        String expectedJson = List.of(ow.writeValueAsString(expectedStudent)).toString();
//        boolean equals = actual.equals(expectedJson);
//        System.out.println("equals = " + equals);
//        Assertions.assertThat(equals).isTrue();
//        testRestTemplate.delete("http://localhost:" + port + "/student/" + lastID);
//
//    }
}
//    @GetMapping("/findByAgeBetween")
//    public Collection<Student> findByAgeBetween(@RequestParam int min,
//                                                @RequestParam int max){
//        return studentService.findByAgeBetween(min, max);
//    }