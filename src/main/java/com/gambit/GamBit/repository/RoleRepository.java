package com.gambit.GamBit.repository;

import com.gambit.GamBit.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
