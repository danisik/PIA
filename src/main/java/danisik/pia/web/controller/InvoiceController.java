package danisik.pia.web.controller;

import danisik.pia.Constants;
import danisik.pia.domain.Contact;
import danisik.pia.domain.Invoice;
import danisik.pia.domain.InvoiceType;
import danisik.pia.service.ContactManager;
import danisik.pia.service.InvoiceManager;
import danisik.pia.service.InvoiceTypeManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.ParseException;

@Controller
public class InvoiceController {

	private InvoiceManager invoiceManager;
	private InvoiceTypeManager invoiceTypeManager;
	private ContactManager contactManager;

	public InvoiceController(InvoiceManager invoiceManager, InvoiceTypeManager invoiceTypeManager, ContactManager contactManager) {
		this.invoiceTypeManager = invoiceTypeManager;
		this.invoiceManager = invoiceManager;
		this.contactManager = contactManager;
	}

	@GetMapping("invoices/invoice/new")
	public ModelAndView invoicesInvoiceNewGet() {
		ModelAndView modelAndView = new ModelAndView("purser/invoice/newInvoice");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_INVOICE, new Invoice());
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_INVOICE_TYPES, invoiceTypeManager.getInvoiceTypes());
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_CONTACTS, contactManager.getContacts());

		return modelAndView;
	}

	@PostMapping("invoices/invoice/new")
	public ModelAndView invoicesInvoiceNewPost(@Valid @ModelAttribute(Constants.ATTRIBUTE_NAME_INVOICE) Invoice invoiceValues)
												throws ParseException {
		ModelAndView modelAndView = new ModelAndView();

		ModelMap modelMap = modelAndView.getModelMap();

		Long Id = this.invoiceManager.addInvoice(invoiceValues);
		modelAndView.setViewName("redirect:/invoices/invoice/info?id=" + Id);

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_INVOICE, this.invoiceManager.findInvoiceByID(Id));

		return modelAndView;
	}

	@GetMapping("invoices/invoice/info")
	public ModelAndView invoicesInvoiceInfo(@RequestParam(value = Constants.REQUEST_PARAM_ID) Long Id) {
		ModelAndView modelAndView = new ModelAndView("purser/invoice/infoInvoice");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_INVOICE, invoiceManager.findInvoiceByID(Id));

		return modelAndView;
	}

	@GetMapping("invoices/invoice/edit")
	public ModelAndView invoicesInvoiceEditGet(@RequestParam(value = Constants.REQUEST_PARAM_ID) Long Id) {
		ModelAndView modelAndView = new ModelAndView("purser/invoice/editInvoice");

		ModelMap modelMap = modelAndView.getModelMap();

		Invoice invoice = invoiceManager.findInvoiceByID(Id);

		if (invoice.getCancelled()) {
			modelMap.addAttribute(Constants.ATTRIBUTE_NAME_INVOICE_CANCELLED_MESSAGE, Constants.INVOICE_CANCELLED_MESSAGE);
		}

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_INVOICE, invoice);
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_INVOICE_TYPES, invoiceTypeManager.getInvoiceTypes());
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_CONTACTS, contactManager.getContacts());

		return modelAndView;
	}

	@PostMapping("invoices/invoice/edit")
	public ModelAndView invoicesInvoiceEditPost(@RequestParam(value = Constants.REQUEST_PARAM_ID) Long Id,
												@Valid @ModelAttribute(Constants.ATTRIBUTE_NAME_INVOICE) Invoice invoiceValues)
												throws ParseException {
		ModelAndView modelAndView = new ModelAndView("redirect:/invoices/invoice/info?id="+ Id);
		invoiceManager.updateInvoice(Id, invoiceValues);
		return modelAndView;
	}

	@GetMapping("/invoices/info")
	public ModelAndView invoiceInfoGet() {
		ModelAndView modelAndView = new ModelAndView("purser/invoice/infoListInvoices");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_INVOICES, invoiceManager.getInvoices());
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_INVOICES_INVOICE_ID, 0);

		return modelAndView;
	}

	@PostMapping("/invoices/info")
	public ModelAndView invoiceInfoPost(@RequestParam(value= Constants.ATTRIBUTE_NAME_INVOICES_INVOICE_ID, required=true) Long Id,
										@RequestParam(value=Constants.ATTRIBUTE_NAME_INVOICES_BUTTON_NAME, required=true) String action) {

		ModelAndView modelAndView = new ModelAndView("purser/invoice/infoListInvoices");

		ModelMap modelMap = modelAndView.getModelMap();

		if (action.equals(Constants.INVOICE_CANCEL_STRING)) {
			invoiceManager.updateInvoiceCancelled(Id);
		}

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_INVOICES, invoiceManager.getInvoices());

		return modelAndView;
	}
}
