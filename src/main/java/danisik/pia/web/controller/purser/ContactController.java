package danisik.pia.web.controller.purser;

import danisik.pia.Constants;
import danisik.pia.domain.Contact;
import danisik.pia.service.ContactManager;
import danisik.pia.service.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ContactController {

	private UserManager userManager;
	private ContactManager contactManager;

	public ContactController(ContactManager contactManager, UserManager userManager) {
		this.contactManager = contactManager;
		this.userManager = userManager;
	}

	@GetMapping("/addressbook/info")
	public ModelAndView addressBookInfo() {
		ModelAndView modelAndView = new ModelAndView("purser/addressbook/infoListContacts");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_CONTACTS, contactManager.getContacts());

		return modelAndView;
	}

	@GetMapping("/addressbook/contact/info")
	public ModelAndView addressBookContactInfo(@RequestParam(value = Constants.REQUEST_PARAM_ID) Long Id) {
		ModelAndView modelAndView = new ModelAndView("purser/addressbook/infoContact");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_CONTACT, contactManager.findContactByID(Id));

		return modelAndView;
	}

	@GetMapping("/addressbook/contact/edit")
	public ModelAndView addressBookContactEditGet(@RequestParam(value = Constants.REQUEST_PARAM_ID) Long Id) {
		ModelAndView modelAndView = new ModelAndView("purser/addressbook/editContact");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_CONTACT, contactManager.findContactByID(Id));

		return modelAndView;
	}

	@PostMapping("/addressbook/contact/edit")
	public ModelAndView addressBookContactEditPost(@RequestParam(value = Constants.REQUEST_PARAM_ID) Long Id,
												   @Valid @ModelAttribute(Constants.ATTRIBUTE_NAME_CONTACT) Contact contactValues) {
		ModelAndView modelAndView = new ModelAndView("redirect:/addressbook/contact/info?id="+ Id);

		ModelMap modelMap = modelAndView.getModelMap();

		contactManager.updateContactInfo(Id, contactValues);
		Contact contact = contactManager.findContactByID(Id);

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_CONTACT, contact);

		return modelAndView;
	}

	@GetMapping("/addressbook/contact/new")
	public ModelAndView addressBookContactNewGet() {
		ModelAndView modelAndView = new ModelAndView("/purser/addressbook/newContact");

		return modelAndView;
	}

	@PostMapping("/addressbook/contact/new")
	public ModelAndView addressBookContactNewPost(@Valid @ModelAttribute(Constants.ATTRIBUTE_NAME_CONTACT) Contact contactValues) {
		ModelAndView modelAndView = new ModelAndView();

		ModelMap modelMap = modelAndView.getModelMap();

		Long Id = this.contactManager.addContact(contactValues);
		modelAndView.setViewName("redirect:/addressbook/contact/info?id=" + Id);

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_CONTACT, this.contactManager.findContactByID(Id));

		return modelAndView;
	}

	@GetMapping("/addressbook/contact/delete")
	public ModelAndView addressBookContactDeleteGet() {
		ModelAndView modelAndView = new ModelAndView("redirect:/addressbook/info");
		return modelAndView;
	}

	@PostMapping("/addressbook/contact/delete")
	public ModelAndView addressBookContactDeletePost(@RequestParam(value = Constants.REQUEST_PARAM_ID) Long Id) {
		ModelAndView modelAndView = new ModelAndView("redirect:/addressbook/info");

		contactManager.deleteContact(Id);

		return modelAndView;
	}
}
