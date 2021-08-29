package com.mabu.MabuWebStore.dto;

import java.time.LocalDate;
import java.util.List;



import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDTO {

	private String idOrder;
	private String dateCreated;
	private String deliveryType;
	private String totalAmount;
	private String state;
	
	
}
