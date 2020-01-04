package danisik.pia.web.controller.user;

import danisik.pia.Constants;
import danisik.pia.domain.User;
import danisik.pia.exceptions.ObjectNotFoundException;
import danisik.pia.model.ChangePasswordObject;
import danisik.pia.service.user.UserManager;
import danisik.pia.validators.PasswordValidator;
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

	@Autowired
	private PasswordValidator passwordValidator;

	@InitBinder("user")
	protected void userBinder(WebDataBinder binder) {
		binder.addValidators(this.userValidator);
	}

	@InitBinder("changePasswordObject")
	protected void passwordBinder(WebDataBinder binder) {
		binder.addValidators(this.passwordValidator);
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
		ModelMap modelMap = modelAndView.getModelMap();
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_CHANGE_PASSWORD_OBJECT, new ChangePasswordObject());

		return modelAndView;
	}

	@PostMapping("/user/password")
	public ModelAndView userChangePasswordPost(@Valid @ModelAttribute(Constants.ATTRIBUTE_NAME_CHANGE_PASSWORD_OBJECT) ChangePasswordObject changePasswordObject,
											   BindingResult result) throws ObjectNotFoundException {

		ModelAndView modelAndView = new ModelAndView("user/passwordUser");
		ModelMap modelMap = modelAndView.getModelMap();

		if (result.hasErrors()) {
			String message = getFullErrorMessage(result);
			modelMap.addAttribute(Constants.ATTRIBUTE_NAME_MESSAGE, message);
		}
		else {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			userManager.updatePassword(username, changePasswordObject.getNewPassword());

			modelMap.addAttribute(Constants.ATTRIBUTE_NAME_MESSAGE, Constants.USER_PASSWORD_CHANGE_MESSAGE);
		}
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_CHANGE_PASSWORD_OBJECT, new ChangePasswordObject());
		return modelAndView;
	}

}