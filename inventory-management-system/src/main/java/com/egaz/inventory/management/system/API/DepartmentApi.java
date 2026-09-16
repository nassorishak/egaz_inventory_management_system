package com.egaz.inventory.management.system.API;
import com.egaz.inventory.management.system.model.Department;
import com.egaz.inventory.management.system.repository.DepartmentRepository;
import com.egaz.inventory.management.system.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/departments")
public class DepartmentApi {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createDepartment(@RequestBody Map<String, String> payload) {
        try {
            String departmentName = payload.get("department");

            System.out.println("Received payload: " + payload);
            System.out.println("Department name: " + departmentName);

            if (departmentName == null || departmentName.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Department name is required");
            }

            Department newDept = new Department();
            newDept.setDepartmentName(departmentName.trim());

            System.out.println("Before save - Department object: " + newDept);
            System.out.println("Department name value: " + newDept.getDepartmentName());

            Department savedDept = departmentRepository.save(newDept);

            System.out.println("After save - Department ID: " + savedDept.getDepartmentId());
            System.out.println("After save - Department name: " + savedDept.getDepartmentName());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Department created successfully");

        } catch (Exception e) {
            System.out.println("Exception occurred: ");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating department: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Integer id) {
        Department department = departmentService.getDepartmentById(id);
        if (department != null) {
            return new ResponseEntity<>(department, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Integer id) {
        try {
            departmentService.deleteDepartment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
