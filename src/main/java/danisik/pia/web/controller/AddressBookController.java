package danisik.pia.web.controller;

import danisik.pia.domain.Contact;
import danisik.pia.domain.User;
import danisik.pia.service.ContactManager;
import danisik.pia.service.UserManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AddressBookController {

	private UserManager userManager;
	private ContactManager contactManager;

	private static final String ATTRIBUTE_NAME_CONTACT = "contact";
	private static final String ATTRIBUTE_NAME_CONTACTS = "contacts";

	private static final String ATTRIBUTE_MAPPING_ID = "id";

	public AddressBookController(ContactManager contactManager, UserManager userManager) {
		this.contactManager = contactManager;
		this.userManager = userManager;
	}

	@GetMapping("/addressbook/info")
	public ModelAndView addressBookInfo() {
		ModelAndView modelAndView = new ModelAndView("purser/addressbook/infoListContacts");

		ModelMap modelMap = modelAndView.getModelMap();

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userManager.findUserByUsername(username);

		modelMap.addAttribute(ATTRIBUTE_NAME_CONTACTS, user.getContacts());

		return modelAndView;
	}

	@GetMapping("/addressbook/contact/info")
	public ModelAndView addressBookInfoContact(@RequestParam(value = ATTRIBUTE_MAPPING_ID) Long Id) {
		ModelAndView modelAndView = new ModelAndView("purser/addressbook/infoContact");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(ATTRIBUTE_NAME_CONTACT, contactManager.findContactByID(Id));

		return modelAndView;
	}

	@GetMapping("/addressbook/contact/edit")
	public ModelAndView addressBookEditContactGet(@RequestParam(value = ATTRIBUTE_MAPPING_ID) Long Id) {
		ModelAndView modelAndView = new ModelAndView("purser/addressbook/editContact");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(ATTRIBUTE_NAME_CONTACT, contactManager.findContactByID(Id));

		return modelAndView;
	}

	@PostMapping("/addressbook/contact/edit")
	public ModelAndView addressBookEditContactPost(@RequestParam(value = ATTRIBUTE_MAPPING_ID) Long Id, @Valid @ModelAttribute(ATTRIBUTE_NAME_CONTACT) Contact contactValues) {
		ModelAndView modelAndView = new ModelAndView("redirect:/addressbook/contact/info?id="+ Id);

		ModelMap modelMap = modelAndView.getModelMap();

		Contact editedContact = contactManager.updateContactInfo(Id, contactValues);

		modelMap.addAttribute(ATTRIBUTE_NAME_CONTACT, editedContact);

		return modelAndView;
	}

	@GetMapping("/addressbook/new")
	public ModelAndView addressBookNew() {
		ModelAndView modelAndView = new ModelAndView("purser/addressbook/newContact");

		ModelMap modelMap = modelAndView.getModelMap();

		return modelAndView;
	}
}
