package com.handong.cra.crawebbackend.user.domain;

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
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(nullable = false)
    private String email;

    @Column(name = "github_id", nullable = false)
    private String githubId;

    @Column(nullable = false)
    private String role;

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

    public User(String userName, String githubId, String email, String role, Long studentNumber, String term) {
        this.userName = userName;
        this.githubId = githubId;
        this.email = email;
        this.role = role;
        this.studentNumber = studentNumber;
        this.term = term;
    }


    public void likeBoard(Board board){
        this.likedBoards.add(board);
    }
    public void unlikeBoard(Board board){
        this.likedBoards.remove(board);
    }
}
