package com.handong.cra.crawebbackend.user.repository;

import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.user.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"likedBoards"})
    public Optional<User> findById(Long userId);

    public User findByUsername(String username);

    public User findByNameAndStudentIdAndEmail(String name, String studentId, String email);

    public User findByEmail(String email);

    public User getUserById(Long userId);

    public List<User> findAllByLastLoginAtBeforeAndDeletedFalse(LocalDateTime time); // 휴면계정 대상

    public List<User> findAllByLastLoginAtBeforeAndDeletedTrue(LocalDateTime time); // 삭제 대상

    public List<User> findAllByName(String name);


//    @Query(value = "SELECT * FROM user " + "WHERE SUBSTRING(student_id, 2, 2) = :entranceYear", nativeQuery = true)
//    public List<User> findByStudentCodeNative(@Param("entranceYear") String entranceYear);

//    public List<User> findAllByTerm(String term);
}
