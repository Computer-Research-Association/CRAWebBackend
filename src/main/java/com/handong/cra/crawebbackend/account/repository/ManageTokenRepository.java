package com.handong.cra.crawebbackend.account.repository;

import com.handong.cra.crawebbackend.account.domain.ManageToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ManageTokenRepository extends JpaRepository<ManageToken, Long> {
    public ManageToken findByCode(String code);

}
