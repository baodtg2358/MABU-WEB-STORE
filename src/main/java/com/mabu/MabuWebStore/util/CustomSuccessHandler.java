package com.mabu.MabuWebStore.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mabu.MabuWebStore.UserLocal.UserLocalDetails;
import com.mabu.MabuWebStore.entity.User;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	

	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		UserLocalDetails userLocal = (UserLocalDetails) authentication.getPrincipal();
		User user = userLocal.getUser();
		if(user.getOTP()!=null && user.isInExpiredTime()) {
			
			response.sendRedirect("api/v1/fa/new-pass?email="+ Base64.getUrlEncoder().encodeToString(user.getEmail().getBytes()));
		}else if( !user.isActived()) {
			response.sendRedirect("/login?error");
		}else if(user.getRole().getRoleName().equals("ADMIN") || user.getRole().getRoleName().equals("USER")) {
			response.sendRedirect("/staffDashboard");
		}else response.sendRedirect("/product");
		
	}
	
	

	
	
}
