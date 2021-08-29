package com.mabu.MabuWebStore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mabu.MabuWebStore.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	public Optional<User> findByEmail(String Email);
	
	
	public Optional<User> findByFullName(String fullName);
	
	@Query(value = "SELECT * FROM USERS u WHERE u.role_id = 3",nativeQuery = true)
	public List<User> findAllMember();
	
	@Query(value = "SELECT * FROM USERS u WHERE u.email = :email AND u.role_id = 1",nativeQuery = true)
	public Optional<User> findUser(@Param("email") String email);
}
