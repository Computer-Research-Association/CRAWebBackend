package com.handong.cra.crawebbackend.user.repository;

import com.handong.cra.crawebbackend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
    public User findByUsernameAndPassword(String username, String password);
    public User findByNameAndStudentIdAndEmail(String name, Long studentId, String email);
    public User findByEmail(String email);

    public User getUserById(Long userId);
}
