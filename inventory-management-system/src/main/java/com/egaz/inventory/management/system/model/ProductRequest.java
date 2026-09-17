//package com.egaz.inventory.management.system.model;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDate;
//
//@Entity
//@Data
//@Table(name = "product_request")
//public class ProductRequest {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer requestId;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "product_id", nullable = false)
//    private Product product;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "department_id", nullable = false)
//    private Department department;
//
//    @Column(nullable = false)
//    private Integer quantity;
//
//    private LocalDate issueDate;
//
//    @Column(nullable = false)
//    private String status = "PENDING";
//
//    private LocalDate requestDate;
//
//    private String adminNote;
//
//    // ================= FLAT FIELDS FOR FRONTEND =================
//
//    @JsonProperty("productName")
//    public String getProductName() {
//        return product != null ? product.getProductName() : null;
//    }
//
//    @JsonProperty("productDescription")
//    public String getProductDescription() {
//        return product != null ? product.getProductDescription() : null;
//    }
//
//    @JsonProperty("departmentName")
//    public String getDepartmentName() {
//        return department != null ? department.getDepartmentName() : null;
//    }
//}

package com.egaz.inventory.management.system.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "product_request")
public class ProductRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer requestId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(nullable = false)
    private Integer quantity;

    private LocalDate issueDate;

    // ✅ Description typed by the staff who requested
    @Column(name = "description", length = 1000)
    private String description;

    @Column(nullable = false)
    private String status = "PENDING";

    private LocalDate requestDate;

    private String adminNote;

    // ================= FLAT FIELDS FOR THE FRONTEND =================

    @JsonProperty("productName")
    public String getProductName() {
        return product != null ? product.getProductName() : null;
    }

    @JsonProperty("departmentName")
    public String getDepartmentName() {
        return department != null ? department.getDepartmentName() : null;
    }

    // ✅ Return the description the staff typed, not the product's
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }
}