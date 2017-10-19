package org.sharyan.project.cardwebapp.service;

import org.sharyan.project.cardwebapp.dto.UserDto;
import org.sharyan.project.cardwebapp.error.UserAlreadyExistsException;
import org.sharyan.project.cardwebapp.persistence.dao.UserRepository;
import org.sharyan.project.cardwebapp.persistence.entity.Role;
import org.sharyan.project.cardwebapp.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new UserAlreadyExistsException();
        }

        return userRepository.save(User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(Collections.singletonList(new Role("ROLE_USER")))
                .build());
    }
}
