package com.github.tbquyen.categories;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.tbquyen.config.BaseController;
import com.github.tbquyen.entity.CategoriesMaster;

@Controller
@RequestMapping(value = "/categories")
public class CategoriesController extends BaseController {
	private static final Logger LOGGER = LogManager.getLogger(CategoriesController.class);

	@Autowired
	private CategoriesService service;
	@Autowired
	private CategoriesValidator validator;
	@Autowired
	private MessageSource messageSource;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null)
			return;

		if (CategoriesForm.class == binder.getTarget().getClass()) {
			binder.addValidators(validator);
		}
	}

	@GetMapping
	public ModelAndView get(ModelMap modelMap, @ModelAttribute(CategoriesForm.NAME) CategoriesForm form) {
		LOGGER.debug("GET: categories");
		loadRedirectErrors(modelMap, CategoriesForm.NAME, CategoriesForm.VALIDATOR);

		List<CategoriesMaster> list = service.getCategories();
		modelMap.addAttribute("categories", list);
		return new ModelAndView("categories");
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getId(@PathVariable(name = "id") int id) {
		LOGGER.debug("GET: category");
		JSONObject jsonObject = new JSONObject(service.getCategoryById(id));
		return jsonObject.toString();
	}

	@PostMapping
	public ModelAndView post(ModelMap modelMap, @ModelAttribute(CategoriesForm.NAME) @Validated CategoriesForm form,
			BindingResult result, RedirectAttributes redirectAttributes) {
		LOGGER.debug("POST: category");
		if (result.hasErrors()) {
			redirectErrors(redirectAttributes, CategoriesForm.NAME, form, CategoriesForm.VALIDATOR, result);
		} else {
			if (form.getId() == 0) {
				int id = service.insert(form.getName());
				if (id > -1) {
					modelMap.addAttribute(SUCCESS,
							messageSource.getMessage("SUCCESS.001", new String[] { "category" }, null));
				}
			} else {
				int row = service.update(form.getId(), form.getName());
				if (row > 0) {
					modelMap.addAttribute(SUCCESS,
							messageSource.getMessage("SUCCESS.002", new String[] { "category" }, null));
				}
			}

		}

		return new ModelAndView("redirect:/categories");
	}

	@GetMapping(value = "/d/{id}")
	public ModelAndView deleteId(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes) {
		LOGGER.debug("DELETE: category");
		int row = service.delete(id);
		if (row > 0) {
			redirectAttributes.addFlashAttribute(SUCCESS,
					messageSource.getMessage("SUCCESS.003", new String[] { "category" }, null));
		}

		return new ModelAndView("redirect:/categories");
	}
}
