package com.github.tbquyen.changepassword;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.github.tbquyen.config.WebPrincipal;
import com.github.tbquyen.login.LoginForm;

@Controller
@RequestMapping(value = { "/changePassword" })
public class ChangePasswordController {
	@Autowired
	private ChangePasswordValidator validator;
	@Autowired
	private ChangePasswordService changePasswordService;

	private DefaultRedirect defaultRedirect = new DefaultRedirect();

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validator);
	}

	@GetMapping
	public ModelAndView changePasswordGET(@ModelAttribute("ChangePasswordForm") ChangePasswordForm form,
			@RequestAttribute(name = "LoginForm", required = false) LoginForm formForward) {
		if (formForward != null) {
			BeanUtils.copyProperties(formForward, form);
		} else {
			if (WebPrincipal.getInstance().isAuthenticated()) {
				form.setUsername(WebPrincipal.getInstance().getName());
			}
		}
		return new ModelAndView("changePassword");
	}

	@PostMapping
	public ModelAndView changePasswordPOST(@ModelAttribute("ChangePasswordForm") @Validated ChangePasswordForm form,
			BindingResult result, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("changePassword");

		if (result.hasErrors()) {
			mv.setStatus(HttpStatus.BAD_REQUEST);
			return mv;
		}

		int rcUpdate = changePasswordService.updatePassword(form.getUsername(), form.getNewpassword());

		if (rcUpdate == 0) {
			result.reject("login.001");
			mv.setStatus(HttpStatus.BAD_REQUEST);
			return mv;
		}

		LoginForm loginForm = new LoginForm();
		BeanUtils.copyProperties(form, loginForm);
		redirectAttributes.addFlashAttribute("LoginForm", loginForm);

		mv.setView(new RedirectView(defaultRedirect.getRedirectUrl(request, response, "login")));
		return mv;
	}
}
