package danisik.pia.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Login controller.
 */
@Controller
public class LoginController extends BasicController {

	/**
	 * Get mapping method for login page.
	 * @return Model and view for login page.
	 */
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView("homepage/loginHomepage");

		return modelAndView;
	}
}
