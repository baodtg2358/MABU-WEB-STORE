package com.mabu.MabuWebStore.email;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.mabu.MabuWebStore.email.emailContent.EmailContent;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void SendEmail(String subject, String content, String emailTo) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
		
		helper.setFrom("mabuteams@gmail.com","MABU CENTER");
		System.out.print(emailTo);
		helper.setTo(emailTo);
		helper.setSubject(subject);
		helper.setText(content,true);
		helper.addInline("MABULogo", new File("src/img/logoblack.jpg"));
		mailSender.send(message);
		System.out.println("Mail already send!");
		
	}
	


	
	
}
