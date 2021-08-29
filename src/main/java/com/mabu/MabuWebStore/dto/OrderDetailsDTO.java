package com.mabu.MabuWebStore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetailsDTO {
	private String productName;
	private String quantity;
	private String totalAmountProduct;
}
