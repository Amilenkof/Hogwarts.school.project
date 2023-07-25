package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exeption.StudentNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("student/avatar")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }


    @PostMapping(value = "/{studentId}/avatars", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 500) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            avatarService.uploadAvatar(studentId, avatar);
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/{studentId}/getAvatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long studentId) {
        Avatar avatar = avatarService.findAvatarByStudentId(studentId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(200).headers(headers).body(avatar.getData());

    }

    @GetMapping("/{studentId}/getAvatar-from-disc")
    public void downloadAvatar(@PathVariable Long studentId, HttpServletResponse response) throws IOException {

        Avatar avatar = avatarService.findAvatarByStudentId(studentId);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream inputStream = Files.newInputStream(path);
             OutputStream outputStream = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            inputStream.transferTo(outputStream);
        }


    }

}
