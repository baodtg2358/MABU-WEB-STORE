package com.mabu.MabuWebStore.UserOauth2Client;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class UserOauthDetails implements OAuth2User{

	private OAuth2User oauthUser;
	
	public UserOauthDetails(OAuth2User user) {
		this.oauthUser = user;
	}
	
	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return oauthUser.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return oauthUser.getAuthorities();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return oauthUser.getAttribute("email");
	}
	
	public String getEmail() {
		return oauthUser.getAttribute("email");
	}

}
