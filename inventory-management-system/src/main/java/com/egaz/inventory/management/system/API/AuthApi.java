package com.egaz.inventory.management.system.API;

import com.egaz.inventory.management.system.model.User;
import com.egaz.inventory.management.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthApi {

    @Autowired private UserRepository userRepository;
    @Autowired private JavaMailSender mailSender;
    @Autowired private PasswordEncoder passwordEncoder;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");

        // ✅ FIXED: no double Optional
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.ok(Map.of("message", "If the email exists, a reset link was sent."));
        }

        User user = optionalUser.get();
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpires(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        String link = frontendUrl + "/reset-password/" + token;

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("youremail@gmail.com"); // MUST match spring.mail.username
        msg.setTo(user.getEmail());
        msg.setSubject("Password Reset");
        msg.setText("Click to reset your password:\n" + link + "\n\nExpires in 1 hour.");
        mailSender.send(msg);

        return ResponseEntity.ok(Map.of("message", "If the email exists, a reset link was sent."));
    }

    @GetMapping("/verify-reset-token/{token}")
    public ResponseEntity<?> verifyToken(@PathVariable String token) {
        // ✅ already correct
        Optional<User> user = userRepository.findByResetToken(token);
        if (user.isEmpty() || user.get().getResetTokenExpires().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid or expired token"));
        }
        return ResponseEntity.ok(Map.of("valid", true));
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable String token,
                                           @RequestBody Map<String, String> body) {
        // ✅ FIXED: no double Optional
        Optional<User> optionalUser = userRepository.findByResetToken(token);

        if (optionalUser.isEmpty() || optionalUser.get().getResetTokenExpires().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid or expired token"));
        }

        String newPassword = body.get("password");
        if (newPassword == null || newPassword.length() < 8) {
            return ResponseEntity.badRequest().body(Map.of("message", "Password must be at least 8 characters"));
        }

        User user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpires(null);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Password reset successful"));
    }
}