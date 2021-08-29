package com.mabu.MabuWebStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mabu.MabuWebStore.paypal.PayPalService;
import com.mabu.MabuWebStore.paypal.PaypalOrder;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PayPalController {
	
	
	
	@Autowired
	private PayPalService service;
	
	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";
	
	

	
	@PostMapping("/pay")
	public String payment(@ModelAttribute("order") PaypalOrder order) {
		System.out.println(order.getCurrency());
		System.out.println(order.getDescription());
		
		try {
			Payment payment = service.createPayment(10.0, "USD", 
					"paypal", "sale", "123", "http://localhost:8080/"+ CANCEL_URL,"http://localhost:8080/"+ SUCCESS_URL);
			for(Links link : payment.getLinks()) {
				System.out.println(link.getHref());
				if(link.getRel().equals("approval_url")) {
					return "redirect:"+link.getHref();
				}
			}
//		
		}catch(PayPalRESTException e) {
			e.printStackTrace();
			e.getDetails();
		}
		return "redirect:/";
	}
	
//	@GetMapping(value = CANCEL_URL)
//	public String cancelPay() {
//		return "redirect:/api/v1/order/checkout/reject";
//	}
//	
//	@GetMapping(value = SUCCESS_URL)
//	@ResponseBody
//	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, RedirectAttributes redirect) throws PayPalRESTException {
//		
//		Payment payment = service.executePayment(paymentId, payerId);
//		System.out.println("Transactions ne: "+  payment.getTransactions());
//		if(payment.getState().equals("approved")) {
//			redirect.addFlashAttribute("checkoutDescription",payment.getTransactions());
//			return "redirect:/api/v1/order/checkout/p2";
//		}
//		return "false";
//	}
	
	
}
