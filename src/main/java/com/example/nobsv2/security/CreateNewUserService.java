package com.example.nobsv2.security;

import com.example.nobsv2.Command;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateNewUserService implements Command<CustomUser, String> {

    private final PasswordEncoder encoder;

    private final CustomUserRepository customUserRepository;

    public CreateNewUserService(PasswordEncoder encoder, CustomUserRepository customUserRepository) {
        this.encoder = encoder;
        this.customUserRepository = customUserRepository;
    }

    @Override
    public ResponseEntity<String> execute(CustomUser user) {
        Optional<CustomUser> optionalUser = customUserRepository.findById(user.getUsername());
        if (!optionalUser.isPresent()) {
            customUserRepository.save(new CustomUser(user.getUsername(), encoder.encode(user.getPassword())));
            return ResponseEntity.ok("Success");
        }

        return ResponseEntity.badRequest().body("Failure");
    }
}
