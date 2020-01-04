package danisik.pia.web.controller.user;

import danisik.pia.Constants;
import danisik.pia.domain.User;
import danisik.pia.exceptions.ObjectNotFoundException;
import danisik.pia.service.user.UserManager;
import danisik.pia.validators.UserValidator;
import danisik.pia.web.controller.BasicController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController extends BasicController {

	@Autowired
	private UserManager userManager;

	@Autowired
	private UserValidator userValidator;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(this.userValidator);
	}

	@GetMapping("/user/edit")
	public ModelAndView userEditGet() throws ObjectNotFoundException {

		ModelAndView modelAndView = new ModelAndView("user/editUser");

		ModelMap modelMap = modelAndView.getModelMap();

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userManager.findUserByUsername(username);

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, user);
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_ROLES, user);

		return modelAndView;
	}

	@PostMapping("/user/edit")
	public ModelAndView userEditPost(@Valid @ModelAttribute(Constants.ATTRIBUTE_NAME_USER) User userValues, BindingResult result) throws ObjectNotFoundException {

		ModelAndView modelAndView = new ModelAndView();

		ModelMap modelMap = modelAndView.getModelMap();

		if (result.hasErrors()) {
			modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, userValues);

			String message = getFullErrorMessage(result);

			modelMap.addAttribute(Constants.ATTRIBUTE_NAME_MESSAGE, message);

			modelAndView.setViewName("user/editUser");
		}
		else {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			userManager.updateUserInfo(username, userValues);
			User user = userManager.findUserByUsername(username);
			modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, user);
			modelAndView.setViewName("user/infoUser");
		}

		return modelAndView;
	}

	@GetMapping("/user/info")
	public ModelAndView userInfo() throws ObjectNotFoundException {

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
											   @RequestParam(Constants.REQUEST_PARAM_USER_NEW_PASSWORD_CONFIRMATION) String newPasswordConfirmation) throws ObjectNotFoundException {

		ModelAndView modelAndView = new ModelAndView("user/passwordUser");
		ModelMap modelMap = modelAndView.getModelMap();

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		userManager.updatePassword(username, oldPassword, newPassword, newPasswordConfirmation);

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER_SUCCESS_MESSAGE, Constants.USER_PASSWORD_CHANGE_MESSAGE);
		return modelAndView;
	}


}