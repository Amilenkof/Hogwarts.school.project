package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exeption.AvatarNotFoundException;
import ru.hogwarts.school.exeption.StudentNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Scope("singleton")
//@Transactional
public class AvatarService {
    @Value("${path.to.avatars.folder}")
    private String pathAdress;
    private final AvatarRepository avatarRepository;
    //    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final Logger logger = LoggerFactory.getLogger(AvatarService.class);


    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;

    }


    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoke method for upload avatar for student with id={}",studentId);
        Student student = studentService.read(studentId).orElseThrow(() -> new StudentNotFoundException("Указанный студент не найден"));//находим студента с указанным id

        Path path = Path.of(pathAdress, studentId + "." + getExtensions(avatarFile.getOriginalFilename())); //создаем путь к нему (2 параметра 1-папка где лежит будет создан файл, 2 как называть новый файл) , строка из проперти,
        // указывает в какую папку создаем директорию, потом получаем расширение файла и склеиваем его с id- чтобы было уникальное значение
        logger.debug("Path to file ={}",path);
        Files.createDirectories(path.getParent());//проверяем есть ли папки по адресу ( в проперти  @Value("${path.to.avatars.folder}") private String pathAdress;) если нет он их создаст
        boolean isEmptyDirectory = Files.deleteIfExists(path);//проверяем есть ли уже такой файл, если есть удаляем
        logger.debug("File is exists = {}",isEmptyDirectory);

        try (InputStream inputStream = avatarFile.getInputStream();
             OutputStream outputStream = Files.newOutputStream(path, CREATE_NEW);) {
            inputStream.transferTo(outputStream);
        }

        Avatar avatar = avatarRepository.findAvatarByStudent_Id(studentId).orElse(new Avatar());// аватары создаются через сеттеры, мы получаем из SQL запроса какойто аватар по id студента, если он null
        logger.debug("Avatar from db without changes = {}",avatar);
        avatar.setStudent(student);
        avatar.setFilePath(path.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);


    }


    private String getExtensions(String s) {
        logger.info("Was invoke method for get extensions input file");
        return s.substring(s.lastIndexOf(".") + 1);
    }

    public Avatar findAvatarByStudentId(Long studentId) {

        Optional<Avatar> result = avatarRepository.findAvatarByStudent_Id(studentId);
        if (result.isEmpty()){
            logger.error("Student with id ={} not found",studentId);
        }
        return result.orElseThrow(() -> new AvatarNotFoundException("Аватар с указанным id не найден"));
    }

    public List<Avatar> getAllAvatars(int page, int size) {
        logger.info("Was invoke method for get all avatars");

        PageRequest pageRequest = PageRequest.of(page, size);
        return avatarRepository.findAll(pageRequest).getContent();
//        return allAvatars.stream().map(Avatar::getData).toList();

    }
}
