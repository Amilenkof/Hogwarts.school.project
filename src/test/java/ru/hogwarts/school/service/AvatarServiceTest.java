package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import org.assertj.core.api.Assertions;

@ExtendWith(MockitoExtension.class)
public class AvatarServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Mock
    AvatarRepository avatarRepository;
    @Mock
    private StudentService studentService1;

    private AvatarService avatarService;


    private final Student studentHarry = new Student(1L,"Harry",12);
    @Mock

    private final Avatar avatar = new Avatar();

    @BeforeEach
    public void setUp() throws IOException {
        Files.createDirectories(Path.of("/avatar-test"));
        avatarService = new AvatarService(avatarRepository, studentService);

    }


//        public Avatar findAvatarByStudentId(Long studentId) {
//        return avatarRepository.findAvatarByStudent_Id(studentId).orElseThrow(() -> new AvatarNotFoundException("Аватар с указанным id не найден"));
//    }
@Test
public void findAvatarByStudentIDMethodTest(){
    Mockito.when(avatarRepository.findAvatarByStudent_Id(1L)).thenReturn(Optional.of(avatar));
    Assertions.assertThat(avatarService.findAvatarByStudentId(1L).equals(avatar));
    Mockito.when(avatarRepository.findAvatarByStudent_Id(2L)).thenReturn(Optional.empty());
    Assertions.assertThatThrownBy(()-> avatarService.findAvatarByStudentId(2L));


}
    @Test
    public void uploadAvatarMethodTests() throws IOException {
        Mockito.when(studentService.read(Mockito.anyLong())).thenReturn(Optional.of(studentHarry));
        byte[] arr = {1, 2, 3, 4, 5, 6};
        MultipartFile file = new MockMultipartFile("name",arr);
        Mockito.when(avatarService.getExtensions(Mockito.anyString())).thenReturn("123.jpeg");

        avatarService.uploadAvatar(1L, file);
        Mockito.verify(avatarService,Mockito.times(1)).uploadAvatar(1L,file);


//

//        facultyService.deleteFaculty(1L);
//
//        verify(facultyRepository, times(1)).deleteById(1L)


    }
}


//    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
//        Student student = studentService.read(studentId).orElseThrow(()->new StudentNotFoundException("Указанный студент не найден"));//находим студента с указанным id
//
//        Path path = Path.of(pathAdress, studentId + "." + getExtensions(avatarFile.getOriginalFilename())); //создаем путь к нему (2 параметра 1-папка где лежит будет создан файл, 2 как называть новый файл) , строка из проперти,
//        // указывает в какую папку создаем директорию, потом получаем расширение файла и склеиваем его с id- чтобы было уникальное значение
//
//        Files.createDirectories(path.getParent());//проверяем есть ли папки по адресу ( в проперти  @Value("${path.to.avatars.folder}") private String pathAdress;) если нет он их создаст
//        Files.deleteIfExists(path);//проверяем есть ли уже такой файл, если есть удаляем
//
//
//        try (InputStream inputStream = avatarFile.getInputStream();
//             OutputStream outputStream = Files.newOutputStream(path, CREATE_NEW);) {
//            inputStream.transferTo(outputStream);
//        }
//        System.out.println("student = " + student);
//        Avatar avatar = avatarRepository.findAvatarByStudent_Id(studentId).orElse(new Avatar());// аватары создаются через сеттеры, мы получаем из SQL запроса какойто аватар по id студента, если он null
//        avatar.setStudent(student);
//        avatar.setFilePath(path.toString());
//        avatar.setFileSize(avatarFile.getSize());
//        avatar.setMediaType(avatarFile.getContentType());
//        avatar.setData(avatarFile.getBytes());
//        avatarRepository.save(avatar);
//
//
//    }