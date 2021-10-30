package com.github.tbquyen.questions;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.tbquyen.entity.QuestionsMaster;

@Repository
public class QuestionsDAO {
	public static final String HQL_SELECT_BY_CATEGORY = "SELECT q FROM QuestionsMaster q INNER JOIN q.category c WHERE c.id=:id";
	public static final String HQL_SELEC_BY_ID = "FROM QuestionsMaster WHERE id=:id";
	public static final String HQL_DELETE_BY_ID = "DELETE QuestionsMaster WHERE id=:id";

	@Autowired
	private SessionFactory sessionFactory;

	public List<QuestionsMaster> getQuestions(int id) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL_SELECT_BY_CATEGORY, QuestionsMaster.class).setParameter("id", id)
				.getResultList();
	}

	public int insert(QuestionsMaster questionsMaster) {
		Session session = sessionFactory.getCurrentSession();
		return (Integer) session.save(questionsMaster);
	}

	public QuestionsMaster getQuestionById(int id) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL_SELEC_BY_ID, QuestionsMaster.class).setParameter("id", id).uniqueResult();
	}

	public int update(QuestionsMaster questionsMaster) {
		Session session = sessionFactory.getCurrentSession();
		session.update(questionsMaster);
		return 1;
	}

	public int delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL_DELETE_BY_ID).setParameter("id", id).executeUpdate();
	}
}
