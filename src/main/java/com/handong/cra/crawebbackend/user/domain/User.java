package com.handong.cra.crawebbackend.user.domain;

import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

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

    public User(String userName, String githubId, String email, String role, Long studentNumber, String term) {
        this.userName = userName;
        this.githubId = githubId;
        this.email = email;
        this.role = role;
        this.studentNumber = studentNumber;
        this.term = term;
    }
}
