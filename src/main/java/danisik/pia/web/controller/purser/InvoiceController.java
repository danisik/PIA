package danisik.pia.web.controller.purser;

import danisik.pia.Constants;
import danisik.pia.domain.Invoice;
import danisik.pia.exceptions.ObjectNotFoundException;
import danisik.pia.exceptions.ParseIDException;
import danisik.pia.service.purser.ContactManager;
import danisik.pia.service.purser.InvoiceManager;
import danisik.pia.service.purser.InvoiceTypeManager;
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
import java.text.ParseException;
import java.util.List;

@Controller
public class InvoiceController extends BasicController {

	@Autowired
	private InvoiceManager invoiceManager;

	@Autowired
	private InvoiceTypeManager invoiceTypeManager;

	@Autowired
	private ContactManager contactManager;

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
			throws ParseException, ObjectNotFoundException {
		ModelAndView modelAndView = new ModelAndView();

		ModelMap modelMap = modelAndView.getModelMap();

		Long Id = this.invoiceManager.addInvoice(invoiceValues);
		modelAndView.setViewName("redirect:/invoices/invoice/info?id=" + Id);

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_INVOICE, this.invoiceManager.findInvoiceByID(Id));

		return modelAndView;
	}

	@GetMapping("invoices/invoice/info")
	public ModelAndView invoicesInvoiceInfo(@RequestParam(value = Constants.REQUEST_PARAM_ID) String Id) throws ParseIDException, ObjectNotFoundException {
		ModelAndView modelAndView = new ModelAndView("purser/invoice/infoInvoice");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_INVOICE, invoiceManager.findInvoiceByID(parseId(Id)));
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_WARES, invoiceManager.findInvoiceByID(parseId(Id)).getWares());

		return modelAndView;
	}

	@GetMapping("invoices/invoice/edit")
	public ModelAndView invoicesInvoiceEditGet(@RequestParam(value = Constants.REQUEST_PARAM_ID) String Id) throws ParseIDException, ObjectNotFoundException {
		ModelAndView modelAndView = new ModelAndView("purser/invoice/editInvoice");

		ModelMap modelMap = modelAndView.getModelMap();

		Invoice invoice = invoiceManager.findInvoiceByID(parseId(Id));

		if (invoice.getCancelled()) {
			modelMap.addAttribute(Constants.ATTRIBUTE_NAME_MESSAGE, Constants.INVOICE_CANCELLED_MESSAGE);
		}

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_INVOICE, invoice);
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_INVOICE_TYPES, invoiceTypeManager.getInvoiceTypes());
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_CONTACTS, contactManager.getContacts());


		return modelAndView;
	}

	@PostMapping("invoices/invoice/edit")
	public ModelAndView invoicesInvoiceEditPost(@RequestParam(value = Constants.REQUEST_PARAM_ID) String Id,
												@Valid @ModelAttribute(Constants.ATTRIBUTE_NAME_INVOICE) Invoice invoiceValues)
			throws ParseException, ParseIDException, ObjectNotFoundException {
		ModelAndView modelAndView = new ModelAndView("redirect:/invoices/invoice/info?id="+ Id);
		System.out.println(invoiceValues.toString());
		invoiceManager.updateInvoice(parseId(Id), invoiceValues);
		return modelAndView;
	}

	@GetMapping("/invoices/info")
	public ModelAndView invoicesInfoGet() {
		ModelAndView modelAndView = new ModelAndView("purser/invoice/infoListInvoices");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_INVOICES, invoiceManager.getInvoices());
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_INVOICE_TYPES, invoiceTypeManager.getInvoiceTypes());
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_INVOICES_INVOICE_ID, 0);

		return modelAndView;
	}

	@PostMapping("/invoices/info")
	public ModelAndView invoicesInfoPost(@RequestParam(value= Constants.ATTRIBUTE_NAME_INVOICES_INVOICE_ID, required=false) String Id,
										@RequestParam(value=Constants.ATTRIBUTE_NAME_INVOICES_BUTTON_NAME, required=true) String action) throws ParseIDException, ObjectNotFoundException {

		ModelAndView modelAndView = new ModelAndView("purser/invoice/infoListInvoices");

		ModelMap modelMap = modelAndView.getModelMap();

		List<Invoice> invoices = null;

		switch(action) {
			case Constants.INVOICE_CANCEL_STRING:
				invoiceManager.updateInvoiceCancelled(parseId(Id));
				invoices = invoiceManager.getInvoices();
				break;
		}

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_INVOICES, invoices);

		return modelAndView;
	}
}
