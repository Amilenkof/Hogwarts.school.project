package ru.hogwarts.school.serviceTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class StudentsServiceTests {
    private final StudentService studentService = new StudentService();
    private final Student harryPotter = new Student(1L, "Harry Potter", 11);
    private final Student drakoMalfoy = new Student(1L, "Drako Malfoy", 12);

    @Test
    public void readMethodTests() {
        //test2 negative
        var actual = (studentService.read(10005000L));
        assertThat(actual == null);

        //test1 positive
        var res= studentService.create(harryPotter);
        assertThat((studentService.read(2L)).equals(harryPotter));




    }

    @Test
    public void createMethodTests() {

        //test1 positive

        var actual = studentService.create(harryPotter);
        assertThat(actual.equals(harryPotter));

        //test2 negative

        actual = studentService.create(null);
        assertThat(actual == null);

        //test 3 posive-size
        assertThat(studentService.getAll().size() == 1);
    }



    @Test
    public void deleteMethodTests() {
        //test1 negative
        assertThat((studentService.delete(100005000L)) == null);


        //test2 positive
        studentService.create(harryPotter);

        assertThat((studentService.delete(3L)).equals(harryPotter));

        //test3 positive size
        studentService.create(harryPotter);
        int sizeBeforeDelete = studentService.getAll().size();
        studentService.delete(1L);
        int sizeAfterDelete = studentService.getAll().size();
        assertThat(sizeBeforeDelete - sizeAfterDelete == 1);

    }

    @Test
    public void updateMethodTests() {
        //test1 positive
        var studentBeforeUpdate = studentService.create(harryPotter);
        studentService.update(drakoMalfoy);
        var studentAfterUpdate = studentService.read(1L);
        assertThat(!studentBeforeUpdate.equals(studentAfterUpdate));

        //test2 negative
        Student anyStudent = new Student(1000L, "student", 111);
        var actual = studentService.update(anyStudent);
        assertThat(!studentService.getAll().containsValue(anyStudent));

        //test3 negative-return null if updated wrong faculty
        assertThat(actual == null);
    }

    @Test
    public void findForColorTests(){
        studentService.create(harryPotter);
        studentService.create(drakoMalfoy);
        //test1 positive
        var actual = studentService.findForAge(11);
        assertThat(actual.equals(List.of(harryPotter)));
        //test2 not found this color
        actual = studentService.findForAge(1);
        assertThat(actual.equals(List.of()));

    }
}
