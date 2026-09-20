package com.egaz.inventory.management.system.service;

import com.egaz.inventory.management.system.model.User;
import com.egaz.inventory.management.system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Integer userId) {
        return userRepository.findById(userId);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Update existing user fields.
     * Assumes user object contains the userId of the user to update.
     */
    public User updateUser(User user) {
        if (user.getUserId() == null) {
            throw new IllegalArgumentException("User ID must not be null for update.");
        }

        Optional<User> existingUserOpt = userRepository.findById(user.getUserId());
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            // Update fields as needed
            existingUser.setUserName(user.getUserName());
            existingUser.setEmail(user.getEmail());
            existingUser.setGender(user.getGender());
            existingUser.setPassword(user.getPassword());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setDepartment(user.getDepartment());

            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User with ID " + user.getUserId() + " not found.");
        }
    }

    public void deleteById(Integer userId) {
        userRepository.deleteById(userId);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User findByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).get();
    }
}