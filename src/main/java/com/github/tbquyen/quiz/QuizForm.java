package com.github.tbquyen.quiz;

public class QuizForm {
	public static final String VALIDATOR = "QuizFormResult";
	public static final String NAME = "QuizForm";

	private String numberofquestions;
	private String categoryId;
	private String anser;
	private String end;
	private String next;
	private String previous;
	private String total;

	public String getNumberofquestions() {
		return numberofquestions;
	}

	public void setNumberofquestions(String numberofquestions) {
		this.numberofquestions = numberofquestions;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getAnser() {
		return anser;
	}

	public void setAnser(String anser) {
		this.anser = anser;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}
}
