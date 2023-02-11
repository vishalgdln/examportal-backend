package com.exam.model;

import org.springframework.security.core.GrantedAuthority;

public class Authorities implements GrantedAuthority {

	private String authority;

	public Authorities(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return null;
	}

}
