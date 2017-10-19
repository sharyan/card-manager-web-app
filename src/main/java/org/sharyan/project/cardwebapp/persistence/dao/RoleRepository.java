package org.sharyan.project.cardwebapp.persistence.dao;

import org.sharyan.project.cardwebapp.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);
}

