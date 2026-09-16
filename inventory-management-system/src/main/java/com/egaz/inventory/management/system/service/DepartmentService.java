package com.egaz.inventory.management.system.service;

import com.egaz.inventory.management.system.model.Department;
import com.egaz.inventory.management.system.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Integer id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public void deleteDepartment(Integer id) {
        departmentRepository.deleteById(id);
    }
}