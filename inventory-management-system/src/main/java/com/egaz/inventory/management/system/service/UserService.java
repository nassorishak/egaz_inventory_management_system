package com.egaz.inventory.management.system.service;

import com.egaz.inventory.management.system.model.User;
import com.egaz.inventory.management.system.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() { return userRepository.findAll(); }
    public Optional<User> findById(Integer userId) { return userRepository.findById(userId); }

    /**
     * Save a NEW user. Automatically hashes the password if it's not already a BCrypt hash.
     */
    public User save(User user) {
        hashIfNeeded(user);
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        if (user.getUserId() == null) {
            throw new IllegalArgumentException("User ID must not be null for update.");
        }
        User existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new RuntimeException("User with ID " + user.getUserId() + " not found."));

        existingUser.setUserName(user.getUserName());
        existingUser.setEmail(user.getEmail());
        existingUser.setGender(user.getGender());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setDepartment(user.getDepartment());

        // only update password if provided (non-empty) — and hash it
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    public void deleteById(Integer userId) { userRepository.deleteById(userId); }
    public void delete(User user) { userRepository.delete(user); }

    public User findByEmailAndPassword(String email, String password) {
        System.out.println("🔍 Login lookup: [" + email + "]");

        if (email == null || password == null) return null;

        Optional<User> userOpt = userRepository.findByEmail(email.trim());
        if (userOpt.isEmpty()) {
            System.out.println("❌ No user for email [" + email + "]");
            return null;
        }

        User user = userOpt.get();
        String stored = user.getPassword();
        System.out.println("   DB password prefix: "
                + (stored != null && stored.length() > 7 ? stored.substring(0, 7) : stored));

        if (stored == null || !passwordEncoder.matches(password, stored)) {
            System.out.println("❌ Password mismatch");
            return null;
        }

        System.out.println("✅ Password OK");
        return user;
    }

    public Optional<User> findByEmail(String email) { return userRepository.findByEmail(email); }
    public List<User> getAllUsers() { return userRepository.findAll(); }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    public String createPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No account found with that email."));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpires(LocalDateTime.now().plusHours(1));
        userRepository.save(user);
        return token;
    }

    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired reset token."));

        if (user.getResetTokenExpires() == null
                || user.getResetTokenExpires().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset token has expired.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpires(null);
        userRepository.save(user);
    }

    /**
     * Encodes the password UNLESS it's already a BCrypt hash.
     * BCrypt hashes always start with $2a$, $2b$, or $2y$.
     */
    private void hashIfNeeded(User user) {
        String pwd = user.getPassword();
        if (pwd == null || pwd.isBlank()) return;

        boolean alreadyHashed = pwd.startsWith("$2a$")
                || pwd.startsWith("$2b$")
                || pwd.startsWith("$2y$");

        if (!alreadyHashed) {
            user.setPassword(passwordEncoder.encode(pwd));
            System.out.println("🔐 Password hashed for " + user.getEmail());
        } else {
            System.out.println("ℹ️ Password already hashed for " + user.getEmail());
        }
    }
}