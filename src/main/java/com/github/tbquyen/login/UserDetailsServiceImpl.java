package com.github.tbquyen.login;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.tbquyen.entity.UserMaster;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private LoginDAO loginDAO;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserMaster user = loginDAO.loadUserByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("Username not found: " + username);
		}

		UserDetailslmpl detailslmpl = new UserDetailslmpl();
		BeanUtils.copyProperties(user, detailslmpl);
		return detailslmpl;
	}
}
