package com.mabu.MabuWebStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mabu.MabuWebStore.dto.UserDTO;

@Controller
public class MainController {
	
//	ANYONE CAN GET
	@GetMapping({"/","/home"})
	public String  homePage() {
		return "user/index";
	}
	
	@GetMapping("/login")
	public String login(ModelMap model) {
		model.addAttribute("regisDTO", new UserDTO());
		return "admin/login";
	}
	
	@GetMapping("/AboutUs")
	public String aboutUs() {
		return "user/AboutUs";
	}
	
	//	PERMIT ALL
	
	@GetMapping("/cart")
	public String cart() {
		return "user/cart";
	}
	
	
//	ADMIN PAGE REQUEST
	
//	Any Request need Authentication
		
	@GetMapping("/staffDashboard")
	public String staffHome() {
		return "admin/adminDashboard";
	}
	
	@GetMapping({"/product"})
	public String product() {
		return "user/product";
	}
	
	
//	MEMBER PAGE REQUEST
	
// AUTHORIZED (MEMBER)
	
	@GetMapping({"/mDashboard"})
	public String memberDashboard() {
		return "user/MemberDashboard";
	}
	


	

}
