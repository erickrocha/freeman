package com.erocha.freeman.security.usecases;

import com.erocha.freeman.commons.exceptions.ResourceNotFoundException;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.gateways.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UpdatePassword {


    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UpdatePassword(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User execute(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(ResourceNotFoundException::new);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setFirstLogin(false);
        return userRepository.save(user);
    }
}
