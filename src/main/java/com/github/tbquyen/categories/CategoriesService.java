package com.github.tbquyen.categories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.tbquyen.entity.CategoriesMaster;

@Service
public class CategoriesService {
	@Autowired
	private CategoriesDAO categoriesDAO;

	@Transactional(readOnly = true)
	public List<CategoriesMaster> getCategories() {
		return categoriesDAO.getCategories();
	}

	@Transactional(readOnly = true)
	public CategoriesMaster getCategory(String name) {
		return categoriesDAO.getCategory(name);
	}

	@Transactional(rollbackFor = Exception.class)
	public int insert(String name) {
		return categoriesDAO.insert(name);
	}

	@Transactional(readOnly = true)
	public CategoriesMaster getCategoryById(int id) {
		return categoriesDAO.getCategoryById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public int update(int id, String name) {
		return categoriesDAO.update(id, name);
	}

	@Transactional(rollbackFor = Exception.class)
	public int delete(int id) {
		return categoriesDAO.delete(id);
	}
}
