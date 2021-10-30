package com.github.tbquyen.quiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.tbquyen.config.WebPrincipal;
import com.github.tbquyen.entity.CategoriesMaster;
import com.github.tbquyen.entity.QuestionsMaster;
import com.github.tbquyen.entity.Quiz;
import com.github.tbquyen.entity.QuizInfo;
import com.github.tbquyen.entity.UserMaster;

@Service
public class QuizService {
	@Autowired
	private QuizDAO dao;

	@Transactional(rollbackFor = Exception.class)
	public int createQuiz(String categoryId, String numberofquestions) {
		CategoriesMaster category = null;
		List<QuestionsMaster> questions = null;
		int id = NumberUtils.toInt(categoryId);
		int questionsCount = NumberUtils.toInt(numberofquestions);

		if (id == 0) {
			questions = dao.questions(questionsCount);
		} else {
			category = new CategoriesMaster();
			category.setId(id);
			questions = dao.questions(id, questionsCount);
		}

		if (questions.size() == 0) {
			return 0;
		}

		UserMaster user = new UserMaster();
		user.setUsername(WebPrincipal.getInstance().getName());

		Quiz quiz = new Quiz();
		quiz.setUser(user);
		quiz.setCategory(category);
		quiz.setQuestions(questionsCount);
		quiz.setTimeStart(new Date());

		List<QuizInfo> listQuizinfo = new ArrayList<QuizInfo>();
		QuizInfo quizinfo = null;
		for (QuestionsMaster question : questions) {
			quiz.setDuration(quiz.getDuration() + question.getDuration());

			quizinfo = new QuizInfo();
			quizinfo.setQuiz(quiz);
			quizinfo.setQuestion(question);
			listQuizinfo.add(quizinfo);
		}

		int quizId = dao.insert(quiz);

		for (QuizInfo quizInfo : listQuizinfo) {
			dao.insert(quizInfo);
		}

		return quizId;
	}

	@Transactional(readOnly = true)
	public QuizInfo getQuizinfo(String id, String index) {
		return dao.getQuizinfo(NumberUtils.toInt(id), NumberUtils.toInt(index));
	}

	@Transactional(readOnly = true)
	public Quiz getQuizById(String id) {
		return dao.getQuizById(NumberUtils.toInt(id));
	}

	@Transactional(rollbackFor = Exception.class)
	public int anser(String id, String index, String anser) {
		QuizInfo quizinfo = dao.getQuizinfo(NumberUtils.toInt(id), NumberUtils.toInt(index));
		if (quizinfo == null) {
			return -1;
		}

		String currAnser = quizinfo.getAnser();
		if (anser.equals(currAnser)) {
			return 0;
		}

		quizinfo.setAnser(anser);
		quizinfo.setTimeAnser(new Date());

		dao.anser(quizinfo);

		Quiz quiz = dao.getQuizById(NumberUtils.toInt(id));

		if (anser.equals(quizinfo.getQuestion().getAnser())) {
			quiz.setPassed(quiz.getPassed() + 1);
			dao.anser(quiz);
		} else if (quizinfo.getQuestion().getAnser().equals(currAnser)) {
			quiz.setPassed(quiz.getPassed() - 1);
			dao.anser(quiz);
		}

		return 1;
	}

	@Transactional(rollbackFor = Exception.class)
	public Quiz end(String id) {
		dao.end(NumberUtils.toInt(id));
		return dao.getQuizById(NumberUtils.toInt(id));
	}
}
