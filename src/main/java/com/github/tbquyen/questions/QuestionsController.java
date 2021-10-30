package com.github.tbquyen.questions;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.tbquyen.categories.CategoriesService;
import com.github.tbquyen.config.BaseController;
import com.github.tbquyen.entity.CategoriesMaster;
import com.github.tbquyen.entity.QuestionsMaster;

@Controller
@RequestMapping(value = "/questions")
public class QuestionsController extends BaseController {
	private static final Logger LOGGER = LogManager.getLogger(QuestionsController.class);

	@Autowired
	private QuestionsService service;
	@Autowired
	private CategoriesService categoriesService;
	@Autowired
	private QuestionsValidator validator;
	@Autowired
	private MessageSource messageSource;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null)
			return;

		if (QuestionsForm.class == binder.getTarget().getClass()) {
			binder.addValidators(validator);
		}
	}

	@GetMapping(value = { "", "/category/{categoryKEY}" })
	public ModelAndView get(ModelMap modelMap, @PathVariable(name = "categoryKEY", required = false) String id,
			@ModelAttribute(QuestionsForm.NAME) QuestionsForm form, RedirectAttributes redirectAttributes) {
		LOGGER.debug("GET: questions, category=" + id);
		loadRedirectErrors(modelMap, QuestionsForm.NAME, QuestionsForm.VALIDATOR);

		List<CategoriesMaster> categories = categoriesService.getCategories();

		if (categories.size() == 0) {
			redirectAttributes.addFlashAttribute(ERROR, messageSource.getMessage("category.001", null, null));
			return new ModelAndView("redirect:/categories");
		}

		modelMap.addAttribute("categories", categories);

		int categoryID = NumberUtils.toInt(id);
		form.setCategoryId(categoryID);
		if (categoryID == 0) {
			form.setCategoryId(categories.get(0).getId());
		}

		modelMap.addAttribute("questions", service.getQuestions(form.getCategoryId()));
		return new ModelAndView("questions");
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getId(@PathVariable(name = "id") String id) {
		LOGGER.debug("GET: question=" + id);
		JSONObject jsonObject = new JSONObject(service.getQuestionById(NumberUtils.toInt(id)));
		return jsonObject.toString();
	}

	@PostMapping
	public ModelAndView post(@ModelAttribute(QuestionsForm.NAME) @Validated QuestionsForm form,
			BindingResult result, RedirectAttributes redirectAttributes) {
		LOGGER.debug("POST: question");
		if (result.hasErrors()) {
			redirectErrors(redirectAttributes, QuestionsForm.NAME, form, QuestionsForm.VALIDATOR, result);
		} else {
			QuestionsMaster questionsMaster = new QuestionsMaster();
			BeanUtils.copyProperties(form, questionsMaster);
			CategoriesMaster categoriesMaster = new CategoriesMaster();
			categoriesMaster.setId(form.getCategoryId());
			questionsMaster.setCategory(categoriesMaster);

			if (form.getId() == 0) {
				int id = service.insert(questionsMaster);
				if (id > -1) {
					redirectAttributes.addFlashAttribute(SUCCESS,
							messageSource.getMessage("SUCCESS.001", new String[] { "question" }, null));
				}
			} else {
				int row = service.update(questionsMaster);
				if (row > 0) {
					redirectAttributes.addFlashAttribute(SUCCESS,
							messageSource.getMessage("SUCCESS.002", new String[] { "question" }, null));
				}
			}
		}

		return new ModelAndView("redirect:/questions/category/" + form.getCategoryId());
	}

	@PostMapping(value = "/upload")
	public ModelAndView upload(@RequestParam("file") MultipartFile file,
			@RequestParam("categoryId") String categoryId, RedirectAttributes redirectAttributes)
			throws IOException {
		LOGGER.debug("POST: upload");
		int row = service.upload(file);
		if (row > 0) {
			redirectAttributes.addFlashAttribute(SUCCESS,
					messageSource.getMessage("SUCCESS.001", new String[] { "file" }, null));
		}

		return new ModelAndView("redirect:/questions/category/" + categoryId);
	}

	@GetMapping(value = "/{categoriId}/{id}")
	public ModelAndView deleteId(@PathVariable(name = "categoriId") String categoriId, @PathVariable(name = "id") String id,
			RedirectAttributes redirectAttributes) {
		LOGGER.debug("DELETE: question=" + id);
		int row = service.delete(NumberUtils.toInt(id));
		if (row > 0) {
			redirectAttributes.addFlashAttribute(SUCCESS,
					messageSource.getMessage("SUCCESS.003", new String[] { "question" }, null));
		}

		return new ModelAndView("redirect:/questions/category/" + categoriId);
	}
}
