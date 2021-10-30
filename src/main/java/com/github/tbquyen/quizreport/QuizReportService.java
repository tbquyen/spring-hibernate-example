package com.github.tbquyen.quizreport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.tbquyen.entity.Quiz;

@Service
public class QuizReportService {
	@Autowired
	private QuizReportDAO dao;

	@Transactional(readOnly = true)
	public List<Quiz> getReport() {
		return dao.getReport();
	}
}
