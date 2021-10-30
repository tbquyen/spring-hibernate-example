package com.github.tbquyen.quizreport;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.tbquyen.entity.Quiz;

@Repository
public class QuizReportDAO {
	public static final String HQL_SELECT_QUIZ = "SELECT q FROM Quiz q LEFT JOIN FETCH q.category";

	@Autowired
	private SessionFactory sessionFactory;

	public List<Quiz> getReport() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL_SELECT_QUIZ, Quiz.class).setMaxResults(10).getResultList();
	}
}
