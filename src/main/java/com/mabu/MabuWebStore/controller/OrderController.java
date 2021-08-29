package com.mabu.MabuWebStore.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.mabu.MabuWebStore.dto.MOrderDetails;
import com.mabu.MabuWebStore.dto.OrderDTO;
import com.mabu.MabuWebStore.dto.OrderDetailsDTO;
import com.mabu.MabuWebStore.entity.Order;
import com.mabu.MabuWebStore.entity.OrderDetails;
import com.mabu.MabuWebStore.entity.Product;
import com.mabu.MabuWebStore.entity.User;
import com.mabu.MabuWebStore.entity.Voucher;
import com.mabu.MabuWebStore.service.IProductService;
import com.mabu.MabuWebStore.service.OrderDetailsService;
import com.mabu.MabuWebStore.service.OrderService;
import com.mabu.MabuWebStore.service.UserService;
import com.mabu.MabuWebStore.service.VoucherService;

import net.bytebuddy.utility.RandomString;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
public class OrderController {
    
	@Autowired
    private OrderService orderService;
	
	@Autowired
	private OrderDetailsService orderDetailService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VoucherService voucherService;
	
	@Autowired
	private IProductService productService;
	

    
    @GetMapping("/order/by_code")
    public Order getOrderByCode(@RequestParam("code") String code) {
    	return orderService.findByCode(code);
    }
    
    @GetMapping("/order/by_phone")
    public List<Order> getAllByPhone(@RequestParam("phone") String phoneNumber){
        List<Order> allBills = orderService.findAllByPhone(phoneNumber);
        return allBills;
    }

    @GetMapping("/order/by_date")
    public List<Order> getBillById(@RequestParam("date_order") LocalDate date){
        return  orderService.findAllByDate(date);
    }

    @GetMapping("/order/a/getAllOrder")
    public ResponseEntity<?> getAllOrderAdmin(){
    	List<Order> orderz = orderService.findAllOrderAdmin();
    	if(orderz == null) {
			return new ResponseEntity<>("",HttpStatus.OK);
		}else {
			List<OrderDTO> dto = new ArrayList<OrderDTO>();
		for(Order order : orderz) {
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
    
    @GetMapping("/order/a/getDetails")
    public ResponseEntity<?> getDetailsOrderForAdmin(@RequestParam("_id") String param){
    	Order order = orderService.findByCode(param);
    	MOrderDetails mOrderDetails = new MOrderDetails();
    	mOrderDetails.setFullName(order.getCutomerName());
    	mOrderDetails.setPhoneNumber(order.getPhoneNumber());
    	mOrderDetails.setAddress(order.getAddress());
    	mOrderDetails.setNotes(order.getNote());
    	mOrderDetails.setPayment(order.isPayment());
    	mOrderDetails.setDeliveryType(order.isDeliveryType()?"MABUSHIP":"VIETTEL POST");
    	mOrderDetails.setTotalAmount(String.valueOf(order.getTotalAmount()));
    	mOrderDetails.setVoucher(order.getVoucher()==null?"":order.getVoucher().getVoucherCode());
    	
    	
    	List<OrderDetails> details = order.getOrderDetails();
    	List<OrderDetailsDTO> dto = new ArrayList<OrderDetailsDTO>();
    	for(OrderDetails oDetail : details) {
    		OrderDetailsDTO oDTO = new OrderDetailsDTO();
    		oDTO.setProductName(oDetail.getProduct().getName());
    		oDTO.setQuantity(String.valueOf(oDetail.getQuantity()));
    		oDTO.setTotalAmountProduct(String.valueOf(oDetail.getAmountProduct()));
    		dto.add(oDTO);
    	}
    	mOrderDetails.setDetails(dto);
    	return ResponseEntity.ok(mOrderDetails);
    }
    
    
    
//    create order with normal checkout
    @PostMapping("/order/checkout")
    public ResponseEntity normalCheckout(@RequestBody String orderRQ) {
    	System.out.print(orderRQ);
    	JSONObject orderObj = new JSONObject(orderRQ);
    	Order order = new Order();
    	order.setOrderDate(LocalDate.now());
    	order.setCutomerName(orderObj.getString("name"));
    	order.setAddress(orderObj.getString("address"));
    	order.setPhoneNumber(orderObj.getString("phone"));
    	order.setTotalAmount(Float.valueOf(orderObj.getString("total")));
    	order.setNote(orderObj.getString("note"));
    	order.setDeliveryType(orderObj.getString("total").equals("0"));
    	order.setDeliveryState("pending");
    	
    	User user = userService.findByEmail(orderObj.getString("email"));
    	System.out.print(user.getUserId());
    	order.setUser(user);
    	order.setOrderCode(randomCode(order.getUser()));
    	Voucher voucher = null;
    	if(orderObj.getString("voucher").equals("")) {
    		order.setVoucher(voucher);
    	}else {
    		voucher = voucherService.findByVoucherCode(orderObj.getString("voucher"));
    		order.setVoucher(voucher);
    	}
    	
    	JSONArray array = orderObj.getJSONArray("items"); 

    	List<OrderDetails> orderDetailList = convertODFromJson(order, array);

    	order.setOrderDetails(orderDetailList);
    	orderService.save(order);
    	return ResponseEntity.ok("");
    }
    
//    @PostMapping("/order/checkout/p2")
//    @ResponseBody
//    public String p2Checkout(@ModelAttribute("checkoutDescription") String order) {
//    	System.out.println(order);
//    	
//    	return order;
//    }
    
    
//    For MDashboard
    @GetMapping("/p/getDetails")
    public ResponseEntity<?> getDetails(@RequestParam("_id") String param){
    	Order order = orderService.findByCode(param);
    	MOrderDetails mOrderDetails = new MOrderDetails();
    	mOrderDetails.setFullName(order.getCutomerName());
    	mOrderDetails.setPhoneNumber(order.getPhoneNumber());
    	mOrderDetails.setAddress(order.getAddress());
    	mOrderDetails.setNotes(order.getNote());
    	mOrderDetails.setPayment(order.isPayment());
    	mOrderDetails.setDeliveryType(order.isDeliveryType()?"MABUSHIP":"VIETTEL POST");
    	mOrderDetails.setTotalAmount(String.valueOf(order.getTotalAmount()));
    	mOrderDetails.setVoucher(order.getVoucher()==null?"":order.getVoucher().getVoucherCode());
    	
    	
    	List<OrderDetails> details = order.getOrderDetails();
    	List<OrderDetailsDTO> dto = new ArrayList<OrderDetailsDTO>();
    	for(OrderDetails oDetail : details) {
    		OrderDetailsDTO oDTO = new OrderDetailsDTO();
    		oDTO.setProductName(oDetail.getProduct().getName());
    		oDTO.setQuantity(String.valueOf(oDetail.getQuantity()));
    		oDTO.setTotalAmountProduct(String.valueOf(oDetail.getAmountProduct()));
    		dto.add(oDTO);
    	}
    	mOrderDetails.setDetails(dto);
    	return ResponseEntity.ok(mOrderDetails);
    }
    
    @GetMapping("/c/Order")
    public ResponseEntity<?> cancelOrder(@RequestParam("_id_code") String code){
    	Order order = orderService.findByCode(code);
    	order.setDeliveryState("cancel");
    	orderService.save(order);
    	return ResponseEntity.ok("");
    }
    
    @PostMapping("/d/Order")
    public ResponseEntity updateInfoOrder(@RequestBody String json){
    	System.out.println(json);
    	JSONObject jsonObj = new JSONObject(json);
    	Order order = orderService.findByCode(jsonObj.getString("idCode"));
    	order.setCutomerName(jsonObj.getString("fullName"));
    	order.setPhoneNumber(jsonObj.getString("phone"));
    	order.setAddress(jsonObj.getString("address"));
    	order.setNote(jsonObj.getString("note"));
    	orderService.save(order);
    	return ResponseEntity.ok("");
    }
     
    
//    random code for order_code (Date + random)
    
   private String randomCode(User user) {
	   DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyy");
	   String codeDate = LocalDate.now().format(format);
	   String random  = RandomString.make(4);
	   return codeDate+user.getUserId()+random;
}
   
//   Convert the order to orderDetailsDTO
   private List<OrderDetails> convertODFromJson(Order order,JSONArray json){
	   List<OrderDetails> detailList = new ArrayList<OrderDetails>();
	   for(int i = 0; i < json.length(); i++) {
		   JSONObject obj = json.getJSONObject(i);
		   OrderDetails detailsObj = new OrderDetails();
		   detailsObj.setAmountProduct(Float.valueOf(obj.getString("totalOfItem")));
		   detailsObj.setQuantity(Integer.valueOf(obj.getString("quantity")));
		   Product product = productService.findProductByName(obj.getString("nameItem"));
		   detailsObj.setProduct(product);
		   detailsObj.setOrder(order);
		  detailList.add(detailsObj);
	  }
	   return detailList;
   }

}




