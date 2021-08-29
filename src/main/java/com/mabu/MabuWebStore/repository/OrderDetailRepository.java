package com.mabu.MabuWebStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mabu.MabuWebStore.entity.OrderDetails;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, Integer> {

	
}
