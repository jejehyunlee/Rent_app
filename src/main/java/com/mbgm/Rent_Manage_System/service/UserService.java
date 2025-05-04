package com.mbgm.Rent_Manage_System.service;


import com.mbgm.Rent_Manage_System.entity.User;
import com.mbgm.Rent_Manage_System.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public void register(User user){
        if (userRepository.findByUsername(user.getUsername()) != null ) {
            throw new RuntimeException("Username already exists");
        }
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole());

        userRepository.save(user);
    }


}
