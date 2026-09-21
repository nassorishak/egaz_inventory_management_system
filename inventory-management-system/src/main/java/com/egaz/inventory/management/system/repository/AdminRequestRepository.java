package com.egaz.inventory.management.system.repository;

import com.egaz.inventory.management.system.model.AdminRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdminRequestRepository extends JpaRepository<AdminRequest, Integer> {

    List<AdminRequest> findByStatus(String status);

    List<AdminRequest> findByAdminId(Integer adminId);

    List<AdminRequest> findByRequestType(String requestType);

    List<AdminRequest> findByStatusOrderByCreatedAtDesc(String status);

    List<AdminRequest> findByAdminIdOrderByCreatedAtDesc(Integer adminId);

}