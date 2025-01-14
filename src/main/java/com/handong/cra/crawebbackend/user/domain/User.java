package com.handong.cra.crawebbackend.user.domain;

import com.handong.cra.crawebbackend.auth.dto.SignupDto;
import com.handong.cra.crawebbackend.board.domain.Board;
import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name = "github_id", nullable = false)
    private String githubId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @Column(name = "student_number", unique = true, nullable = false)
    private Long studentNumber;

    @Column(nullable = false)
    private String term;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "board_id")
    )
    private List<Board> likedBoards = new ArrayList<>();

    public User(String username, String name, String password, String githubId, String email, UserRoleEnum role, Long studentNumber, String term) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.githubId = githubId;
        this.email = email;
        this.role = role;
        this.studentNumber = studentNumber;
        this.term = term;
    }

    public User(SignupDto signupDto) {
        this.username = signupDto.getUsername();
        this.name = signupDto.getName();
        this.password = signupDto.getPassword();
        this.githubId = signupDto.getGithubId();
        this.email = signupDto.getEmail();
        this.role = signupDto.getRole();
        this.term = signupDto.getTerm();
        this.studentNumber = signupDto.getStudentNumber();
    }

    public static User from(SignupDto signupDto) {
        return new User(signupDto);
    }

    public void likeBoard(Board board){
        this.likedBoards.add(board);
    }
    public void unlikeBoard(Board board){
        this.likedBoards.remove(board);
    }
}
