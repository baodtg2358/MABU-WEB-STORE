package com.mabu.MabuWebStore.controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonSerializer;
import com.mabu.MabuWebStore.dto.OrderDTO;
import com.mabu.MabuWebStore.dto.UserDTO;
import com.mabu.MabuWebStore.email.EmailService;
import com.mabu.MabuWebStore.entity.Order;
import com.mabu.MabuWebStore.entity.User;
import com.mabu.MabuWebStore.service.OrderService;
import com.mabu.MabuWebStore.service.UserService;



@Controller
@RequestMapping("/api/v2")
public class MemberController {
	
	private static String randomCodeStatic = null;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private  EmailService emailService;
	
	public BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	
	
	
	
	
	@GetMapping("/cbp/{phoneNumber}")
	public ResponseEntity<?> checkMemberByPhone(@PathVariable("phoneNumber") String phone){
		String user = userService.findByPhone(phone);
		if(user == null) return new ResponseEntity<>("MABU NOT FOUND",HttpStatus.BAD_REQUEST);
		else return ResponseEntity.ok(user);
		
	}
	
//	api/v2/cbe/{email}
	@GetMapping("/cbe/{email}")
	public ResponseEntity<?> checkMemberByEmail(@PathVariable("email") String email){
		User user = userService.findByEmail(email);
		if(user == null) return new ResponseEntity<>("MABU NOT FOUND",HttpStatus.BAD_REQUEST);
		else return ResponseEntity.ok(user.getFullName());
	}

	
	
//	RESET PASS (MEMBER ROLE)
	
	@GetMapping("/member/rspw/{email}") // on member Dashboard
	public ResponseEntity<?> resetPasswordRequest(@PathVariable("email") String email) throws UnsupportedEncodingException, MessagingException{
			String randomCode = randomCode(); // RANDOM 6DIGIT Number
			this.randomCodeStatic = randomCode;
			
			
			User updateUser = userService.findByEmail(email);
			if(updateUser == null) {
				return new  ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
			}else {
			String CodeContent = "<p>Hello <b>" + updateUser.getFullName() + "</b></p><br>"  //CONTENT EMAIl
				+ "<p> Welcome to <b>MABU STORES</b></p>"
	            + "<p>THIS CODE USE FOR RESET PASSWORD"
				+ "<br>"
	            + "<p><b>" + randomCode + "</b></p>"
	            + "<br>"
	            + "Please includes this code in reset password form"
	            + "<p><b>Note: this OTP is set to expire in 10 minutes.</b></p>";
			emailService.SendEmail("CODE FOR RESET PASSWORD", CodeContent, updateUser.getEmail()); // email
			return new ResponseEntity<>("Email already send", HttpStatus.OK);
			}
	}
	

	
	@PostMapping("/member/rspw")
	public ResponseEntity<?> updatePassword(@RequestBody String updatePassJson){
		System.out.println(updatePassJson);
		JSONObject obj = new JSONObject(updatePassJson);
		String randomCodeRequest =String.valueOf(obj.get("code"));
		if(!randomCodeRequest.equals(randomCodeStatic)) return new ResponseEntity<>("Code not work",HttpStatus.BAD_REQUEST);
		else {
			
		boolean uPass =	userService.updatePasswordM(String.valueOf(obj.get("email")), String.valueOf(obj.get("password")));
			if(uPass) return ResponseEntity.ok("");
			else return new ResponseEntity<>("",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/fa/rspw") // on Login page
	public ResponseEntity<?> resetPasswordOnLogin(@RequestParam("email") String email) throws UnsupportedEncodingException, MessagingException {
		User updateUser = userService.findByEmail(email);
		if(updateUser == null) {
			return new ResponseEntity<>("Email not exist",HttpStatus.BAD_REQUEST);
		}else {
		userService.generateOTP(updateUser);
		return new ResponseEntity<>("Email already send!",HttpStatus.OK);
		}
	}
	
	
//	ACCOUNT DETAIL
	
	@GetMapping("/member/getInfo")
	public ResponseEntity<?> getInfo(@RequestParam("email") String email){
		User user = userService.findByEmail(email);
		UserDTO dto = new UserDTO();
		dto.setEmail(user.getEmail());
		dto.setFullName(user.getFullName());
		dto.setPhoneNumber(user.getUserDetails().getPhoneNumber());
		dto.setBirthday(user.getUserDetails().getBirthday());
		dto.setSex(user.getUserDetails().isSex());
		dto.setAddress(user.getUserDetails().getPrivateAddress());
		dto.setAddressCompany(user.getUserDetails().getCompanyAddress());
		
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
	
	
//	UPDATE FROM MEMBER DASHBOARD
	
	@PostMapping("/member/update")
	public ResponseEntity saveUpdate(@RequestBody String memberJSON) {
		System.out.println(memberJSON);
			UserDTO dto = parseDTOFromJSON(memberJSON);
			userService.updateMember(dto);
		
		
		return new ResponseEntity<String>(dto.getFullName(),HttpStatus.OK);
	}
	
//	REGIS ON LOGIN PAGE
	
	@PostMapping("/fa/regis")
	public ResponseEntity regis(@RequestBody String jsonNewMember) {
			System.out.println(jsonNewMember);
			JSONObject obj = new JSONObject(jsonNewMember);
			UserDTO dto = new UserDTO();
			dto.setEmail(obj.getString("email"));
			dto.setFullName(obj.getString("fullName"));
			dto.setPhoneNumber(obj.getString("phoneNumber").equals("")?"":obj.getString("phoneNumber"));
			userService.createNewMember(dto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
//	ORDER 
	
	
	@GetMapping("/member/p/filter_order")
	public ResponseEntity<?> checkOrderByPhone(@RequestParam("phone_number") String phoneNumber) {
		return new ResponseEntity<>(orderService.findAllByPhone(phoneNumber),HttpStatus.OK);
	}
	
	@GetMapping("/member/p/getAllOrder/{email}")
	public ResponseEntity<?> getAllOrder(@PathVariable("email") String email){
		User user = userService.findByEmail(email);
		List<Order> allOrder = orderService.findAllByUser(user);
		if(allOrder == null) {
			return new ResponseEntity<>("",HttpStatus.OK);
		}else {
			List<OrderDTO> dto = new ArrayList<OrderDTO>();
		for(Order order : allOrder) {
			OrderDTO dtoObj = new OrderDTO();
			dtoObj.setIdOrder(order.getOrderCode());
			dtoObj.setDateCreated(order.getOrderDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			dtoObj.setTotalAmount(String.valueOf(order.getTotalAmount()));
			dtoObj.setDeliveryType(order.isDeliveryType()?"MABUSHIP":"VIETTEL POST");
			dtoObj.setState(order.getDeliveryState());
			dto.add(dtoObj);
		}
		
		String json = new Gson().toJson(dto);
		
		JSONArray array = new JSONArray(json);

		return new ResponseEntity<>(String.valueOf(array),HttpStatus.OK);
		}
	}
	
	
	
	
	
//	private code custom
	private UserDTO parseDTOFromJSON(String userDTO) {
		JSONObject obj = new JSONObject(userDTO);
		UserDTO member = new UserDTO();
		member.setEmail(obj.getString("email"));
		member.setFullName(obj.getString("full_name"));
		member.setAddress(obj.getString("address_private"));
		member.setPhoneNumber(obj.getString("phone_number"));
		member.setSex(obj.getString("sex").equals("1")?true:false);
		member.setBirthday(LocalDate.parse(obj.getString("birthday")));
		member.setAddressCompany(obj.getString("address_company"));

		
		return member;
	}
	
	private String randomCode() {
		int min = 111111;
		int max = 999999;
		String randomString = String.valueOf((int)Math.floor(Math.random()*(max-min+1)+min));
		return randomString;
	}
	
}
