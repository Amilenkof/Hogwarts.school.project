package ru.hogwarts.school.serviceTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;

import java.util.List;

@ExtendWith(MockitoExtension.class)

public class AvatarServiceTest {
    @Mock
    private AvatarRepository avatarRepository;
    @InjectMocks
    private AvatarService avatarService;

    @Test
    public void getAllAvatarsTest(){
        List<Avatar> avatars = List.of(new Avatar());
        Mockito.when(avatarRepository.findAll()).thenReturn(avatars);
        Assertions.assertThat(avatarService.getAllAvatars(1, 1).equals(avatars)).isTrue();

    }

}
