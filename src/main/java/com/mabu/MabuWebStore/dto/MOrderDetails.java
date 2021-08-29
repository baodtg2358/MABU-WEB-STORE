package com.mabu.MabuWebStore.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MOrderDetails {
	private String fullName;
	private String phoneNumber;
	private String address;
	private String notes;
	private boolean payment;
	private String deliveryType;
	private String totalAmount;
	private String voucher;
	private List<OrderDetailsDTO> details;
	

}
