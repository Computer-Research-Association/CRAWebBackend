package com.handong.cra.crawebbackend.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRoleSet {
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<UserRoleEnum> roles = new HashSet<>();

    public UserRoleSet(final Collection<UserRoleEnum> roles) {
        this.roles.addAll(roles);
    }

    public boolean hasRole(final UserRoleEnum role) {
        return roles.contains(role);
    }

    public static UserRoleSet getDefault(UserRoleEnum role) {
        Set<UserRoleEnum> returnRoles = new HashSet<>();
        returnRoles.add(role);

        return new UserRoleSet(returnRoles);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());
    }
}
