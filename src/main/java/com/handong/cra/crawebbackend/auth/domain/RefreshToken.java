package com.handong.cra.crawebbackend.auth.domain;

import com.handong.cra.crawebbackend.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseEntity {
    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String refreshToken;

    public RefreshToken(String username, String refreshToken) {
        this.username = username;
        this.refreshToken = refreshToken;
    }

    public static RefreshToken of(final String username, final String refreshToken) {
        return new RefreshToken(username, refreshToken);
    }
}
