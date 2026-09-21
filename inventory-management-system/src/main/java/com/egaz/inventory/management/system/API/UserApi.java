package com.egaz.inventory.management.system.API;

import com.egaz.inventory.management.system.model.Department;
import com.egaz.inventory.management.system.model.User;
import com.egaz.inventory.management.system.repository.DepartmentRepository;
import com.egaz.inventory.management.system.repository.UserRepository;
import com.egaz.inventory.management.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private UserRepository userRepository;

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
        if (!departmentOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Department not found");
        }

        User newUser = new User();
        newUser.setName(userName);
        newUser.setDepartment(departmentOpt.get());

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
            // Validate required fields
            if (user == null || user.getEmail() == null || user.getEmail().trim().isEmpty()
                    || user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Email and password are required.");
            }

            // Check if email already exists
            User existingUser = userService.findByEmail(user.getEmail());
            if (existingUser != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("An account with this email already exists.");
            }

            // Save the new user to database
            User savedUser = userService.save(user);

            System.out.println("User registered successfully: " + savedUser.getEmail());

            return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error: " + e.getMessage());
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
//        String email = loginRequest.get("email");
//        String password = loginRequest.get("password");
//
//        User user = userService.findByEmailAndPassword(email, password);
//
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
//        }
//
//        return ResponseEntity.ok(user);


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            // Find user by email and password
            User foundUser = userRepository.findByEmailAndPassword(
                    user.getEmail(),
                    user.getPassword()
            );

            if (foundUser == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid email or password");
            }

            // ✅ Return user data including userId
            return ResponseEntity.ok(foundUser);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Login error: " + e.getMessage());
        }
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

    // Get all users
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // ✅ NEW: Get all users — accessible at /api/users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsersRoot() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);

}
}
