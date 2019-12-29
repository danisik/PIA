package danisik.pia.web.controller;

import danisik.pia.domain.User;
import danisik.pia.service.InvoiceManager;
import danisik.pia.service.UserManager;
import org.dom4j.rule.Mode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InvoiceController {

	private InvoiceManager invoiceManager;

	public InvoiceController(InvoiceManager invoiceManager) {
		this.invoiceManager = invoiceManager;
	}

	@GetMapping("invoices/invoice/new")
	public ModelAndView invoicesInvoiceNew() {
		ModelAndView modelAndView = new ModelAndView("purser/invoice/newInvoice");

		return modelAndView;
	}

	@GetMapping("invoices/invoice/info")
	public ModelAndView invoicesInvoiceInfo() {
		ModelAndView modelAndView = new ModelAndView("purser/invoice/infoInvoice");

		return modelAndView;
	}

	@GetMapping("invoices/invoice/edit")
	public ModelAndView invoicesInvoiceEditGet() {
		ModelAndView modelAndView = new ModelAndView("purser/invoice/editInvoice");

		return modelAndView;
	}

	@PostMapping("invoices/invoice/edit")
	public ModelAndView invoicesInvoiceEditPost() {
		ModelAndView modelAndView = new ModelAndView("purser/invoice/editInvoice");

		return modelAndView;
	}

	@GetMapping("/invoices/info")
	public ModelAndView invoiceInfo() {
		ModelAndView modelAndView = new ModelAndView("purser/invoice/infoListInvoices");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute("invoices", invoiceManager.getInvoices());

		return modelAndView;
	}
}
