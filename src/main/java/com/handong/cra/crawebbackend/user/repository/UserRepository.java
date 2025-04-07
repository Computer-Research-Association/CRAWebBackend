package com.handong.cra.crawebbackend.user.repository;

import com.handong.cra.crawebbackend.user.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"likedBoards"})
    Optional<User> findById(Long userId);

    User findByUsername(String username);

    User findByNameAndStudentIdAndEmail(String name, String studentId, String email);

    User findByEmail(String email);

    User getUserById(Long userId);

    List<User> findAllByLastLoginAtBeforeAndDeletedFalse(LocalDateTime time); // 휴면계정 대상

    List<User> findAllByLastLoginAtBeforeAndDeletedTrue(LocalDateTime time); // 삭제 대상

    List<User> findAllByName(String name);

}
