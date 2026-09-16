package com.egaz.inventory.management.system.repository;

import com.egaz.inventory.management.system.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department ,Integer>{
}
