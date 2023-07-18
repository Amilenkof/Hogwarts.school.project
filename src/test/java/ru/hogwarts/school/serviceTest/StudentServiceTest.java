package ru.hogwarts.school.serviceTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService studentService;
    private final Student student = new Student(1L, "test", 11);
    private final Student student2 = new Student(2L, "test1", 12);

    @Test
    public void createMethodTests() {

        when(studentRepository.save(student)).thenReturn(student);
        assertThat(studentService.create(student).equals(student));



    }

    @Test
    public void readMethodTest() {
        Optional<Student> optional = Optional.of(student);
        when(studentRepository.findById(1L)).thenReturn(optional);
        assertThat(studentService.read(1L).equals(optional));
    }
    @Test
    public void negativeReadMethodTest(){
        Optional<Student> optional = Optional.empty();
        when(studentRepository.findById(1L)).thenReturn(optional);
        assertThat(studentService.read(1L).equals(Optional.empty()));

    }

    @Test
    public void deleteMethodTest(){
        studentService.delete(1L);
        verify(studentRepository, only()).deleteById(1L);

    }
    @Test
    public void getallMethodTest() {
        List<Student> list=new ArrayList<>();
        assertThat(studentService.getAll().size() == 0);
        assertThat(studentService.getAll().equals(list));
        list.add(student);
        when(studentService.getAll()).thenReturn(list);
        assertThat(studentService.getAll().equals(list));
        assertThat(studentService.getAll().size() == 1);

    }
    @Test
    public void findForColorTest() {
        List<Student> list=new ArrayList<>();
        list.add(student);
        list.add(student2);
        when(studentService.getAll()).thenReturn(list);
        List<Student> result = new ArrayList<>();
        result.add(student);
        assertThat(studentService.findForAge(11).equals(result));
        assertThat(studentService.findForAge(100500).equals(new ArrayList<>()));

    }

}
