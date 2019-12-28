package danisik.pia.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("index");

		return modelAndView;
	}

	@GetMapping("/info")
	public ModelAndView info() {
		ModelAndView modelAndView = new ModelAndView("homepage/infoHomepage");

		return modelAndView;
	}

	@GetMapping("/services")
	public ModelAndView services() {
		ModelAndView modelAndView = new ModelAndView("homepage/servicesHomepage");

		return modelAndView;
	}
}
