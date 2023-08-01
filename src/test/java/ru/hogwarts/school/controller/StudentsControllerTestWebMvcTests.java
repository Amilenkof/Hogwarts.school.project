package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
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
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private StudentService studentService;
    @SpyBean// то же что и MockBean но если не переопределить поведение объекта запустится реальный код
    private FacultyService facultyService;
    @SpyBean
    private AvatarService avatarService;
    @InjectMocks
    private StudentController studentController;

    private final String name = "Vasiliy";
    private final int age = 100500;
    private Student student = new Student(1L, name, age);


    @Test
    public void createMethodTests() throws Exception {

        JSONObject jsonStudent = new JSONObject();
        jsonStudent.put("id", "1");
        jsonStudent.put("name", name);
        jsonStudent.put("age", age);

        Mockito.when(studentRepository.save(any(Student.class))).thenReturn(student);


        mockMvc.perform(MockMvcRequestBuilders//у mockMVC -вызываем Perform - метод который отправляет запрос
                        .post("/student")// указываем тип запроса и маппинг
                        .content(jsonStudent.toString())// передает тело запроса
                        .contentType(MediaType.APPLICATION_JSON)// указываем контент тайп запроса
                        .accept(MediaType.APPLICATION_JSON))/***указываем в каком формате должны получить тело ответа?*/
                .andExpect(MockMvcResultMatchers.status().isOk())// c этого момента начинаются проверки - это проверяет статус - равен ли он 200
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(100500));


    }

    @Test
    public void readMethodTests() throws Exception {
        Mockito.when(studentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(age));

    }

    @Test
    public void updateMethodTests() throws Exception {
        JSONObject jsonStudent = new JSONObject();
        jsonStudent.put("id", 1);
        jsonStudent.put("name", name + name);
        jsonStudent.put("age", age);
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(jsonStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name + name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(age));
    }

    @Test
    public void deleteMethodTests() throws Exception {
        Mockito.when(studentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(age));


//        Mockito.verify(studentService, Mockito.only()).delete(Mockito.anyLong());
    }
}
