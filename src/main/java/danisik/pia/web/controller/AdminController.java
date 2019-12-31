package danisik.pia.web.controller;

import danisik.pia.Constants;
import danisik.pia.domain.User;
import danisik.pia.service.UserManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AdminController {

	private UserManager userManager;

	public AdminController(UserManager userManager) {
		this.userManager = userManager;
	}

	@GetMapping("/admin/manage")
	public ModelAndView adminManageUsersGet() {
		ModelAndView modelAndView = new ModelAndView("admin/manageUsersAdmin");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USERS, userManager.getUsers());

		return modelAndView;
	}

	@PostMapping("/admin/manage")
	public ModelAndView adminManageUsersPost() {
		ModelAndView modelAndView = new ModelAndView("admin/manageUsersAdmin");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USERS, userManager.getUsers());

		return modelAndView;
	}

	@GetMapping("/admin/manage/user/new")
	public ModelAndView adminCreateUserGet() {
		ModelAndView modelAndView = new ModelAndView("admin/createUserAdmin");

		return modelAndView;
	}


	@PostMapping("/admin/manage/user/new")
	public ModelAndView adminCreateUserPost() {
		ModelAndView modelAndView = new ModelAndView("admin/manageUsersAdmin");

		return modelAndView;
	}

	@GetMapping("/admin/manage/user/info")
	public ModelAndView adminInfoUser(@RequestParam(Constants.REQUEST_PARAM_ID) Long Id) {
		ModelAndView modelAndView = new ModelAndView("admin/infoUserAdmin");

		ModelMap modelMap = modelAndView.getModelMap();

		User user = userManager.findUserById(Id);

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, user);

		return modelAndView;
	}

	@GetMapping("/admin/manage/user/edit")
	public ModelAndView userEditGet(@RequestParam(Constants.REQUEST_PARAM_ID) Long Id) {

		ModelAndView modelAndView = new ModelAndView("admin/editUserAdmin");

		ModelMap modelMap = modelAndView.getModelMap();

		User user = userManager.findUserById(Id);

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, user);

		return modelAndView;
	}

	@PostMapping("/admin/manage/user/edit")
	public ModelAndView userEditPost(@Valid @ModelAttribute(Constants.ATTRIBUTE_NAME_USER) User userValues,
									 @RequestParam(Constants.REQUEST_PARAM_ID) Long Id) {

		ModelAndView modelAndView = new ModelAndView("redirect:/admin/manage/user/info?id=" + Id);

		ModelMap modelMap = modelAndView.getModelMap();

		User updateUser = userManager.findUserById(Id);
		userManager.updateUserInfo(updateUser.getUsername(), userValues);
		updateUser = userManager.findUserByUsername(updateUser.getUsername());
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, updateUser);

		return modelAndView;
	}

	@GetMapping("/admin/manage/user/password")
	public ModelAndView userChangePasswordGet(@RequestParam(Constants.REQUEST_PARAM_ID) Long Id) {

		ModelAndView modelAndView = new ModelAndView("admin/passwordUserAdmin");

		ModelMap modelMap = modelAndView.getModelMap();

		User user = userManager.findUserById(Id);

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, user);

		return modelAndView;
	}

	@PostMapping("/admin/manage/user/password")
	public ModelAndView userChangePasswordPost(@RequestParam(Constants.REQUEST_PARAM_USER_NEW_PASSWORD) String newPassword,
											   @RequestParam(Constants.REQUEST_PARAM_ID) Long Id) {

		ModelAndView modelAndView = new ModelAndView("admin/passwordUserAdmin");
		ModelMap modelMap = modelAndView.getModelMap();

		User updateUser = userManager.findUserById(Id);
		userManager.updatePassword(updateUser.getUsername(), newPassword);
		updateUser = userManager.findUserById(Id);

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER_SUCCESS_MESSAGE, Constants.USER_PASSWORD_CHANGE_MESSAGE);
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, updateUser);
		return modelAndView;
	}
}
