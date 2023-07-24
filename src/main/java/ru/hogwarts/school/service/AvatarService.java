package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.hogwarts.school.repository.AvatarRepository;

@Service
@Scope("singleton")
public class AvatarService {
    private final AvatarRepository avatarRepository;


    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }


}
