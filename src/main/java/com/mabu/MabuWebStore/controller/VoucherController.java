package com.mabu.MabuWebStore.controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.mail.MessagingException;

import com.mabu.MabuWebStore.email.EmailServiceImpl;
import com.mabu.MabuWebStore.email.emailContent.EmailContent;
import com.mabu.MabuWebStore.entity.User;
import com.mabu.MabuWebStore.entity.Voucher;
import com.mabu.MabuWebStore.service.UserService;
import com.mabu.MabuWebStore.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoucherController {
    @Autowired
    private VoucherService voucherService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private EmailServiceImpl emailService;
    
    @GetMapping("api/v1/staff/voucher")
    public List<Voucher> getAllVoucher(){
        List<Voucher> allVoucher = voucherService.findAllVoucher();
        return allVoucher;
    }

    
//    Only for member
    @GetMapping("/api/v1/fa/voucher/search")
    public ResponseEntity<?> getVoucherById(@RequestParam("code") String code){
    	
        Voucher voucherByCode = voucherService.findByVoucherCode(code);
        if(voucherByCode == null) {
        	return new ResponseEntity<>("",HttpStatus.METHOD_NOT_ALLOWED);
        }else if(LocalDate.now().isBefore(voucherByCode.getStartDate()) || LocalDate.now().isAfter(voucherByCode.getEndDate())) {
        	return new ResponseEntity<>("Event non actived",HttpStatus.METHOD_NOT_ALLOWED);
        
        }else return new ResponseEntity<>(voucherByCode, HttpStatus.OK);
    }
    
    
    @GetMapping("/api/v1/staff/voucher/search/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email){
    	User user = userService.findByEmail(email);
    	return new ResponseEntity<>(user, HttpStatus.OK);
    }

	@RequestMapping(value = "api/v1/staff/voucher", produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,method = RequestMethod.POST)
    public ResponseEntity<?> createVoucher(@ModelAttribute Voucher voucher){	
        Voucher newVoucher = voucherService.createVoucher(voucher);
        	return new ResponseEntity<>(newVoucher, HttpStatus.OK);
    }

    @RequestMapping(value = "api/v1/staff/voucher/update/{id}", produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,method = RequestMethod.PUT)
    public ResponseEntity<?> updateVoucher(@ModelAttribute Voucher voucher, @PathVariable("id") Integer id){
        voucher.setVoucher_id(id);
        Voucher updateVoucher = voucherService.updateVoucher(voucher);
        return new ResponseEntity<>(updateVoucher, HttpStatus.OK);
    }
    

    @DeleteMapping("api/v1/staff/voucher/delete/{id}")
    public ResponseEntity<?> deleteVoucher(@PathVariable(value = "id") Integer id){
        voucherService.deleteVoucher(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("api/v1/staff/email/{code}")
    public ResponseEntity<?> sendEmailVoucher(@PathVariable(value="code") String code) throws UnsupportedEncodingException, MessagingException{
    	Voucher voucher = voucherService.findByVoucherCode(code);
    	sendEmailEvent(voucher);
    	return ResponseEntity.ok("");
    	
    }
    
    public void sendEmailEvent(Voucher voucher) throws UnsupportedEncodingException, MessagingException {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	List<User> member = userService.getAllMember();
        for(User m : member) {
        	String content = EmailContent.EventContent(m.getFullName(),voucher.getContent(), voucher.getStartDate().format(formatter), voucher.getEndDate().format(formatter),voucher.getVoucherCode(), String.valueOf(voucher.getDiscount()));
        	emailService.SendEmail("MABU VOUCHER WITH EVENT", content, m.getEmail());
        }
    }
    
    
}
