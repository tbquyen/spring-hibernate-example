package com.github.tbquyen.questions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.tbquyen.categories.CategoriesDAO;
import com.github.tbquyen.entity.CategoriesMaster;
import com.github.tbquyen.entity.QuestionsMaster;

@Service
public class QuestionsService {
	@Autowired
	private QuestionsDAO dao;
	@Autowired
	private CategoriesDAO categoriesDAO;

	@Transactional(readOnly = true)
	public List<QuestionsMaster> getQuestions(int categoryId) {
		return dao.getQuestions(categoryId);
	}

	@Transactional(rollbackFor = Exception.class)
	public int insert(QuestionsMaster questionsMaster) {
		return dao.insert(trimToNull(questionsMaster));
	}

	@Transactional(readOnly = true)
	public QuestionsMaster getQuestionById(int id) {
		return dao.getQuestionById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public int update(QuestionsMaster questionsMaster) {
		return dao.update(questionsMaster);
	}

	@Transactional(rollbackFor = Exception.class)
	public int delete(int id) {
		return dao.delete(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public int upload(MultipartFile file) throws IOException {
		Workbook wb = new XSSFWorkbook(file.getInputStream());
		Sheet ws = wb.getSheetAt(0);
		DataFormatter dfm = new DataFormatter();

		List<CategoriesMaster> categories = categoriesDAO.getCategories();

		List<QuestionsMaster> questions = new ArrayList<QuestionsMaster>();
		QuestionsMaster question = null;
		long lastRow = ws.getLastRowNum();
		Row row = null;
		for (int i = 2; i < lastRow; i++) {
			row = ws.getRow(i);
			if (row == null) {
				continue;
			}

			question = new QuestionsMaster();
			question.setCategory(getCategory(categories,
					dfm.formatCellValue(row.getCell(2, MissingCellPolicy.CREATE_NULL_AS_BLANK))));
			question.setQuestion(dfm.formatCellValue(row.getCell(3, MissingCellPolicy.CREATE_NULL_AS_BLANK)));
			question.setOptionA(dfm.formatCellValue(row.getCell(4, MissingCellPolicy.CREATE_NULL_AS_BLANK)));
			question.setOptionB(dfm.formatCellValue(row.getCell(5, MissingCellPolicy.CREATE_NULL_AS_BLANK)));
			question.setOptionC(dfm.formatCellValue(row.getCell(6, MissingCellPolicy.CREATE_NULL_AS_BLANK)));
			question.setOptionD(dfm.formatCellValue(row.getCell(7, MissingCellPolicy.CREATE_NULL_AS_BLANK)));
			question.setAnser(dfm.formatCellValue(row.getCell(8, MissingCellPolicy.CREATE_NULL_AS_BLANK)));
			question.setDuration(
					NumberUtils.toInt(dfm.formatCellValue(row.getCell(9, MissingCellPolicy.CREATE_NULL_AS_BLANK))));
			question.setExplanation(dfm.formatCellValue(row.getCell(10, MissingCellPolicy.CREATE_NULL_AS_BLANK)));

			if (question.getCategory() != null) {
				questions.add(question);
			}
		}

		wb.close();

		if (questions.size() == 0)
			return 0;

		for (QuestionsMaster questionsMaster : questions) {
			dao.insert(trimToNull(questionsMaster));
		}

		return questions.size();
	}

	private CategoriesMaster getCategory(List<CategoriesMaster> categories, String categoryName) {
		for (CategoriesMaster category : categories) {
			if (category.getName().equals(categoryName))
				return category;
		}

		return null;
	}

	private QuestionsMaster trimToNull(QuestionsMaster questionsMaster) {
		if (questionsMaster == null) {
			return null;
		}

		questionsMaster.setQuestion(StringUtils.trimToNull(questionsMaster.getQuestion()));
		questionsMaster.setOptionA(StringUtils.trimToNull(questionsMaster.getOptionA()));
		questionsMaster.setOptionB(StringUtils.trimToNull(questionsMaster.getOptionB()));
		questionsMaster.setOptionC(StringUtils.trimToNull(questionsMaster.getOptionC()));
		questionsMaster.setOptionD(StringUtils.trimToNull(questionsMaster.getOptionD()));
		questionsMaster.setAnser(StringUtils.trimToNull(questionsMaster.getAnser()));
		questionsMaster.setExplanation(StringUtils.trimToNull(questionsMaster.getExplanation()));

		return questionsMaster;
	}
}
