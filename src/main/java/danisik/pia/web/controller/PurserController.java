package danisik.pia.web.controller;

import danisik.pia.domain.User;
import danisik.pia.service.UserManager;
import org.dom4j.rule.Mode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PurserController {

	private UserManager userManager;

	public PurserController(UserManager userManager) {
		this.userManager = userManager;
	}

	@GetMapping("/addressbook/info")
	public ModelAndView addressBookInfo() {
		ModelAndView modelAndView = new ModelAndView("purser/addressbook/infoContact");

		ModelMap modelMap = modelAndView.getModelMap();

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userManager.findUserByUsername(username);

		modelMap.addAttribute("contacts", user.getContacts());

		return modelAndView;
	}

	@GetMapping("/addressbook/new")
	public ModelAndView addressBookNew() {
		ModelAndView modelAndView = new ModelAndView("purser/addressbook/newContact");

		return modelAndView;
	}

	@GetMapping("invoice/new")
	public ModelAndView invoiceNew() {
		ModelAndView modelAndView = new ModelAndView("purser/invoice/newInvoice");

		return modelAndView;
	}

	@GetMapping("/invoice/edit")
	public ModelAndView invoiceEdit() {
		ModelAndView modelAndView = new ModelAndView("purser/invoice/editInvoice");

		return modelAndView;
	}

	@GetMapping("/invoice/info")
	public ModelAndView invoiceInfo() {
		ModelAndView modelAndView = new ModelAndView("purser/invoice/infoInvoice");

		ModelMap modelMap = modelAndView.getModelMap();

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userManager.findUserByUsername(username);

		modelMap.addAttribute("invoices", user.getInvoices());

		return modelAndView;
	}
}
