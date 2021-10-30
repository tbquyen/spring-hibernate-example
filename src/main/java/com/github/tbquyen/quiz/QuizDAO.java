package com.github.tbquyen.quiz;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.tbquyen.entity.QuestionsMaster;
import com.github.tbquyen.entity.Quiz;
import com.github.tbquyen.entity.QuizInfo;

@Repository
public class QuizDAO {
	public static final String HQL_SELECT_QUESTIONS_BY_CATEGORY = "FROM QuestionsMaster WHERE category.id=:id ORDER BY RAND()";
	public static final String HQL_SELECT_QUESTIONS = "FROM QuestionsMaster ORDER BY RAND()";
	public static final String HQL_SELECT_QUIZ_BY_ID = "FROM Quiz WHERE id=:id";
	public static final String HQL_SELECT_QUESTIONINFO_BY_QUIZ = "SELECT i FROM QuizInfo i INNER JOIN i.quiz q INNER JOIN i.question qs WHERE q.id=:id ORDER BY i.id";
	public static final String HQL_END_QUIZ = "UPDATE Quiz SET timeEnd=:timeEnd WHERE id=:id";

	@Autowired
	private SessionFactory sessionFactory;

	public List<QuestionsMaster> questions(int id, int numberofquestions) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL_SELECT_QUESTIONS_BY_CATEGORY, QuestionsMaster.class).setParameter("id", id)
				.setMaxResults(numberofquestions).getResultList();
	}

	public List<QuestionsMaster> questions(int numberofquestions) {
		Session session = sessionFactory.getCurrentSession();

		return session.createQuery(HQL_SELECT_QUESTIONS, QuestionsMaster.class).setMaxResults(numberofquestions)
				.getResultList();
	}

	public int insert(QuizInfo quizInfo) {
		Session session = sessionFactory.getCurrentSession();
		return (Integer) session.save(quizInfo);
	}

	public int insert(Quiz quiz) {
		Session session = sessionFactory.getCurrentSession();
		return (Integer) session.save(quiz);
	}

	public QuizInfo getQuizinfo(int id, int index) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL_SELECT_QUESTIONINFO_BY_QUIZ, QuizInfo.class).setParameter("id", id)
				.setFirstResult(index - 1).setMaxResults(1).uniqueResult();
	}

	public Quiz getQuizById(int id) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL_SELECT_QUIZ_BY_ID, Quiz.class).setParameter("id", id).uniqueResult();
	}

	public void anser(QuizInfo quizinfo) {
		Session session = sessionFactory.getCurrentSession();
		session.save(quizinfo);
	}

	public void anser(Quiz quiz) {
		Session session = sessionFactory.getCurrentSession();
		session.update(quiz);
	}

	public int end(int id) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(HQL_END_QUIZ).setParameter("timeEnd", new Date()).setParameter("id", id).executeUpdate();
	}
}
