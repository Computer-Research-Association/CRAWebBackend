package com.handong.cra.crawebbackend.user.repository;

import com.handong.cra.crawebbackend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
    public User findByUsernameAndPassword(String username, String password);
    public User findByNameAndStudentIdAndEmail(String name, Long studentId, String email);
    public User findByEmail(String email);

    public User getUserById(Long userId);
    public List<User> findAllByLastLoginAtBeforeAndDeletedFalse(LocalDateTime time); // 휴면계정 대상
    public List<User> findAllByLastLoginAtBeforeAndDeletedTrue(LocalDateTime time); // 삭제 대상
}
