//package com.egaz.inventory.management.system.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDate;
//
//@Entity
//@Data
//@Table(name = "product")
//public class Product {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer productId;
//
//    private String productName;
//    private String productQuantity;
//    private LocalDate receiptDate;
//    private LocalDate issueDate;
//    private Integer price;
//    private String supplierName;
//
//    @Column(name = "product_description", length = 1000)
//    private String productDescription;
//
//    // ✅ NEW — simple FK column
//    @Column(name = "department_id")
//    private Integer departmentId;
//
//
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    // getters + setters (keep your existing ones, add these)
//    public Integer getDepartmentId() { return departmentId; }
//    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }
//
//    public void setContractPurpose(String purpose) {
//    }
//
//    public void setContractSignature(String signature) {
//    }
//
//    public void setContractEndDate(LocalDate endDate) {
//    }
//
//    public void setContractStartDate(LocalDate startDate) {
//    }
//
//    public void setContractStatus(String submitted) {
//    }
//}

package com.egaz.inventory.management.system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private String productName;
    private String productQuantity;
    private LocalDate receiptDate;
    private LocalDate issueDate;
    private Integer price;
    private String supplierName;

    @Column(name = "product_description", length = 1000)
    private String productDescription;

    // ✅ FK column
    @Column(name = "department_id")
    private Integer departmentId;

    // ✅ NEW — Receipt Voucher No
    @Column(name = "receipt_voucher_no", length = 100)
    private String receiptVoucherNo;

    // ✅ NEW — Balance
    @Column(name = "balance")
    private Integer balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    // getters + setters
    public Integer getDepartmentId() { return departmentId; }
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }

    public String getReceiptVoucherNo() { return receiptVoucherNo; }
    public void setReceiptVoucherNo(String receiptVoucherNo) { this.receiptVoucherNo = receiptVoucherNo; }

    public Integer getBalance() { return balance; }
    public void setBalance(Integer balance) { this.balance = balance; }

    public void setContractPurpose(String purpose) {
    }

    public void setContractSignature(String signature) {
    }

    public void setContractEndDate(LocalDate endDate) {
    }

    public void setContractStartDate(LocalDate startDate) {
    }

    public void setContractStatus(String submitted) {
    }
}