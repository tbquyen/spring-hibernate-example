package com.github.tbquyen.users;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.tbquyen.entity.UserMaster;

@Service
public class UsersService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UsersDAO dao;

	@Transactional(readOnly = true)
	public List<UserMaster> getCategories() {
		return dao.getUsers();
	}

	@Transactional(readOnly = true)
	public UserMaster getUser(String username) {
		return dao.getUser(username);
	}

	@Transactional(rollbackFor = Exception.class)
	public String insert(UserMaster userMaster) {
		userMaster.setPassword(passwordEncoder.encode(userMaster.getPassword()));
		return dao.insert(userMaster);
	}

	@Transactional(rollbackFor = Exception.class)
	public int update(String username, String password, String role, String status) {
		if (StringUtils.isEmpty(password)) {
			return dao.update(username, role, NumberUtils.toInt(status));
		}

		return dao.update(username, password, role);
	}

	@Transactional(rollbackFor = Exception.class)
	public int delete(String username) {
		return dao.delete(username);
	}
}
