package org.sharyan.project.cardwebapp.security;

import org.sharyan.project.cardwebapp.persistence.dao.UserRepository;
import org.sharyan.project.cardwebapp.persistence.entity.Role;
import org.sharyan.project.cardwebapp.persistence.entity.User;
import org.sharyan.project.cardwebapp.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service("persistentUserDetailsService")
@Transactional
public class PersistentUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityLoginService securityLoginService;

    public PersistentUserDetailsService() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        if (securityLoginService.isBlockingUser(RequestUtils.getRequestIpAddress())) {
            // throw the same error as a normal flow, do not leak information that we block users
            throw new UsernameNotFoundException(("No user found with username: " + username));
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                true, true, true, true, getAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        return roles.stream().map(Role::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}


