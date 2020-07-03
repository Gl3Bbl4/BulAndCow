package com.game.jpa;

import com.game.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
