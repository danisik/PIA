package danisik.pia.web.controller.user;

import danisik.pia.Constants;
import danisik.pia.domain.User;
import danisik.pia.exceptions.ObjectNotFoundException;
import danisik.pia.exceptions.ParseIDException;
import danisik.pia.model.ChangePasswordObject;
import danisik.pia.service.role.RoleManager;
import danisik.pia.service.user.UserManager;
import danisik.pia.validators.UserValidator;
import danisik.pia.web.controller.BasicController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Controller for User with role admin.
 */
@Controller
public class AdminController extends BasicController {

	@Autowired
	private UserManager userManager;

	@Autowired
	private RoleManager roleManager;

	@Autowired
	private UserValidator userValidator;

	/**
	 * Bind user validator.
	 * @param binder Binder.
	 */
	@InitBinder("user")
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(this.userValidator);
	}

	/**
	 * Get mapping method for admin manage page.
	 * @return Model and view for admin manage page.
	 */
	@GetMapping("/admin/manage")
	public ModelAndView adminManageUsersGet() {
		ModelAndView modelAndView = new ModelAndView("admin/listUsersAdmin");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USERS, userManager.getUsers());

		return modelAndView;
	}

	/**
	 * Get mapping method for new user page.
	 * @return Model and view for new user page.
	 */
	@GetMapping("/admin/manage/user/new")
	public ModelAndView adminCreateUserGet() {
		ModelAndView modelAndView = new ModelAndView("admin/newUserAdmin");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, new User());
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_ROLES, roleManager.getRoles());

		return modelAndView;
	}


	/**
	 * Post mapping method for new user page.
	 * @param userValues Object representing user values.
	 * @param result Result from validator
	 * @return Model and view for new user page in case of errors, else info user page..
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@PostMapping("/admin/manage/user/new")
	public ModelAndView adminCreateUserPost(@Valid @ModelAttribute(Constants.ATTRIBUTE_NAME_USER) User userValues, BindingResult result) throws ObjectNotFoundException {
		ModelAndView modelAndView = new ModelAndView();

		ModelMap modelMap = modelAndView.getModelMap();

		if (result.hasErrors()) {
			modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, userValues);
			modelMap.addAttribute(Constants.ATTRIBUTE_NAME_ROLES, roleManager.getRoles());

			String message = getFullErrorMessage(result);

			modelMap.addAttribute(Constants.ATTRIBUTE_NAME_MESSAGE, message);
			modelAndView.setViewName("admin/newUserAdmin");
		}
		else {
			Long Id = userManager.addUser(userValues);
			modelAndView.setViewName("redirect:/admin/manage/user/info?id=" + Id);
			modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, this.userManager.findUserById(Id));
		}

		return modelAndView;
	}

	/**
	 * Get mapping method of info user admin.
	 * @param Id ID of user.
	 * @return Model and view for info user admin.
	 * @throws ParseIDException Exception if ID from url is not valid.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@GetMapping("/admin/manage/user/info")
	public ModelAndView adminInfoUserGet(@RequestParam(Constants.REQUEST_PARAM_ID) String Id) throws ParseIDException, ObjectNotFoundException {
		ModelAndView modelAndView = new ModelAndView("admin/infoUserAdmin");

		ModelMap modelMap = modelAndView.getModelMap();

		User user = userManager.findUserById(parseId(Id));

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, user);

		return modelAndView;
	}

	/**
	 * Post mapping method for Delete user.
	 * @param Id Id of User.
	 * @return Model and view for admin manage.
	 * @throws ParseIDException Exception if ID from url is not valid.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@PostMapping("/admin/manage/user/delete")
	public ModelAndView adminInfoUserPost(@RequestParam(value = Constants.REQUEST_PARAM_ID) String Id) throws ParseIDException, ObjectNotFoundException {
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/manage");

		userManager.deleteUser(parseId(Id));

		return modelAndView;
	}

	/**
	 * Get mapping method for edit user admin.
	 * @param Id ID of user.
	 * @param message Displayed message.
	 * @return Model and view of edit user admin.
	 * @throws ParseIDException Exception if ID from url is not valid.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@GetMapping("/admin/manage/user/edit")
	public ModelAndView adminEditUserGet(@RequestParam(Constants.REQUEST_PARAM_ID) String Id,
									@RequestParam(value = Constants.ATTRIBUTE_NAME_USER_SUCCESS_MESSAGE, required = false) String message) throws ParseIDException, ObjectNotFoundException {

		ModelAndView modelAndView = new ModelAndView("admin/editUserAdmin");

		ModelMap modelMap = modelAndView.getModelMap();

		User user = userManager.findUserById(parseId(Id));

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, user);
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_ROLES, roleManager.getRoles());

		return modelAndView;
	}

	/**
	 * Post mapping method for Edit user.
	 * @param userValues Object user containing user values.
	 * @param result Validation result.
	 * @param Id ID of user.
	 * @return Model and view of edit user page in case of no success, else return model and view of info user page.
	 * @throws ParseIDException Exception if ID from url is not valid.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@PostMapping("/admin/manage/user/edit")
	public ModelAndView adminEditUserPost(@Valid @ModelAttribute(Constants.ATTRIBUTE_NAME_USER) User userValues, BindingResult result,
									 @RequestParam(Constants.REQUEST_PARAM_ID) String Id) throws ParseIDException, ObjectNotFoundException {

		ModelAndView modelAndView = new ModelAndView();

		ModelMap modelMap = modelAndView.getModelMap();

		if (result.hasErrors()) {
			modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, userValues);
			modelMap.addAttribute(Constants.ATTRIBUTE_NAME_ROLES, roleManager.getRoles());

			String message = getFullErrorMessage(result);

			modelMap.addAttribute(Constants.ATTRIBUTE_NAME_MESSAGE, message);

			modelAndView.setViewName("admin/editUserAdmin");
		}
		else {
			User updateUser = userManager.findUserById(parseId(Id));

			userManager.updateUserInfo(updateUser.getUsername(), userValues);
			boolean success = userManager.updateUserRole(updateUser.getUsername(), userValues.getRole().getCode());

			if (!success) {
				modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER_SUCCESS_MESSAGE, Constants.ADMIN_EDIT_USER_ROLE_MESSAGE);
				modelAndView.setViewName("redirect:/admin/manage/user/edit?id=" + Id);
			}
			else {
				modelAndView.setViewName("redirect:/admin/manage/user/info?id=" + Id);
			}

			updateUser = userManager.findUserByUsername(updateUser.getUsername());

			modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, updateUser);
			modelMap.addAttribute(Constants.ATTRIBUTE_NAME_ROLES, roleManager.getRoles());
		}


		return modelAndView;
	}

	/**
	 * Get mapping method for changing password of user.
	 * @param Id ID of user.
	 * @return Model and view of password user admin.
	 * @throws ParseIDException Exception if ID from url is not valid.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@GetMapping("/admin/manage/user/password")
	public ModelAndView adminChangeUserPasswordGet(@RequestParam(Constants.REQUEST_PARAM_ID) String Id) throws ParseIDException, ObjectNotFoundException {

		ModelAndView modelAndView = new ModelAndView("admin/passwordUserAdmin");

		ModelMap modelMap = modelAndView.getModelMap();

		User user = userManager.findUserById(parseId(Id));

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, user);
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_CHANGE_PASSWORD_OBJECT, new ChangePasswordObject());

		return modelAndView;
	}

	/**
	 * Post mapping method for Change password of user.
	 * @param changePasswordObject Object changePasswordObject containing changePasswordObject values.
	 * @param Id User id.
	 * @return Model and view of password user admin.
	 * @throws ParseIDException Exception if ID from url is not valid.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@PostMapping("/admin/manage/user/password")
	public ModelAndView adminChangeUserPasswordPost(@Valid @ModelAttribute(Constants.ATTRIBUTE_NAME_CHANGE_PASSWORD_OBJECT) ChangePasswordObject changePasswordObject,
											   @RequestParam(Constants.REQUEST_PARAM_ID) String Id) throws ParseIDException, ObjectNotFoundException {

		ModelAndView modelAndView = new ModelAndView("admin/passwordUserAdmin");
		ModelMap modelMap = modelAndView.getModelMap();

		User updateUser = userManager.findUserById(parseId(Id));
		userManager.updatePassword(updateUser.getUsername(), changePasswordObject.getNewPassword());
		updateUser = userManager.findUserById(parseId(Id));

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER_SUCCESS_MESSAGE, Constants.USER_PASSWORD_CHANGE_MESSAGE);
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_CHANGE_PASSWORD_OBJECT, new ChangePasswordObject());
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_USER, updateUser);
		return modelAndView;
	}
}
