package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {
    @Mock
    private FacultyRepository facultyRepository;
    @InjectMocks
    private FacultyService facultyService;
    private final Faculty faculty = new Faculty(1L, "test", "red");
    private final Faculty faculty2 = new Faculty(2L, "test1", "red1");

    @Test
    public void createMethodTests() {

        when(facultyRepository.save(faculty)).thenReturn(faculty);
        assertThat(facultyService.create(faculty).equals(faculty));


    }

    @Test
    public void readMethodTest() {
        Optional<Faculty> optional = Optional.of(faculty);
        when(facultyRepository.findById(1L)).thenReturn(optional);
        assertThat(facultyService.read(1L).equals(optional));
    }

    @Test
    public void negativeReadMethodTest() {
        Optional<Faculty> optional = Optional.empty();
        when(facultyRepository.findById(1L)).thenReturn(optional);
        assertThat(facultyService.read(1L).equals(Optional.empty()));

    }

    @Test
    public void deleteMethodTest() {
        facultyService.delete(1L);
        verify(facultyRepository, only()).deleteById(1L);

    }

    @Test
    public void getallMethodTest() {
        List<Faculty> list = new ArrayList<>();
        assertThat(facultyService.getAll().size() == 0);
        assertThat(facultyService.getAll().equals(list));
        list.add(faculty);
        when(facultyService.getAll()).thenReturn(list);
        assertThat(facultyService.getAll().equals(list));
        assertThat(facultyService.getAll().size() == 1);

    }

    @Test
    public void findForColorTest() {
        List<Faculty> list = new ArrayList<>();
        list.add(faculty);
        list.add(faculty2);
        when(facultyService.getAll()).thenReturn(list);
        List<Faculty> result = new ArrayList<>();
        result.add(faculty);
        assertThat(facultyService.findForColor("red").equals(result));
        assertThat(facultyService.findForColor("").equals(new ArrayList<>()));

    }

    @Test
    public void findByColorIgnoreCaseMethodTest() {
        when(facultyRepository.findByColorIgnoreCase("")).thenReturn(faculty);
        assertThat(facultyService.findByColorIgnoreCase("").equals(faculty));
    }

    @Test
    public void findByNameIgnoreCaseMethodTest() {
        when(facultyRepository.findByNameIgnoreCase("")).thenReturn(faculty);
        assertThat(facultyService.findByNameIgnoreCase("").equals(faculty));
    }

    @Test
    public void findByStudentMethodTest() {

        Student student = new Student(1L, "test", 11);
        student.setFaculty(faculty);
        when(facultyRepository.findByStudents_id(1L)).thenReturn(faculty);
        assertThat(facultyService.findByStudent(1L).equals(faculty));

    }

}