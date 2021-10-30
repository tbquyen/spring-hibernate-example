package com.github.tbquyen.categories;

public class CategoriesForm {
	public static final String VALIDATOR = "CategoriesResult";
	public static final String NAME = "CategoriesForm";

	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
