package com.ucd.CenterRDV.services;

import com.ucd.CenterRDV.models.ApplicationUser;
import com.ucd.CenterRDV.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private Map<String, Long> tokens = new HashMap<>();

    @Autowired
    public PasswordResetService(UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public void sendPasswordResetRequest(String email) throws MessagingException {
        ApplicationUser user = userRepository.findByUserName(email)
                .orElseThrow(() -> new IllegalArgumentException("Email address not found"));

        String token = generatePasswordResetToken(user);

        long expirationTime = System.currentTimeMillis() + (30 * 60 * 1000);
        this.storeToken(token, expirationTime);

        emailService.sendPasswordResetEmail(user.getUsername(), token);
    }

    private String generatePasswordResetToken(ApplicationUser user) {
        String token = UUID.randomUUID().toString();
        String userToken = token;
        return token;
    }

    public void resetPassword(String token, String newPassword,String email) {
        String userToken = retrieveTemporaryToken();

        if (userToken == null || !userToken.equals(token)) {
            throw new IllegalArgumentException("Invalid or expired token");
        }

        ApplicationUser user = userRepository.findByUserName(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found for token"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public boolean isValidToken(String token) {
        if (!tokens.containsKey(token)) {
            return false;
        }

        long expirationTime = tokens.get(token);
        if (expirationTime < System.currentTimeMillis()) {
            tokens.remove(token);
            return false;
        }

        return true;
    }

    private void storeToken(String token, long expirationTime) {
        tokens.put(token, expirationTime);
    }

    private String retrieveTemporaryToken() {
        for (Map.Entry<String, Long> entry : tokens.entrySet()) {
            if (entry.getValue() > System.currentTimeMillis()) {
                return entry.getKey();
            }
        }
        return null;
    }

}
