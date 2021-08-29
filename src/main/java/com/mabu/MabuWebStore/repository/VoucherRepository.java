package com.mabu.MabuWebStore.repository;


import com.mabu.MabuWebStore.entity.Voucher;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
	
	Optional<Voucher> findByVoucherCode(String code);
   
}

