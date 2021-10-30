package com.github.tbquyen.quiz;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.tbquyen.categories.CategoriesService;
import com.github.tbquyen.config.BaseController;
import com.github.tbquyen.entity.CategoriesMaster;
import com.github.tbquyen.entity.QuestionsMaster;
import com.github.tbquyen.entity.Quiz;
import com.github.tbquyen.entity.QuizInfo;

@Controller
@RequestMapping(value = "/quiz")
public class QuizController extends BaseController {
	private static final Logger LOGGER = LogManager.getLogger(QuizController.class);
	@Autowired
	private CategoriesService categoriesService;
	@Autowired
	private QuizService service;
	@Autowired
	private MessageSource messageSource;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null)
			return;
	}

	@GetMapping
	public ModelAndView get(ModelMap modelMap, @ModelAttribute(QuizForm.NAME) QuizForm form) {
		LOGGER.debug("GET: quiz");
		loadRedirectErrors(modelMap, QuizForm.NAME, QuizForm.VALIDATOR);

		List<CategoriesMaster> categories = categoriesService.getCategories();
		modelMap.addAttribute("categories", categories);

		return new ModelAndView("quiz1");
	}

	@PostMapping
	public ModelAndView post(ModelMap modelMap, @ModelAttribute(QuizForm.NAME) QuizForm form,
			RedirectAttributes redirectAttributes) {
		LOGGER.debug("POST: quiz");
		loadRedirectErrors(modelMap, QuizForm.NAME, QuizForm.VALIDATOR);

		int id = service.createQuiz(form.getCategoryId(), form.getNumberofquestions());

		if (id == 0) {
			redirectAttributes.addFlashAttribute(ERROR,
					messageSource.getMessage("ERROR.003", new String[] { "Quiz" }, null));
			return new ModelAndView("redirect:/quiz");
		}

		return new ModelAndView("redirect:/quiz/" + id + "/1");
	}

	@GetMapping(value = "/{id}/{index}")
	public ModelAndView get(ModelMap modelMap, @PathVariable(name = "id") String id,
			@PathVariable(name = "index") String index, @ModelAttribute(QuizForm.NAME) QuizForm form,
			RedirectAttributes redirectAttributes) {
		LOGGER.debug("TEST: quiz, quiz=" + id + ", index=" + index);
		loadRedirectErrors(modelMap, QuizForm.NAME, QuizForm.VALIDATOR);
		ModelAndView mv = new ModelAndView("quiz2");

		QuizInfo quizinfo = service.getQuizinfo(id, index);
		if (quizinfo == null) {
			redirectAttributes.addFlashAttribute(ERROR,
					messageSource.getMessage("ERROR.003", new String[] { "Question" }, null));
			mv.setViewName("redirect:/quiz");
			return mv;
		}

		QuestionsMaster question = quizinfo.getQuestion();
		Quiz quiz = quizinfo.getQuiz();

		if (quiz.getTimeEnd() != null) {
			modelMap.addAttribute("total", quiz.getQuestions());
			modelMap.addAttribute("passed", quiz.getPassed());
			modelMap.addAttribute("duration", quiz.getDuration());

			mv.setViewName("quiz3");
		}

		form.setAnser(quizinfo.getAnser());
		form.setTotal(String.valueOf(quiz.getQuestions()));

		modelMap.addAttribute("question", question);
		modelMap.addAttribute("id", id);
		modelMap.addAttribute("index", index);
		modelMap.addAttribute("total", quiz.getQuestions());
		modelMap.addAttribute("timeStart", quiz.getTimeStart());
		modelMap.addAttribute("duration", quiz.getDuration());

		int nextIndex = NumberUtils.toInt(index) + 1;
		if (nextIndex <= quiz.getQuestions()) {
			modelMap.addAttribute("nextIndex", NumberUtils.toInt(index) + 1);
		}

		int previousIndex = NumberUtils.toInt(index) - 1;
		if (previousIndex > 0) {
			modelMap.addAttribute("previousIndex", previousIndex);
		}

		return mv;
	}

	@PostMapping(value = "/{id}/{index}")
	public ModelAndView post(ModelMap modelMap, @PathVariable(name = "id") String id,
			@PathVariable(name = "index") String index, @ModelAttribute(QuizForm.NAME) QuizForm form,
			RedirectAttributes redirectAttributes) {
		LOGGER.debug("TEST: quiz, quiz=" + id + ", index=" + index);
		loadRedirectErrors(modelMap, QuizForm.NAME, QuizForm.VALIDATOR);
		ModelAndView mv = new ModelAndView();

		if (StringUtils.isEmpty(form.getEnd()) && StringUtils.isEmpty(form.getAnser())) {
			redirectAttributes.addFlashAttribute(ERROR,
					messageSource.getMessage("ERROR.001", new String[] { "Anser" }, null));
			mv.setViewName("redirect:/quiz/" + id + "/" + index);
		}

		if (!StringUtils.isEmpty(form.getAnser())) {
			int result = service.anser(id, index, form.getAnser());

			if (result == -1) {
				redirectAttributes.addFlashAttribute(ERROR,
						messageSource.getMessage("ERROR.003", new String[] { "Quiz" }, null));
				return new ModelAndView("redirect:/quiz");
			}

			int nextIndex = NumberUtils.toInt(index);

			if (!StringUtils.isEmpty(form.getNext()) && nextIndex < NumberUtils.toInt(form.getTotal())) {
				nextIndex++;
			}

			if (!StringUtils.isEmpty(form.getPrevious()) && nextIndex > 1) {
				nextIndex--;
			}

			mv.setViewName("redirect:/quiz/" + id + "/" + nextIndex);
		}

		if (!StringUtils.isEmpty(form.getEnd())) {
			Quiz quiz = service.end(id);

			modelMap.addAttribute("total", quiz.getQuestions());
			modelMap.addAttribute("passed", quiz.getPassed());
			modelMap.addAttribute("duration", quiz.getDuration());

			mv.setViewName("quiz3");
		}

		return mv;
	}
}
