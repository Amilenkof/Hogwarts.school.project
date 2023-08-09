package ru.hogwarts.school.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Avatar;

import java.util.List;
import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar,Long> {
   Optional <Avatar> findAvatarByStudent_Id(Long studentId);

//   @Query(value = "SELECT data FROM avatar", nativeQuery = true)
   List<Avatar> findAll();



}
