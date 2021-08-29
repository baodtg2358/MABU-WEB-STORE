package com.mabu.MabuWebStore.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mabu.MabuWebStore.entity.Order;
import com.mabu.MabuWebStore.entity.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
	
	List<Order> findByPhoneNumber(String phoneNumber);
	
	List<Order> findByOrderDate(LocalDate date);
	
	List<Order> findByUser(User user);
	
	@Query(value = "SELECT * FROM ORDERS o WHERE o.delivery_state = 'pending' OR o.delivery_state = 'shipping'",nativeQuery = true)
	List<Order> findAllOrderAdmin();

}
