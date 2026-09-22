package com.egaz.inventory.management.system.API;

import com.egaz.inventory.management.system.model.Department;
import com.egaz.inventory.management.system.model.User;
import com.egaz.inventory.management.system.repository.DepartmentRepository;
import com.egaz.inventory.management.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
@RestController
public class UserApi {

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> payload) {
        String userName = payload.get("name");
        String departmentIdStr = payload.get("departmentId");

        if (userName == null || userName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("User name is required");
        }
        if (departmentIdStr == null || departmentIdStr.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Department ID is required");
        }

        Long departmentId;
        try {
            departmentId = Long.parseLong(departmentIdStr);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid Department ID");
        }

        Optional<Department> departmentOpt = departmentRepository.findById(Math.toIntExact(departmentId));
        if (departmentOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Department not found");
        }

        User newUser = new User();
        newUser.setName(userName);
        newUser.setDepartment(departmentOpt.get().getDepartmentId());

        try {
            userService.save(newUser);
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            if (user == null
                    || user.getEmail() == null || user.getEmail().trim().isEmpty()
                    || user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Email and password are required.");
            }

            if (userService.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("An account with this email already exists.");
            }

            User savedUser = userService.save(user);
            System.out.println("User registered successfully: " + savedUser.getEmail());

            return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        System.out.println("=== LOGIN ATTEMPT ===");
        System.out.println("Email: " + request.getEmail());

        User user = userService.findByEmailAndPassword(request.getEmail(), request.getPassword());

        if (user == null) {
            System.out.println("❌ Login failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }

        System.out.println("✅ Login OK for: " + user.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getUserId());
        response.put("userName", user.getUserName());
        response.put("email", user.getEmail());
        response.put("role", user.getRole());
        response.put("departmentId", user.getDepartment());
        response.put("gender", user.getGender());
        response.put("phoneNumber", user.getPhoneNumber());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User user) {
        try {
            user.setUserId(id);
            User updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user");
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsersRoot() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email is required.");
            }

            String token = userService.createPasswordResetToken(email.trim());
            String resetLink = "http://localhost:3000/reset-password?token=" + token;

            System.out.println("🔐 Reset link for " + email + ": " + resetLink);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "If the email exists, a reset link has been sent.");
            response.put("token", token);
            response.put("resetLink", resetLink);
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "If the email exists, a reset link has been sent.");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        try {
            String token = body.get("token");
            String newPassword = body.get("newPassword");

            if (token == null || token.isEmpty()) {
                return ResponseEntity.badRequest().body("Token is required.");
            }
            if (newPassword == null || newPassword.length() < 6) {
                return ResponseEntity.badRequest().body("Password must be at least 6 characters.");
            }

            userService.resetPassword(token, newPassword);
            return ResponseEntity.ok("Password has been reset successfully.");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}