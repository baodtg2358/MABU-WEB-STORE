package com.mabu.MabuWebStore.service;

import java.time.LocalDate;
import java.util.List;

import com.mabu.MabuWebStore.entity.Order;
import com.mabu.MabuWebStore.entity.User;

public interface OrderService {
	
	public Order findByCode(String code);
	
	public List<Order> findAllByPhone(String phoneNumber);
	
	public List<Order> findAllByDate(LocalDate date);
	
	public List<Order> findAllByUser(User user);
	
	public Order save(Order order);
	
	public List<Order> findAllOrderAdmin();
	
	

}
