package danisik.pia.web.controller.purser;

import danisik.pia.Constants;
import danisik.pia.domain.Contact;
import danisik.pia.exceptions.ObjectNotFoundException;
import danisik.pia.exceptions.ParseIDException;
import danisik.pia.service.purser.ContactManager;
import danisik.pia.service.user.UserManager;
import danisik.pia.web.controller.BasicController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.MissingFormatArgumentException;

@Controller
public class ContactController extends BasicController {

	@Autowired
	private ContactManager contactManager;

	@GetMapping("/addressbook/info")
	public ModelAndView addressBookInfo() {
		ModelAndView modelAndView = new ModelAndView("purser/addressbook/infoListContacts");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_CONTACTS, contactManager.getContacts());

		return modelAndView;
	}

	@GetMapping("/addressbook/contact/info")
	public ModelAndView addressBookContactInfoGet(@RequestParam(value = Constants.REQUEST_PARAM_ID) String Id,
												  @RequestParam(value = Constants.ATTRIBUTE_NAME_MESSAGE, required = false) String message) throws ParseIDException, ObjectNotFoundException, MissingServletRequestParameterException {
		ModelAndView modelAndView = new ModelAndView("purser/addressbook/infoContact");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_CONTACT, contactManager.findContactByID(parseId(Id)));
		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_MESSAGE, message);

		return modelAndView;
	}

	@PostMapping("/addressbook/contact/info")
	public ModelAndView addressBookContactInfoPost(@RequestParam(value = Constants.REQUEST_PARAM_ID) String Id) throws ParseIDException, ObjectNotFoundException {
		ModelAndView modelAndView = new ModelAndView("redirect:/addressbook/info");

		ModelMap modelMap = modelAndView.getModelMap();

		boolean success = contactManager.deleteContact(parseId(Id));

		if (!success) {
			modelAndView.setViewName("redirect:/addressbook/contact/info?id=" + Id + "&" + Constants.ATTRIBUTE_NAME_MESSAGE + "=" + Constants.CONTACT_DELETE_MESSAGE);
		}

		return modelAndView;
	}

	@GetMapping("/addressbook/contact/edit")
	public ModelAndView addressBookContactEditGet(@RequestParam(value = Constants.REQUEST_PARAM_ID) String Id) throws ParseIDException, ObjectNotFoundException, MissingServletRequestParameterException {
		ModelAndView modelAndView = new ModelAndView("purser/addressbook/editContact");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_CONTACT, contactManager.findContactByID(parseId(Id)));

		return modelAndView;
	}

	@PostMapping("/addressbook/contact/edit")
	public ModelAndView addressBookContactEditPost(@RequestParam(value = Constants.REQUEST_PARAM_ID) String Id,
												   @Valid @ModelAttribute(Constants.ATTRIBUTE_NAME_CONTACT) Contact contactValues) throws ParseIDException, ObjectNotFoundException, MissingServletRequestParameterException {
		ModelAndView modelAndView = new ModelAndView("redirect:/addressbook/contact/info?id="+ Id);

		ModelMap modelMap = modelAndView.getModelMap();

		contactManager.updateContactInfo(parseId(Id), contactValues);
		Contact contact = contactManager.findContactByID(parseId(Id));

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_CONTACT, contact);

		return modelAndView;
	}

	@GetMapping("/addressbook/contact/new")
	public ModelAndView addressBookContactNewGet() {
		ModelAndView modelAndView = new ModelAndView("/purser/addressbook/newContact");

		return modelAndView;
	}

	@PostMapping("/addressbook/contact/new")
	public ModelAndView addressBookContactNewPost(@Valid @ModelAttribute(Constants.ATTRIBUTE_NAME_CONTACT) Contact contactValues) throws ObjectNotFoundException, MissingServletRequestParameterException {
		ModelAndView modelAndView = new ModelAndView();

		ModelMap modelMap = modelAndView.getModelMap();

		Long Id = this.contactManager.addContact(contactValues);
		modelAndView.setViewName("redirect:/addressbook/contact/info?id=" + Id);

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_CONTACT, this.contactManager.findContactByID(Id));

		return modelAndView;
	}

	@PostMapping("/addressbook/contact/delete")
	public ModelAndView addressBookContactDeletePost(@RequestParam(value = Constants.REQUEST_PARAM_ID) String Id) throws ParseIDException, ObjectNotFoundException {
		ModelAndView modelAndView = new ModelAndView("redirect:/addressbook/info");

		ModelMap modelMap = modelAndView.getModelMap();

		boolean success = contactManager.deleteContact(parseId(Id));

		if (!success) {
			modelAndView.setViewName("redirect:/addressbook/contact/info?id=" + Id);
			modelMap.addAttribute(Constants.ATTRIBUTE_NAME_MESSAGE, Constants.CONTACT_DELETE_MESSAGE);
		}

		return modelAndView;
	}
}
