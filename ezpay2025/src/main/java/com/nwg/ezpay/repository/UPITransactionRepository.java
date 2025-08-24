package com.nwg.ezpay.repository;

import org.springframework.stereotype.Repository;

import com.nwg.ezpay.entity.UPITransaction;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UPITransactionRepository extends JpaRepository<UPITransaction, Integer> {
	List<UPITransaction> findByStatus(String status);
    Optional<UPITransaction> findById(Integer id);
}




