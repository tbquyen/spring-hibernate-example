package com.github.tbquyen.users;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.tbquyen.config.BaseController;
import com.github.tbquyen.entity.UserMaster;

@Controller
@RequestMapping(value = "/users")
public class UsersController extends BaseController {
	private static final Logger LOGGER = LogManager.getLogger(UsersController.class);

	@Autowired
	private UsersService service;
	@Autowired
	private UsersValidator validator;
	@Autowired
	private MessageSource messageSource;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null)
			return;

		if (UsersForm.class == binder.getTarget().getClass()) {
			binder.addValidators(validator);
		}
	}

	@GetMapping
	public ModelAndView get(ModelMap modelMap, @ModelAttribute(UsersForm.NAME) UsersForm form) {
		LOGGER.debug("GET: users");
		loadRedirectErrors(modelMap, UsersForm.NAME, UsersForm.VALIDATOR);

		List<UserMaster> list = service.getCategories();
		modelMap.addAttribute("users", list);
		return new ModelAndView("users");
	}

	@GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getId(@PathVariable(name = "username") String username) {
		LOGGER.debug("GET: username=" + username);
		JSONObject jsonObject = new JSONObject(service.getUser(username));
		return jsonObject.toString();
	}

	@PostMapping
	public ModelAndView post(ModelMap modelMap, @ModelAttribute(UsersForm.NAME) @Validated UsersForm form,
			BindingResult result, RedirectAttributes redirectAttributes) {
		LOGGER.debug("POST: category");
		if (result.hasErrors()) {
			redirectErrors(redirectAttributes, UsersForm.NAME, form, UsersForm.VALIDATOR, result);
		} else {
			if ("+".equals(form.getAction())) {
				UserMaster userMaster = new UserMaster();
				BeanUtils.copyProperties(form, userMaster);

				String username = service.insert(userMaster);
				if (!StringUtils.isEmpty(username)) {
					redirectAttributes.addFlashAttribute(SUCCESS,
							messageSource.getMessage("SUCCESS.001", new String[] { "user" }, null));
				}
			} else {
				int row = service.update(form.getUsername(), form.getPassword(), form.getRole(), form.getStatus());
				if (row > 0) {
					redirectAttributes.addFlashAttribute(SUCCESS,
							messageSource.getMessage("SUCCESS.002", new String[] { "user" }, null));
				}
			}

		}

		return new ModelAndView("redirect:/users");
	}

	@GetMapping(value = "/d/{username}")
	public ModelAndView deleteId(@PathVariable(name = "username") String username,
			RedirectAttributes redirectAttributes) {
		LOGGER.debug("DELETE: category");
		int row = service.delete(username);
		if (row > 0) {
			redirectAttributes.addFlashAttribute(SUCCESS,
					messageSource.getMessage("SUCCESS.003", new String[] { "user" }, null));
		}

		return new ModelAndView("redirect:/users");
	}
}
