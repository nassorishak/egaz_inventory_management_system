
package com.egaz.inventory.management.system.API;

import com.egaz.inventory.management.system.model.Product;
import com.egaz.inventory.management.system.model.User;
import com.egaz.inventory.management.system.service.ProductService;
import com.egaz.inventory.management.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/products")
public class ProductApi {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    // =====================================================
    // CREATE PRODUCT WITH USER
    // =====================================================
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(
            @RequestBody Product product,
            @RequestParam Integer userId) {

        try {

            System.out.println("========== CREATE PRODUCT ==========");
            System.out.println("User ID: " + userId);
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("====================================");

            if (product == null) {
                return ResponseEntity
                        .badRequest()
                        .body("Product data cannot be empty");
            }

            if (product.getProductName() == null ||
                    product.getProductName().trim().isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body("Product name is required");
            }

            if (product.getProductQuantity() == null ||
                    product.getProductQuantity().trim().isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body("Product quantity is required");
            }

            if (product.getPrice() == null) {
                return ResponseEntity
                        .badRequest()
                        .body("Product price is required");
            }

            if (product.getReceiptDate() == null) {
                return ResponseEntity
                        .badRequest()
                        .body("Receipt date is required");
            }

            if (product.getIssueDate() == null) {
                return ResponseEntity
                        .badRequest()
                        .body("Issue date is required");
            }

            if (product.getSupplierName() == null ||
                    product.getSupplierName().trim().isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body("Supplier name is required");
            }

            // Get the user from database
            User user = userService.findById(userId).orElse(null);

            if (user == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("User not found with ID: " + userId);
            }

            // Set the user on the product
            product.setUser(user);

            // ✅ FIXED: pass Product, not Optional<Product>
            Product savedProduct = productService.save(product);

            System.out.println("Product saved with ID: " + savedProduct.getProductId());
            System.out.println("Product user_id: " + savedProduct.getUser().getUserId());

            return ResponseEntity.ok(savedProduct);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating product: " + e.getMessage());
        }
    }

    // =====================================================
    // GET PRODUCT BY ID
    // =====================================================
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {

        try {

            Optional<Product> productOpt = productService.findById(id);

            if (productOpt.isPresent()) {
                return ResponseEntity.ok(productOpt.get());
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Product not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving product: " + e.getMessage());
        }
    }

    // =====================================================
    // UPDATE PRODUCT
    // =====================================================
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Integer id,
            @RequestBody Product product) {

        try {

            System.out.println("========== UPDATE PRODUCT ==========");
            System.out.println("Product ID: " + id);
            System.out.println("New Name: " + product.getProductName());
            System.out.println("====================================");

            Optional<Product> existingProductOpt = productService.findById(id);

            if (!existingProductOpt.isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Product not found");
            }

            if (product == null) {
                return ResponseEntity
                        .badRequest()
                        .body("Product data cannot be empty");
            }

            if (product.getProductName() == null ||
                    product.getProductName().trim().isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body("Product name is required");
            }

            if (product.getProductQuantity() == null ||
                    product.getProductQuantity().trim().isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body("Product quantity is required");
            }

            if (product.getPrice() == null) {
                return ResponseEntity
                        .badRequest()
                        .body("Product price is required");
            }

            if (product.getReceiptDate() == null) {
                return ResponseEntity
                        .badRequest()
                        .body("Receipt date is required");
            }

            if (product.getProductDescription() == null) {
                return ResponseEntity
                        .badRequest()
                        .body("product description is required");
            }

            if (product.getIssueDate() == null) {
                return ResponseEntity
                        .badRequest()
                        .body("Issue date is required");
            }

            if (product.getSupplierName() == null ||
                    product.getSupplierName().trim().isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body("Supplier name is required");
            }

            Product existingProduct = existingProductOpt.get();

            // Update only the actual Product fields
            existingProduct.setProductName(product.getProductName().trim());
            existingProduct.setProductQuantity(product.getProductQuantity().trim());
            existingProduct.setProductDescription(product.getProductDescription().trim());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setReceiptDate(product.getReceiptDate());
            existingProduct.setIssueDate(product.getIssueDate());
            existingProduct.setSupplierName(product.getSupplierName().trim());

            // Keep existing user relationship
            if (product.getUser() != null) {
                existingProduct.setUser(product.getUser());
            }

            // ✅ FIXED: pass Product, not Optional<Product>
            Product updatedProduct = productService.save(existingProduct);

            System.out.println("Product updated successfully");

            return ResponseEntity.ok(updatedProduct);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating product: " + e.getMessage());
        }
    }

    // =====================================================
    // GET ALL PRODUCTS
    // =====================================================
    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {

        try {
            List<Product> products = productService.findAll();
            return ResponseEntity.ok(products);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving products: " + e.getMessage());
        }
    }

    // =====================================================
    // DELETE PRODUCT (purges product_request children first)
    // =====================================================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        try {
            System.out.println("========== DELETE PRODUCT ==========");
            System.out.println("Product ID: " + id);

            if (productService.findById(id).isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Product not found with ID: " + id);
            }

            productService.deleteById(id);
            return ResponseEntity.ok("Product deleted successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Delete failed: " + e.getMessage());
        }
    }


}
