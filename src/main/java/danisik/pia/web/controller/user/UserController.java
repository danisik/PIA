package danisik.pia.web.controller.user;

import danisik.pia.Constants;
import danisik.pia.domain.User;
import danisik.pia.service.UserManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

	private UserManager userManager;

	public UserController(UserManager userManager) {
		this.userManager = userManager;
	}

	@GetMapping("/user/edit")
	public ModelAndView userEditGet() {

		ModelAndView modelAndView = new ModelAndView("user/editUser");

		ModelMap modelMap = modelAndView.getModelMap();

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userManager.findUserByUsername(username);

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, user);

		return modelAndView;
	}

	@PostMapping("/user/edit")
	public ModelAndView userEditPost(@Valid @ModelAttribute(Constants.ATTRIBUTE_NAME_USER) User userValues) {

		ModelAndView modelAndView = new ModelAndView("user/infoUser");

		ModelMap modelMap = modelAndView.getModelMap();

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		userManager.updateUserInfo(username, userValues);
		User user = userManager.findUserByUsername(username);
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, user);

		return modelAndView;
	}

	@GetMapping("/user/info")
	public ModelAndView userInfo() {

		ModelAndView modelAndView = new ModelAndView("user/infoUser");
		ModelMap modelMap = modelAndView.getModelMap();

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userManager.findUserByUsername(username);

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, user);

		return modelAndView;
	}

	@GetMapping("/user/password")
	public ModelAndView userChangePasswordGet() {

		ModelAndView modelAndView = new ModelAndView("user/passwordUser");

		return modelAndView;
	}

	@PostMapping("/user/password")
	public ModelAndView userChangePasswordPost(@RequestParam(Constants.REQUEST_PARAM_USER_OLD_PASSWORD) String oldPassword,
											   @RequestParam(Constants.REQUEST_PARAM_USER_NEW_PASSWORD) String newPassword,
											   @RequestParam(Constants.REQUEST_PARAM_USER_NEW_PASSWORD_CONFIRMATION) String newPasswordConfirmation) {

		ModelAndView modelAndView = new ModelAndView("user/passwordUser");
		ModelMap modelMap = modelAndView.getModelMap();

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		userManager.updatePassword(username, oldPassword, newPassword, newPasswordConfirmation);

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER_SUCCESS_MESSAGE, Constants.USER_PASSWORD_CHANGE_MESSAGE);
		return modelAndView;
	}


}