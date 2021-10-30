package com.github.tbquyen.quizreport;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.tbquyen.config.BaseController;
import com.github.tbquyen.entity.Quiz;

@Controller
@RequestMapping(value = "/quizreport")
public class QuizReportController extends BaseController {
	private static final Logger LOGGER = LogManager.getLogger(QuizReportController.class);
	@Autowired
	private QuizReportService service;

	@GetMapping
	public ModelAndView get(ModelMap modelMap) {
		LOGGER.debug("GET: quizreport");

		List<Quiz> quizs = service.getReport();
		modelMap.addAttribute("quizs", quizs);

		return new ModelAndView("quizreport");
	}
}
