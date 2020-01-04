package danisik.pia.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Index controller.
 */
@Controller
public class IndexController extends BasicController {

	/**
	 * Get mapping method for index.
	 * @return Model and view for index.
	 */
	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("index");

		return modelAndView;
	}

	/**
	 * Get mapping method for info page.
	 * @return Model and view for info page.
	 */
	@GetMapping("/info")
	public ModelAndView info() {
		ModelAndView modelAndView = new ModelAndView("homepage/infoHomepage");

		return modelAndView;
	}

	/**
	 * Get mapping method for services page.
	 * @return Model and view for services page.
	 */
	@GetMapping("/services")
	public ModelAndView services() {
		ModelAndView modelAndView = new ModelAndView("homepage/servicesHomepage");

		return modelAndView;
	}
}
