package com.egaz.inventory.management.system.repository;

import com.egaz.inventory.management.system.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByStaff_UserIdOrderByContractIdDesc(Integer staffId);
    List<Contract> findByAdminRequest_IdOrderByContractIdDesc(Long requestId);
    List<Contract> findAllByOrderByContractIdDesc();
}