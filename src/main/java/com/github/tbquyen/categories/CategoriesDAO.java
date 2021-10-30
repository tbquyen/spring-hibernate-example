package com.github.tbquyen.categories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.tbquyen.entity.CategoriesMaster;

@Repository
public class CategoriesDAO {
	public static final String HQL_SELECTALL = "FROM CategoriesMaster";
	public static final String HQL_SELEC_BY_NAME = "FROM CategoriesMaster WHERE name=:name";
	public static final String HQL_SELEC_BY_ID = "FROM CategoriesMaster WHERE id=:id";
	public static final String HQL_UPDATE_BY_ID = "UPDATE CategoriesMaster SET name=:name WHERE id=:id";
	public static final String HQL_DELETE_BY_ID = "DELETE CategoriesMaster WHERE id=:id";

	@Autowired
	private SessionFactory sessionFactory;

	public List<CategoriesMaster> getCategories() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL_SELECTALL, CategoriesMaster.class).getResultList();
	}

	public CategoriesMaster getCategory(String name) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL_SELEC_BY_NAME, CategoriesMaster.class).setParameter("name", name).uniqueResult();
	}

	public int insert(String name) {
		Session session = sessionFactory.getCurrentSession();
		CategoriesMaster categoriesMaster = new CategoriesMaster();
		categoriesMaster.setName(name);
		categoriesMaster.setStatus(0);
		return (Integer) session.save(categoriesMaster);
	}

	public CategoriesMaster getCategoryById(int id) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL_SELEC_BY_ID, CategoriesMaster.class).setParameter("id", id).uniqueResult();
	}

	public int update(int id, String name) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL_UPDATE_BY_ID).setParameter("id", id).setParameter("name", name).executeUpdate();
	}

	public int delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL_DELETE_BY_ID).setParameter("id", id).executeUpdate();
	}
}
