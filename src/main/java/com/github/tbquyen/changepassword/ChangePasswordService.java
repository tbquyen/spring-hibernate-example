package com.github.tbquyen.changepassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangePasswordService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ChangePasswordDAO changePasswordDAO;

	@Transactional(rollbackFor = Exception.class)
	public int updatePassword(String username, String newpassword) {
		return changePasswordDAO.updatePassword(username, passwordEncoder.encode(newpassword));
	}
}
