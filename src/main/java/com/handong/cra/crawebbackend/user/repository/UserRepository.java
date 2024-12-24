package com.handong.cra.crawebbackend.user.repository;

import com.handong.cra.crawebbackend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
