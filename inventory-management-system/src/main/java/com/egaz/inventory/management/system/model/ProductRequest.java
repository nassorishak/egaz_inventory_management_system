

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

    @ManyToOne
    @JoinColumn(name = "product_id")
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

    @Column(name = "admin_note", length = 500)
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
}