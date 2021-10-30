package com.github.tbquyen.users;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.tbquyen.entity.UserMaster;
import com.github.tbquyen.login.UserDetailslmpl;

@Repository
public class UsersDAO {
	public static final String HQL_SELECT_ALL = "FROM UserMaster";
	public static final String HQL_SELECT = "FROM UserMaster WHERE username=:username";
	public static final String HQL_UPDATE_PASSWORD = "UPDATE UserMaster SET password=:password, role=:role, status=:status WHERE username=:username";
	public static final String HQL_UPDATE = "UPDATE UserMaster SET role=:role, status=:status WHERE username=:username";
	public static final String HQL_DELETE = "DELETE UserMaster WHERE username=:username";

	@Autowired
	private SessionFactory sessionFactory;

	public List<UserMaster> getUsers() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL_SELECT_ALL, UserMaster.class).getResultList();
	}

	public UserMaster getUser(String username) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL_SELECT, UserMaster.class).setParameter("username", username).uniqueResult();
	}

	public String insert(UserMaster userMaster) {
		Session session = sessionFactory.getCurrentSession();
		return (String) session.save(userMaster);
	}

	public int update(String username, String role, int status) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL_UPDATE).setParameter("username", username).setParameter("role", role).setParameter("status", status)
				.executeUpdate();
	}

	public int update(String username, String password, String role) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL_UPDATE_PASSWORD).setParameter("password", password)
				.setParameter("username", username).setParameter("role", role).setParameter("status", UserDetailslmpl.CREDENTIALSNONEXPIRED).executeUpdate();
	}

	public int delete(String username) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL_DELETE).setParameter("username", username).executeUpdate();
	}
}
