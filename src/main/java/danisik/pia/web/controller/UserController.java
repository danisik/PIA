package danisik.pia.web.controller;

import danisik.pia.domain.User;
import danisik.pia.service.UserManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

	private UserManager userManager;

	private static final String ATTRIBUTE_NAME_USER = "user";

	public UserController(UserManager userManager) {
		this.userManager = userManager;
	}

	@GetMapping("/user/edit")
	public ModelAndView userEditGet() {

		ModelAndView modelAndView = new ModelAndView("user/editUser");

		ModelMap modelMap = modelAndView.getModelMap();

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userManager.findUserByUsername(username);

		modelMap.addAttribute(ATTRIBUTE_NAME_USER, user);

		return modelAndView;
	}

	@PostMapping("/user/edit")
	public ModelAndView userEditPost(@Valid @ModelAttribute(ATTRIBUTE_NAME_USER) User userValues) {

		ModelAndView modelAndView = new ModelAndView("user/infoUser");

		ModelMap modelMap = modelAndView.getModelMap();

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User updatedUser = userManager.updateUserInfo(username, userValues);
		modelMap.addAttribute(ATTRIBUTE_NAME_USER, updatedUser);

		return modelAndView;
	}

	@GetMapping("/user/info")
	public ModelAndView userInfo() {

		ModelAndView modelAndView = new ModelAndView("user/infoUser");
		ModelMap modelMap = modelAndView.getModelMap();

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userManager.findUserByUsername(username);

		modelMap.addAttribute(ATTRIBUTE_NAME_USER, user);

		return modelAndView;
	}

	@GetMapping("/user/password")
	public ModelAndView userChangePassword() {

		ModelAndView modelAndView = new ModelAndView("user/infoUser");
		ModelMap modelMap = modelAndView.getModelMap();

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userManager.findUserByUsername(username);

		modelMap.addAttribute(ATTRIBUTE_NAME_USER, user);

		return modelAndView;
	}
}
