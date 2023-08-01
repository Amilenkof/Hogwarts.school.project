package ru.hogwarts.school;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@WebMvcTest//подгружает все зависимости (только для указанного контроллера, другие ее не интересуют)
public class StudentsControllerTestWebMvcTests {
    @Autowired
    private MockMvc mockMvc;//класс позволяет тестить контроллеры без запуска HTTP сервера
    @MockBean// помещает реализацию обьект в контекст
    private StudentRepository studentRepository;
    @SpyBean// то же что и MockBean но если не переопределить поведение объекта запустится реальный код
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    private Student student = new Student(100500L, "Vasiliy", 100500);
//
//@PostMapping
//public ResponseEntity<Student> create(@RequestBody Student student) {
//    if (student != null) {
//        studentService.create(student);
//        return ResponseEntity.ok(student);
//    }
//    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//}
@Test
    public void createMethodTests() throws Exception {
    JSONObject jsonStudent = new JSONObject();
    jsonStudent.put("name", "Vasiliy");
    jsonStudent.put("age", 100500);

    Mockito.when(studentRepository.save(any(Student.class))).thenReturn(student);
    Mockito.when(studentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(student));

    mockMvc.perform(MockMvcRequestBuilders//у mockMVC -вызываем Perform - метод который отправляет запрос
                    .post("/student")// указываем тип запроса и маппинг
                    .content(jsonStudent.toString())// передает тело запроса
                    .contentType(MediaType.APPLICATION_JSON)// указываем контент тайп запроса
                    .accept(MediaType.APPLICATION_JSON))/***указываем в каком формате должны получить тело ответа?*/
            .andExpect(MockMvcResultMatchers.status().isOk())// c этого момента начинаются проверки - это проверяет статус - равен ли он 200
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(anyLong()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Vasiliy"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(100500));

}




}
