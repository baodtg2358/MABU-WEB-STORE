package com.mabu.MabuWebStore.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name= "ORDERS")
public class Order {
	
	@Id
	@Column(name="order_code")  //This code will be generate by the create and random number
	private String orderCode;
	
	@Column(name="order_date")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDate orderDate;
	
	@Column(name = "customer_name")
	private String cutomerName;
	
	@Column(name="address")
	private String address;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	@Column(name="note")
	private String note;
	
	@Column(name="delivery_type")
	private boolean deliveryType;
	
	@Column(name="delivery_state")
	private String deliveryState;
	
	
	@OneToOne
	@JoinColumn(name="voucher_id")
	private Voucher voucher;
	
	@OneToOne
	@EqualsAndHashCode.Exclude 
    @ToString.Exclude 
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name="total_amount")
	private float totalAmount;
	
	@Column(name="payment")
	private boolean payment;
	
	@OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
	private List<OrderDetails> orderDetails;
	
}
