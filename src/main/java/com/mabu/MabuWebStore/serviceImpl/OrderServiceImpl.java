package com.mabu.MabuWebStore.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabu.MabuWebStore.entity.Order;
import com.mabu.MabuWebStore.entity.User;
import com.mabu.MabuWebStore.repository.OrderRepository;
import com.mabu.MabuWebStore.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository repo;

	@Override
	public List<Order> findAllByPhone(String phoneNumber) {
		// TODO Auto-generated method stub
		return repo.findByPhoneNumber(phoneNumber);
	}

	@Override
	public List<Order> findAllByDate(LocalDate date) {

		return repo.findByOrderDate(date);
	}

	@Override
	public Order findByCode(String code) {
		// TODO Auto-generated method stub
		return repo.findById(code).isPresent()?repo.findById(code).get():null;
	}

	@Override
	public Order save(Order order) {
		repo.save(order);
		return order;
	}

	@Override
	public List<Order> findAllByUser(User user) {
		
		return repo.findByUser(user).isEmpty()?null:repo.findByUser(user);
	}

	@Override
	public List<Order> findAllOrderAdmin() {
		List<Order> order = repo.findAllOrderAdmin();
		return order;
	}

}
