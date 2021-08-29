package com.mabu.MabuWebStore.serviceImpl;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mabu.MabuWebStore.dto.UserDTO;
import com.mabu.MabuWebStore.email.EmailService;
import com.mabu.MabuWebStore.email.EmailServiceImpl;
import com.mabu.MabuWebStore.email.emailContent.EmailContent;
import com.mabu.MabuWebStore.entity.PROVIDER;
import com.mabu.MabuWebStore.entity.Roles;
import com.mabu.MabuWebStore.entity.User;
import com.mabu.MabuWebStore.entity.UserDetails;
import com.mabu.MabuWebStore.entity.Voucher;
import com.mabu.MabuWebStore.repository.RoleRepository;
import com.mabu.MabuWebStore.repository.UserDetailsRepo;
import com.mabu.MabuWebStore.repository.UserRepository;
import com.mabu.MabuWebStore.service.UserService;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private UserDetailsRepo userDetailsRepo;
	
	@Autowired
	private EmailServiceImpl emailService;
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	
	@Override
	public List<User> findAll() {
		return userRepo.findAll();
	}

	@Override
	public User findByEmail(String email) {

		return userRepo.findByEmail(email).isPresent()?userRepo.findByEmail(email).get():null;
	}

	@Override
	public void LoginBySocial(String email, String fullName) {
		User existUser = userRepo.findByEmail(email).isPresent()?userRepo.findByEmail(email).get():null;
		if(existUser == null) {
			User newUser = new User();
			newUser.setEmail(email);
			newUser.setFullName(fullName);
			newUser.setActived(true);
			newUser.setRole(roleRepo.findByRoleName("MEMBER"));
			newUser.setProvider(PROVIDER.SOCIAL);
			newUser.setActivedDate(LocalDate.now());
			userRepo.save(newUser);
			
			int userDetailsId = userRepo.findByEmail(email).get().getUserId();
			
			UserDetails userDetails = new UserDetails();
			userDetails.setUserID(userDetailsId);
			userDetails.setSex(false);
			userDetails.setBirthday(LocalDate.now());
			userDetails.setCompanyAddress("");
			userDetails.setPhoneNumber("");
			userDetails.setPrivateAddress("");
			userDetailsRepo.save(userDetails);
	
		}
		
	
	}

	@Override
	public void generateOTP(User user) throws UnsupportedEncodingException, MessagingException {
		String OTP = RandomString.make(6);
		System.out.println(OTP);
		String hashOTP = passwordEncoder.encode(OTP); // Hash OTP for Spring Security
		user.setPassword(null);
		user.setActived(false);
		user.setOTP(hashOTP);
		user.setOTPExpiredTime(new Date());

		userRepo.save(user);
		
		emailService.SendEmail(EmailContent.OTPSubject, EmailContent.OTPContent(user.getFullName(), OTP) , user.getEmail());
		System.out.println("Mail already send!");
		
		
	}

	@Override
	public void clearOTP(User user) {
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setActivedDate(LocalDate.now());
		user.setOTP(null);
		user.setOTPExpiredTime(null);
		user.setActived(true);
		userRepo.save(user);
	}

	@Override
	public void createNewStaff(UserDTO staff) {
//		 check user
	try {
		
		User newStaff = new User();
		newStaff.setEmail(staff.getEmail());
		newStaff.setFullName(staff.getFullName());
		newStaff.setProvider(PROVIDER.LOCAL);
		newStaff.setRole(roleRepo.findById(staff.getRoleID()).get());
		userRepo.save(newStaff);
		
//		get User ID for UserDetails
		int staffId = userRepo.findByEmail(staff.getEmail()).get().getUserId();

		UserDetails userDetails = new UserDetails();
		userDetails.setUserID(staffId);
		userDetails.setBirthday(staff.getBirthday());
		userDetails.setPrivateAddress(staff.getAddress());
		userDetails.setSex(staff.getSex());
		userDetails.setCompanyAddress("");
		userDetails.setPhoneNumber(staff.getPhoneNumber());
			saveUserDetails(userDetails);
			this.generateOTP(newStaff);
		} catch (UnsupportedEncodingException | MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	public void saveUserDetails(UserDetails user) {
		userDetailsRepo.save(user);
	}

	@Override
	public void createNewMember(UserDTO user) {
		
		User newMember = new User();
		newMember.setEmail(user.getEmail());
		newMember.setFullName(user.getFullName());
		newMember.setPassword(null);
		newMember.setActived(false);
		newMember.setProvider(PROVIDER.LOCAL);
		newMember.setRole(roleRepo.findByRoleName("MEMBER"));
		userRepo.save(newMember);
		
//		get User ID for UserDetails
		int userId = userRepo.findByEmail(user.getEmail()).get().getUserId();
		UserDetails memberDetails = new UserDetails();
		memberDetails.setUserID(userId);
		memberDetails.setPhoneNumber(user.getPhoneNumber());
		memberDetails.setSex(false);
		memberDetails.setCompanyAddress("");
		memberDetails.setPrivateAddress("");
		memberDetails.setBirthday(LocalDate.now());
		userDetailsRepo.save(memberDetails);
		
		try {
			this.generateOTP(newMember);
		} catch (UnsupportedEncodingException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	@Override
	public List<Roles> getAllRoles() {
		// TODO Auto-generated method stub
		return roleRepo.findAll();
	}

	@Override
	public void updateStaff(UserDTO staff,boolean flag) {
	
		
	User newStaff = userRepo.findByEmail(staff.getEmail()).get();
	newStaff.setEmail(staff.getEmail());
	newStaff.setFullName(staff.getFullName());
	if(flag) { // FLAG = TRUE => update Actived_date
	newStaff.setActivedDate(LocalDate.now());
	}
	newStaff.setRole(roleRepo.findById(staff.getRoleID()).get());
	userRepo.save(newStaff);
	
//	get User ID for UserDetails
	int staffId = userRepo.findByEmail(staff.getEmail()).get().getUserId();

	UserDetails userDetails = userDetailsRepo.findById(staffId).get();
	userDetails.setBirthday(staff.getBirthday());
	userDetails.setPrivateAddress(staff.getAddress());
	userDetails.setSex(staff.getSex());
	userDetails.setPhoneNumber(staff.getPhoneNumber());
		saveUserDetails(userDetails);

	}

	@Override
	public void updateMember(UserDTO user) {
		User newMember = userRepo.findByEmail(user.getEmail()).get();
		newMember.setFullName(user.getFullName());
		userRepo.save(newMember);
		
//		get User ID for UserDetails
		int userId = userRepo.findByEmail(user.getEmail()).get().getUserId();
		
		UserDetails memberDetails = userDetailsRepo.findById(userId).get();
		memberDetails.setPhoneNumber(user.getPhoneNumber());
		memberDetails.setBirthday(user.getBirthday());
		memberDetails.setPrivateAddress(user.getAddress());
		memberDetails.setCompanyAddress(user.getAddressCompany());
		memberDetails.setSex(user.getSex());
		userDetailsRepo.save(memberDetails);
		
	}


	@Override
	public User findByFullName(String fullName) {
		
		return userRepo.findByFullName(fullName).isPresent()?userRepo.findByFullName(fullName).get():null;
	}

	@Override
	public boolean updatePasswordM(String email, String pass) {
		User existUser = userRepo.findByEmail(email).isPresent()?userRepo.findByEmail(email).get():null;
		if(existUser != null) {
		existUser.setPassword(passwordEncoder.encode(pass));
		userRepo.save(existUser);
		return true;
		}else return false;
		
		
	}

	@Override
	public List<User> getAllMember() {
		
		
		return userRepo.findAllMember();
	}

	@Override
	public String findByPhone(String phone) {
		UserDetails userDetails = userDetailsRepo.findByPhoneNumber(phone).isPresent()?userDetailsRepo.findByPhoneNumber(phone).get():null;
		if(userDetails == null) return null;
		else { 
			User user = userRepo.getById(userDetails.getUserID());
			return user.getFullName();
		}
		
	}

	@Override
	public User findUserByEmail(String email) {
		
		return userRepo.findUser(email).isPresent()?userRepo.findUser(email).get():null;
	}
	
	
	
	

}
