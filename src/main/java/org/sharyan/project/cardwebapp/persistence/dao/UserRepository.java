package org.sharyan.project.cardwebapp.persistence.dao;

import org.sharyan.project.cardwebapp.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(@NotNull String username);
}