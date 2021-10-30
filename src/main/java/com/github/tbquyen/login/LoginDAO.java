package com.github.tbquyen.login;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.tbquyen.entity.UserMaster;

@Repository
public class LoginDAO {
	public static final String HQL = "FROM UserMaster WHERE username = :username";

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional(readOnly = true)
	public UserMaster loadUserByUsername(String username) {
		Session session = sessionFactory.getCurrentSession();
		UserMaster user = session.createQuery(HQL, UserMaster.class).setParameter("username", username).uniqueResult();
		return user;
	}
}
