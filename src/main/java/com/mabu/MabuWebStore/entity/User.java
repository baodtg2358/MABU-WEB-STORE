package com.mabu.MabuWebStore.entity;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "USERS")
public class User {
	
	private static final long OTP_VALID_DURATION = TimeUnit.MINUTES.toMillis(10);
	
	private static final long ROLE_VALID_DURATION = TimeUnit.DAYS.toMillis(60);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USER_ID")
	private int userId;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="FULLNAME")
	private String fullName;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="ACTIVED")
	private boolean actived;
	
	@Column(name="ACTIVED_DATE")
	@DateTimeFormat(iso= ISO.DATE)
	private LocalDate activedDate;
	
	@Column(name="OTP")
	private String OTP;
	
	@Column(name="OTP_EXPIRED_TIME")
	private Date OTPExpiredTime;
	
	@Enumerated(EnumType.STRING)
	@Column(name="PROVIDER")
	private PROVIDER provider;
	
	@OneToOne
	@JoinColumn(name ="ROLE_ID")
	private Roles role;
	
	@OneToOne
	@JoinColumn(name="USER_ID")
	private UserDetails userDetails;
	

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActived() {
		return actived;
	}

	public void setActived(boolean actived) {
		this.actived = actived;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String oTP) {
		OTP = oTP;
	}

	public Date getOTPExpiredTime() {
		return OTPExpiredTime;
	}

	public void setOTPExpiredTime(Date oTPExpiredTime) {
		OTPExpiredTime = oTPExpiredTime;
	}

	public PROVIDER getProvider() {
		return provider;
	}

	public void setProvider(PROVIDER provider) {
		this.provider = provider;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}
	
	public boolean isInExpiredTime() {
		if(this.OTP == null) {
			return false;
		}
		long currentMilisTime = System.currentTimeMillis();
		long expiredTime = this.getOTPExpiredTime().getTime();
		if(expiredTime + OTP_VALID_DURATION < currentMilisTime) {
			return false; // over time
		}
		return true;
		
	}
	
	public boolean dayExpiredRole() {
		long currentMilisTime = System.currentTimeMillis();
		long expiredTime = this.getActivedDate().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli(); // convert Lodate to miliseconds
		if(currentMilisTime - expiredTime > ROLE_VALID_DURATION) {
			return true; 
		}
		return false;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	
	public LocalDate getActivedDate() {
		return activedDate;
	}
	
	public void setActivedDate(LocalDate date) {
		this.activedDate = date;
	}
	
}
