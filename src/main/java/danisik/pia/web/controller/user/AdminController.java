package danisik.pia.web.controller.user;

import danisik.pia.Constants;
import danisik.pia.domain.User;
import danisik.pia.exceptions.ObjectNotFoundException;
import danisik.pia.exceptions.ParseIDException;
import danisik.pia.service.RoleManager;
import danisik.pia.service.user.UserManager;
import danisik.pia.web.controller.BasicController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AdminController extends BasicController {

	@Autowired
	private UserManager userManager;

	@Autowired
	private RoleManager roleManager;

	@GetMapping("/admin/manage")
	public ModelAndView adminManageUsersGet() {
		ModelAndView modelAndView = new ModelAndView("admin/listUsersAdmin");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USERS, userManager.getUsers());

		return modelAndView;
	}

	@GetMapping("/admin/manage/user/new")
	public ModelAndView adminCreateUserGet() {
		ModelAndView modelAndView = new ModelAndView("admin/newUserAdmin");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, new User());
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_ROLES, roleManager.getRoles());

		return modelAndView;
	}


	@PostMapping("/admin/manage/user/new")
	public ModelAndView adminCreateUserPost(@Valid @ModelAttribute(Constants.ATTRIBUTE_NAME_USER) User userValues) throws ObjectNotFoundException {
		ModelAndView modelAndView = new ModelAndView();

		ModelMap modelMap = modelAndView.getModelMap();

		Long Id = userManager.addUser(userValues);

		modelAndView.setViewName("redirect:/admin/manage/user/info?id=" + Id);

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, this.userManager.findUserById(Id));

		return modelAndView;
	}

	@GetMapping("/admin/manage/user/info")
	public ModelAndView adminInfoUserGet(@RequestParam(Constants.REQUEST_PARAM_ID) String Id) throws ParseIDException, ObjectNotFoundException {
		ModelAndView modelAndView = new ModelAndView("admin/infoUserAdmin");

		ModelMap modelMap = modelAndView.getModelMap();

		User user = userManager.findUserById(parseId(Id));

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, user);

		return modelAndView;
	}

	@PostMapping("/admin/manage/user/delete")
	public ModelAndView adminInfoUserPost(@RequestParam(value = Constants.REQUEST_PARAM_ID) String Id) throws ParseIDException, ObjectNotFoundException {
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/manage");

		userManager.deleteUser(parseId(Id));

		return modelAndView;
	}

	@GetMapping("/admin/manage/user/edit")
	public ModelAndView userEditGet(@RequestParam(Constants.REQUEST_PARAM_ID) String Id,
									@RequestParam(value = Constants.ATTRIBUTE_NAME_USER_SUCCESS_MESSAGE, required = false) String message) throws ParseIDException, ObjectNotFoundException {

		ModelAndView modelAndView = new ModelAndView("admin/editUserAdmin");

		ModelMap modelMap = modelAndView.getModelMap();

		User user = userManager.findUserById(parseId(Id));

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, user);
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_ROLES, roleManager.getRoles());
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER_SUCCESS_MESSAGE, message);

		return modelAndView;
	}

	@PostMapping("/admin/manage/user/edit")
	public ModelAndView userEditPost(@Valid @ModelAttribute(Constants.ATTRIBUTE_NAME_USER) User userValues,
									 @RequestParam(Constants.REQUEST_PARAM_ID) String Id) throws ParseIDException, ObjectNotFoundException {

		ModelAndView modelAndView = new ModelAndView("redirect:/admin/manage/user/info?id=" + Id);

		ModelMap modelMap = modelAndView.getModelMap();

		User updateUser = userManager.findUserById(parseId(Id));

		userManager.updateUserInfo(updateUser.getUsername(), userValues);
		boolean success = userManager.updateUserRole(updateUser.getUsername(), userValues.getRole().getCode());

		if (!success) {
			modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER_SUCCESS_MESSAGE, Constants.ADMIN_EDIT_USER_ROLE_MESSAGE);
			modelAndView.setViewName("redirect:/admin/manage/user/edit?id=" + Id);
		}

		updateUser = userManager.findUserByUsername(updateUser.getUsername());

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, updateUser);
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_ROLES, roleManager.getRoles());

		return modelAndView;
	}

	@GetMapping("/admin/manage/user/password")
	public ModelAndView userChangePasswordGet(@RequestParam(Constants.REQUEST_PARAM_ID) String Id) throws ParseIDException, ObjectNotFoundException {

		ModelAndView modelAndView = new ModelAndView("admin/passwordUserAdmin");

		ModelMap modelMap = modelAndView.getModelMap();

		User user = userManager.findUserById(parseId(Id));

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, user);

		return modelAndView;
	}

	@PostMapping("/admin/manage/user/password")
	public ModelAndView userChangePasswordPost(@RequestParam(Constants.REQUEST_PARAM_USER_NEW_PASSWORD) String newPassword,
											   @RequestParam(Constants.REQUEST_PARAM_ID) String Id) throws ParseIDException, ObjectNotFoundException {

		ModelAndView modelAndView = new ModelAndView("admin/passwordUserAdmin");
		ModelMap modelMap = modelAndView.getModelMap();

		User updateUser = userManager.findUserById(parseId(Id));
		userManager.updatePassword(updateUser.getUsername(), newPassword);
		updateUser = userManager.findUserById(parseId(Id));

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER_SUCCESS_MESSAGE, Constants.USER_PASSWORD_CHANGE_MESSAGE);
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, updateUser);
		return modelAndView;
	}
}
