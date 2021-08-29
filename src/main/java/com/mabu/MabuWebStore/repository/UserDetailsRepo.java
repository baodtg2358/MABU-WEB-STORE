package com.mabu.MabuWebStore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mabu.MabuWebStore.entity.UserDetails;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetails, Integer> {
	
	Optional<UserDetails> findByPhoneNumber(String phoneNumber);

}
