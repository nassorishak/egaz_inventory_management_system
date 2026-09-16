package com.egaz.inventory.management.system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ProductId;
    private  String ProductName;
    private String ProductQuantity;
    private LocalDate ReceiptDate;
    private LocalDate   IssueDate;
    private Integer Price;
    private String SupplierName;
    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;

    public Integer getProductId() {
        return ProductId;
    }

    public void setProductId(Integer productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        ProductQuantity = productQuantity;
    }

    public LocalDate getReceiptDate() {
        return ReceiptDate;
    }

    public void setReceiptDate(LocalDate receiptDate) {
        ReceiptDate = receiptDate;
    }

    public LocalDate getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        IssueDate = issueDate;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        Price = price;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public Object getQuantity() {
        return ProductQuantity;
    }

    public Object getName() {
        return user;
    }

    public void setName(Object name) {
    }

    public void setQuantity(Object quantity) {
    }
}
