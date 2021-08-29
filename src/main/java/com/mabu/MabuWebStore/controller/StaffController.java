package com.mabu.MabuWebStore.controller;

import java.io.UnsupportedEncodingException;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import javax.mail.MessagingException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.mabu.MabuWebStore.dto.UserDTO;
import com.mabu.MabuWebStore.entity.Roles;
import com.mabu.MabuWebStore.entity.User;
import com.mabu.MabuWebStore.service.UserService;




@Controller
@RequestMapping("/api/v1/")
public class StaffController {
	
	@Autowired
	private UserService userService;
	

	
	@GetMapping("/staff/search")
	public ResponseEntity<?> updateForm(@RequestParam("email") String email) {
		User updateUser = userService.findUserByEmail(email);
		
		if(updateUser == null) {
			return new ResponseEntity<>("MABU NOT FOUND",HttpStatus.BAD_REQUEST);
		}else {
		
		UserDTO dto = new UserDTO();
		dto.setEmail(updateUser.getEmail());
		dto.setFullName(updateUser.getFullName());
		dto.setBirthday(updateUser.getUserDetails().getBirthday());
		dto.setSex(updateUser.getUserDetails().isSex());
		dto.setAddress(updateUser.getUserDetails().getPrivateAddress());
		dto.setPhoneNumber(updateUser.getUserDetails().getPhoneNumber());
		dto.setRoleID(updateUser.getRole().getRoleID());
		return new ResponseEntity<>(dto,HttpStatus.OK);
		}
	}
	
	@GetMapping("/staff/rspw")
	public ResponseEntity<String> resetPasswordRequest(@RequestParam("email") String email) throws UnsupportedEncodingException, MessagingException {
		User updateUser = userService.findByEmail(email);
		userService.generateOTP(updateUser);
		return new ResponseEntity<>("",HttpStatus.OK);
	}
	
	
	//	for all -- MEMBER AND USER
	@GetMapping("/fa/new-pass")
	public String newPass(@RequestParam("email") String decode, ModelMap model) throws UnsupportedEncodingException {
		String email = new String(Base64.getUrlDecoder().decode(decode));
		System.out.println(email);
		User user = userService.findByEmail(email);
		if(user != null) {
			model.addAttribute("dto", user);
			return "admin/newPassword";
		}else return "redirect:/login?error";
	}
	
	
	@PostMapping("/fa/rspwAcp")
	@ResponseBody
	public ResponseEntity resetPassword(@RequestBody String password) {
		JSONObject obj = new JSONObject(password);
		System.out.println(obj);
		User updateUser = userService.findByEmail(obj.getString("email"));
			if(updateUser != null) {
			updateUser.setPassword(obj.getString("password"));
			userService.clearOTP(updateUser);
			return ResponseEntity.ok("UPDATED");//200
			}else return (ResponseEntity) ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE); //406
	}
	

	
	
	@PostMapping("/staff/update")
	public ResponseEntity<String> updateStaff(@RequestBody String userDTO) {
		UserDTO staff = parseDTOFromJSON(userDTO);
		User existStaff = userService.findByEmail(staff.getEmail());
		if(existStaff.getRole().getRoleID() != staff.getRoleID() &&  !existStaff.dayExpiredRole()) {

			return new ResponseEntity<>("Can't update role before 30days actived",HttpStatus.NOT_ACCEPTABLE);
			
		}else if(existStaff.getRole().getRoleID() != staff.getRoleID() &&  existStaff.dayExpiredRole()){
			userService.updateStaff(staff,true); //BOOLEAN FLAG FOR update actived date
			return new ResponseEntity<>("Update successfully with the Role changed", HttpStatus.OK);

		}else {
			userService.updateStaff(staff,false); //BOOLEAN FLAG FOR none update actived date
			return new  ResponseEntity<>("Update successfully with no Role changed",HttpStatus.OK);
		}
		
	}
	
	@PostMapping("/staff/save")
	public ResponseEntity<String> saveStaff(@RequestBody String userDTO) {
		UserDTO staff = parseDTOFromJSON(userDTO);
		User existUser =  userService.findByEmail(staff.getEmail());
		if(existUser == null) {
			userService.createNewStaff(staff);
			return new ResponseEntity<>("Create Successfully! Please check email", HttpStatus.OK);
			
		}else return new ResponseEntity<>("This Email is already Created!", HttpStatus.NOT_ACCEPTABLE);
	}
	
	
	
	@ModelAttribute(name="Roles")
	public List<Roles> getAllRole(){
		return userService.getAllRoles();
	}
	
	private UserDTO parseDTOFromJSON(String userDTO) {
		JSONObject obj = new JSONObject(userDTO);
		UserDTO staff = new UserDTO();
		staff.setFullName(obj.getString("full_name"));
		staff.setEmail(obj.getString("email"));
		staff.setAddress(obj.getString("address"));
		staff.setPhoneNumber(obj.getString("phone_number"));
		staff.setSex(obj.getString("sex").equals("1")?true:false);
		staff.setBirthday(LocalDate.parse(obj.getString("birthday")));
		staff.setAddressCompany(null);
		staff.setRoleID(Integer.parseInt(obj.getString("roleID")));
		
		return staff;
	}

}
