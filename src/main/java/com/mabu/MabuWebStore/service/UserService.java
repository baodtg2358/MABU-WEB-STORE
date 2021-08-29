package com.mabu.MabuWebStore.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import com.mabu.MabuWebStore.dto.UserDTO;
import com.mabu.MabuWebStore.entity.Roles;
import com.mabu.MabuWebStore.entity.User;

public interface UserService {
	
	public List<User> findAll();
	
	public List<Roles> getAllRoles();
	
	public List<User> getAllMember();
	
	public User findByEmail(String email);
	
	public String findByPhone(String phone);
	
	public User findByFullName(String fullName);
	
	public User findUserByEmail(String email);
	
	public void LoginBySocial(String email, String fullName);
	
	public void createNewStaff(UserDTO staff);
	
	public void updateStaff(UserDTO staff, boolean flag);
	
	public void updateMember(UserDTO user);
	
	public boolean updatePasswordM(String email, String  pass);
	
	public void createNewMember(UserDTO user);

	public void generateOTP(User user) throws UnsupportedEncodingException, MessagingException;
	
	public void clearOTP(User user);
	

}
