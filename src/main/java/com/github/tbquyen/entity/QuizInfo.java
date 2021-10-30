package com.github.tbquyen.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "quizinformation")
public class QuizInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "quizID", referencedColumnName = "id", updatable = false)
	private Quiz quiz;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "questionID", referencedColumnName = "id", updatable = false)
	private QuestionsMaster question;

	@Column(name = "anser")
	private String anser;

	@Column(name = "timeanser")
	private Date timeAnser;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public QuestionsMaster getQuestion() {
		return question;
	}

	public void setQuestion(QuestionsMaster question) {
		this.question = question;
	}

	public String getAnser() {
		return anser;
	}

	public void setAnser(String anser) {
		this.anser = anser;
	}

	public Date getTimeAnser() {
		return timeAnser;
	}

	public void setTimeAnser(Date timeAnser) {
		this.timeAnser = timeAnser;
	}
}
