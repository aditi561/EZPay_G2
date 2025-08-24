package com.nwg.ezpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nwg.ezpay.entity.UPIAccount;
import java.util.Optional;

@Repository
public interface UPIAccountRepository extends JpaRepository<UPIAccount, Long> {
	Optional<UPIAccount> findByUpiId(String upiId);
}
