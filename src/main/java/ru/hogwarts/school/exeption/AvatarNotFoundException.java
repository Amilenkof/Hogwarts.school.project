package ru.hogwarts.school.exeption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AvatarNotFoundException extends  RuntimeException{

    public AvatarNotFoundException(String message) {
        super(message);
    }
}
