package com.example.G4_DailyReport.service;

import com.example.G4_DailyReport.model.Role;
import com.example.G4_DailyReport.model.User;
import com.example.G4_DailyReport.repository.RoleRepository;
import com.example.G4_DailyReport.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    public void updateUser(User user, String password, Role... roles) {
        user.setPassword(encoder.encode(password));
        for (Role role : roles) {
            user.addRole(role);
        }
    }

    @Transactional
    public void grantRoles() {
        Role roleAdmin = roleRepository.findByName("ADMIN");
        Role roleUser = roleRepository.findByName("USER");
        Role roleManager = roleRepository.findByName("MANAGER");

        //Encode password into BScript type
        Optional<User> user = userRepository.findByUserName("user");
        user.ifPresent(value -> {
            updateUser(value, "123", roleUser);
            userRepository.save(value);
        });

        Optional<User> manager = userRepository.findByUserName("MANAGER");
        manager.ifPresent(value -> {
            updateUser(value, "123", roleManager, roleUser);
            userRepository.save(value);
        });

        Optional<User> admin = userRepository.findByUserName("ADMIN");
        admin.ifPresent(value -> {
            updateUser(value, "123", roleAdmin, roleUser);
            userRepository.save(value);
        });

        userRepository.flush();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return user.get();
    }
}
