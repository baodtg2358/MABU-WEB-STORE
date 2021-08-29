package com.mabu.MabuWebStore.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="USER_DETAILS")
public class UserDetails {
	
	@Id
	@Column(name= "USER_ID")
	private int userID;
	
	@Column(name="BIRDTHDAY")
	private LocalDate birthday;
	
	@Column(name="SEX")
	private boolean sex;
	
	@Column(name="PRIVATE_ADDRESS")
	private String privateAddress;
	
	@Column(name="COMPANY_ADDRESS")
	private String companyAddress;
	
	@Column(name="PHONE_NUMBER")
	private String phoneNumber;
	
	

}
