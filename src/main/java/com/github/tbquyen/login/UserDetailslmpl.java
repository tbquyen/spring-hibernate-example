package com.github.tbquyen.login;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.tbquyen.entity.UserMaster;

public class UserDetailslmpl extends UserMaster implements UserDetails {
	private static final long serialVersionUID = 1L;
	public static final int CREDENTIALSNONEXPIRED = 0;
	public static final int NORMAL = 1;
	public static final int ACCOUNTNONLOCKED = 2;
	public static final int ACCOUNTNONEXPIRED = 3;
	public static final int DISABLED = 4;

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.commaSeparatedStringToAuthorityList(getRole());
	}

	public boolean isAccountNonExpired() {
		return getStatus() != ACCOUNTNONEXPIRED;
	}

	public boolean isAccountNonLocked() {
		return getStatus() != ACCOUNTNONLOCKED;
	}

	public boolean isCredentialsNonExpired() {
		return getStatus() != CREDENTIALSNONEXPIRED;
	}

	public boolean isEnabled() {
		return getStatus() != DISABLED;
	}
}
