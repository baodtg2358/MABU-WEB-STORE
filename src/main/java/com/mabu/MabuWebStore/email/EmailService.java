package com.mabu.MabuWebStore.email;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import com.mabu.MabuWebStore.email.emailContent.EmailContent;



public interface EmailService {

	public void SendEmail(String subject, String content, String emailTo) throws UnsupportedEncodingException, MessagingException;

}
