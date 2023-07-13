package ru.hogwarts.school.serviceTest;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


public class FacultyServiceTests {
    private final FacultyService facultyService = new FacultyService();
    private final Faculty facultyGriffindor = new Faculty(1L, "Griffindor", "Red");
    private final Faculty facultySlizerin = new Faculty(1L, "Slizerin", "Green");
    @Test
    public void readMethodTests() {
        //test2 negative
        var actual = (facultyService.read(10005000L));
        assertThat(actual == null);

        //test1 positive
        var res=facultyService.create(facultyGriffindor);
        assertThat((facultyService.read(2L)).equals(facultyGriffindor));




    }

    @Test
    public void createMethodTests() {

        //test1 positive

        var actual = facultyService.create(facultyGriffindor);
        assertThat(actual.equals(facultyGriffindor));

        //test2 negative
        Faculty faculty1 = null;
        actual = facultyService.create(faculty1);
        assertThat(actual == null);

        //test 3 posive-size
        assertThat(facultyService.getAll().size() == 1);
    }



    @Test
    public void deleteMethodTests() {
        //test1 negative
        assertThat((facultyService.delete(100005000L)) == null);


        //test2 positive
        facultyService.create(facultyGriffindor);

        assertThat((facultyService.delete(3L)).equals(facultyGriffindor));

        //test3 positive size
        facultyService.create(facultyGriffindor);
        int sizeBeforeDelete = facultyService.getAll().size();
        facultyService.delete(1L);
        int sizeAfterDelete = facultyService.getAll().size();
        assertThat(sizeBeforeDelete - sizeAfterDelete == 1);

    }

    @Test
    public void updateMethodTests() {
        //test1 positive
        var facultyBeforeUpdate = facultyService.create(facultyGriffindor);
        facultyService.update(facultySlizerin);
        var facultyAfterUpdate = facultyService.read(1L);
        assertThat(!facultyBeforeUpdate.equals(facultyAfterUpdate));

        //test2 negative
        Faculty facultyHufflepuff = new Faculty(1000L, "Hufflepuff", "puple");
        var actual = facultyService.update(facultyHufflepuff);
        assertThat(!facultyService.getAll().containsValue(facultyHufflepuff));

        //test3 negative-return null if updated wrong faculty
        assertThat(actual == null);
    }


    @Test
    public void findForColorTests(){
        facultyService.create(facultyGriffindor);
        facultyService.create(facultySlizerin);
        //test1 positive
        var actual = facultyService.findForColor("Red");
        assertThat(actual.equals(List.of(facultyGriffindor)));
        //test2 not found this color
        actual = facultyService.findForColor("White");
        assertThat(actual.equals(List.of()));

    }
}
