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
import ru.hogwarts.school.model.Faculty;
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
public class FacultyControllerTestWebMvcTests {
    @Autowired
    private MockMvc mockMvc;//класс позволяет тестить контроллеры без запуска HTTP сервера
    @MockBean// помещает реализацию обьект в контекст
    private FacultyRepository facultyRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private StudentService studentService;
    @SpyBean// то же что и MockBean но если не переопределить поведение объекта запустится реальный код
    private FacultyService facultyService;
    @SpyBean
    private AvatarService avatarService;
    @InjectMocks
    private StudentController studentController;

    private final String name = "TestFaculty";
    private final String color = "black";
    private Faculty faculty = new Faculty(1L, name, color);


    @Test
    public void createMethodTests() throws Exception {

        JSONObject jsonStudent = new JSONObject();
        jsonStudent.put("id", "1");
        jsonStudent.put("name", name);
        jsonStudent.put("color", color);

        Mockito.when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);


        mockMvc.perform(MockMvcRequestBuilders//у mockMVC -вызываем Perform - метод который отправляет запрос
                        .post("/faculty")// указываем тип запроса и маппинг
                        .content(jsonStudent.toString())// передает тело запроса
                        .contentType(MediaType.APPLICATION_JSON)// указываем контент тайп запроса
                        .accept(MediaType.APPLICATION_JSON))/***указываем в каком формате должны получить тело ответа?*/
                .andExpect(MockMvcResultMatchers.status().isOk())// c этого момента начинаются проверки - это проверяет статус - равен ли он 200
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(color));


    }

    @Test
    public void readMethodTests() throws Exception {
        Mockito.when(facultyRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(color));

    }

    @Test
    public void updateMethodTests() throws Exception {
        JSONObject jsonStudent = new JSONObject();
        jsonStudent.put("id", 1);
        jsonStudent.put("name", name + name);
        jsonStudent.put("color", color);
        Mockito.when(facultyRepository.save(Mockito.any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(jsonStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name + name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(color));
    }

    @Test
    public void deleteMethodTests() throws Exception {
        Mockito.when(facultyRepository.findById(anyLong())).thenReturn(Optional.ofNullable(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(color));


//        Mockito.verify(studentService, Mockito.only()).delete(Mockito.anyLong());
    }
}
