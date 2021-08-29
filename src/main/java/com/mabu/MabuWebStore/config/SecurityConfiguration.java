package com.mabu.MabuWebStore.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.mabu.MabuWebStore.UserLocal.UserLocalDetails;
import com.mabu.MabuWebStore.UserLocal.UserLocalDetailsService;
import com.mabu.MabuWebStore.UserOauth2Client.UserOauthDetails;
import com.mabu.MabuWebStore.UserOauth2Client.UserOauthDetailsService;
import com.mabu.MabuWebStore.entity.User;
import com.mabu.MabuWebStore.service.UserService;
import com.mabu.MabuWebStore.util.CustomSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserOauthDetailsService oauthUserService;
	
	@Autowired
	private UserService userService;

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserLocalDetailsService();
	}

	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
	return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().cors().disable().
		authorizeRequests()
			.antMatchers("/css/**","/js/**","/lib/**","/img/**","/jquery/**","/image/**").permitAll() //accept for static source
			.antMatchers("/api/v1/fa/**","/api/v2/fa/**","/home","/AboutUs","/cart","/product","/oauth2/**","/cart").permitAll()
			.antMatchers("/api/v1/staff/**","/staffDashboard","/api/v1/order/a/**","/api/v2/cbp/**","/api/v2/cbe/**").hasAnyAuthority("ADMIN")
			.antMatchers("/api/v2/member/**","/mDashboard","/api/v1/c/**","/api/v1/d/**","/api/v1/p/**","/api/v1/order/**").hasAnyAuthority("MEMBER","ROLE_USER")
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.successHandler(successHandler)	
			.permitAll()
			.and()
			.oauth2Login()
				.loginPage("/login")
				.userInfoEndpoint()
					.userService(oauthUserService)
				.and()
				.successHandler(new AuthenticationSuccessHandler() {
					
					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {
						UserOauthDetails oauth = (UserOauthDetails) authentication.getPrincipal();
						userService.LoginBySocial(oauth.getEmail(), oauth.getName());
						response.sendRedirect("/mDashboard");
					}
				})
				.and()
				.logout()
				.logoutSuccessHandler(new LogoutSuccessHandler()  {
				     
				    @Override
				    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
				                Authentication authentication)
				            throws IOException, ServletException {
				       UserLocalDetails userDetails = (UserLocalDetails) authentication.getPrincipal();
				       User user = userDetails.getUser();
				       if(user.getRole().getRoleName().equals("ADMIN") || user.getRole().getRoleName().equals("USER")) {
				    	   response.sendRedirect("/login");
				       }else  response.sendRedirect("/login");
				    }
				})
				.permitAll();
				
				
			
		

	}
	
	@Autowired
	private CustomSuccessHandler successHandler;

}
