package com.mabu.MabuWebStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mabu.MabuWebStore.entity.Roles;

public interface RoleRepository extends JpaRepository<Roles, Integer> {

	public Roles findByRoleName(String roleName);
	
}
