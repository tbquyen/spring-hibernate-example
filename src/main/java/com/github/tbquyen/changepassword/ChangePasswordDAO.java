package com.github.tbquyen.changepassword;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.tbquyen.login.UserDetailslmpl;

@Repository
public class ChangePasswordDAO {
	public static final String HQL = "UPDATE UserMaster SET password=:password, status=:status WHERE username = :username";

	@Autowired
	private SessionFactory sessionFactory;

	public int updatePassword(String username, String password) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL).setParameter("username", username)
				.setParameter("status", UserDetailslmpl.NORMAL).setParameter("password", password)
				.executeUpdate();
	}
}
