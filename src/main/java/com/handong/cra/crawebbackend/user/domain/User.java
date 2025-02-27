package com.handong.cra.crawebbackend.user.domain;

import com.handong.cra.crawebbackend.auth.dto.SignupDto;
import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import com.handong.cra.crawebbackend.user.dto.UpdateUserDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;

    @Setter
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name; // 본명

    @Column(nullable = false)
    private String email;

    @Column(name = "github_id", nullable = false)
    private String githubId;

    private UserRoleSet roles;

    @Column(name = "student_id", unique = true, nullable = false)
    private String studentId;

    @Column(nullable = false)
    private String term;

    //    @Column(nullable = false)
    private String greetingMessage;

    @Setter
    @Column(name = "img_url")
    private String imgUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "board_id")
    )
    private List<Board> likedBoards = new ArrayList<>();

    @Setter
    private LocalDateTime lastLoginAt;

    public void activeUser() {
        super.setDeleted(false);
    }

    public User(String username, String name, String password, String githubId, String email, UserRoleSet role, String studentId, String term) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.githubId = githubId;
        this.email = email;
        this.roles = role;
        this.studentId = studentId;
        this.term = term;
    }

    public User(SignupDto signupDto) {
        this.username = signupDto.getUsername();
        this.name = signupDto.getName();
        this.password = signupDto.getPassword();
        this.githubId = signupDto.getGithubId();
        this.email = signupDto.getEmail();
        this.roles = signupDto.getRoles();
        this.term = signupDto.getTerm();
        this.studentId = signupDto.getStudentId();
    }

    public static User from(SignupDto signupDto) {
        return new User(signupDto);
    }

    public void likeBoard(Board board) {
        this.likedBoards.add(board);
    }

    public void unlikeBoard(Board board) {
        this.likedBoards.remove(board);

    }


    public User update(UpdateUserDto updateUserDto) {
        this.name = updateUserDto.getName();
        this.studentId = updateUserDto.getStudentId();
        this.term = updateUserDto.getTerm();
        this.email = updateUserDto.getEmail();
        this.githubId = updateUserDto.getGithubId();
        this.greetingMessage = updateUserDto.getGreetingMessage();
        return this;
    }
}
