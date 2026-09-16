package com.egaz.inventory.management.system.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String userName;
    private String email;
    private String gender;
    private String password;
    private String phoneNumber;
    private String role;

    @ManyToOne
    @JoinColumn(name = "DepartmentId")
    private Department department;

    // Explicit getters and setters (Lombok @Data already generates them,
    // but if you want to keep them, they must use 'this' correctly)

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;  // ✅ fixed
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;  // ✅ fixed
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;  // ✅ fixed
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;  // ✅ fixed
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;  // ✅ fixed
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;  // ✅ fixed
    }

    // Optional: remove this confusing method
    public void setName(String userName) {
        this.userName = userName;  // ✅ fixed
    }


}
