package com.mabu.MabuWebStore.entity;

import lombok.*;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name="VOUCHER")
public class Voucher {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="voucher_id")
	private int voucher_id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="created_date")
	private LocalDate createdDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="start_date")
	private LocalDate startDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="end_date")
	private LocalDate endDate;
	
	/*
	 * Voucher code create must contain content of event.
	 * Example: Women International Day is 8/3 => voucher code is : WMD83 or WOMEN83 or something like that.
	 * */
	
	@Column(name="voucher_code")
	private String voucherCode;
	
	@Column(name="content")
	private String content;
	
	@Column(name="actived") // 0 is disable 1 is active
	private boolean actived;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="discount")
	private int discount;


	

}
