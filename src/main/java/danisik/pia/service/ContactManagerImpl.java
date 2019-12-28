package danisik.pia.service;

import java.util.LinkedList;
import java.util.List;

import danisik.pia.InitConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import danisik.pia.dao.ContactRepository;
import danisik.pia.domain.Contact;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContactManagerImpl implements ContactManager {

	private final ContactRepository contactRepo;

	@Autowired
	public ContactManagerImpl(ContactRepository contactRepo) {
		this.contactRepo = contactRepo;
	}

	@EventListener(classes = {
			ContextRefreshedEvent.class
	})
	@Order(1)
	public void setup() {
		if (this.contactRepo.count() == 0) {
			log.info("No contacts present, creating 2 contacts");

			// Supplier
			this.addContact(InitConstants.DEFAULT_RECIPIENT_NAME,
					InitConstants.DEFAULT_RECIPIENT_RESIDENCE,
					InitConstants.DEFAULT_RECIPIENT_IDENTIFICATION_NUMBER,
					InitConstants.DEFAULT_RECIPIENT_TAX_IDENTIFICATION_NUMBER,
					InitConstants.DEFAULT_RECIPIENT_PHONE_NUMBER,
					InitConstants.DEFAULT_RECIPIENT_EMAIL,
					InitConstants.DEFAULT_RECIPIENT_BANK_ACCOUNT);

			// Recipient.
			this.addContact(InitConstants.DEFAULT_SUPPLIER_NAME,
					InitConstants.DEFAULT_SUPPLIER_RESIDENCE,
					InitConstants.DEFAULT_SUPPLIER_IDENTIFICATION_NUMBER,
					InitConstants.DEFAULT_SUPPLIER_TAX_IDENTIFICATION_NUMBER,
					InitConstants.DEFAULT_SUPPLIER_PHONE_NUMBER,
					InitConstants.DEFAULT_SUPPLIER_EMAIL,
					InitConstants.DEFAULT_SUPPLIER_BANK_ACCOUNT);

			Contact supplier = this.contactRepo.findByIdentificationNumber(InitConstants.DEFAULT_SUPPLIER_IDENTIFICATION_NUMBER);
			Contact recipient = this.contactRepo.findByIdentificationNumber(InitConstants.DEFAULT_RECIPIENT_IDENTIFICATION_NUMBER);

			this.contactRepo.save(supplier);
			this.contactRepo.save(recipient);
		}
	}

	public void addContact(String name, String residence, String identificationNumber, String taxIdentificationNumber,
						   String phoneNumber, String email, String bankAccount) {
		Contact contact = new Contact(name, residence, identificationNumber, taxIdentificationNumber, phoneNumber, email, bankAccount);
		this.contactRepo.save(contact);
	}

	@Override
	public List<Contact> getContacts() {
		List<Contact> retVal = new LinkedList<>();
		this.contactRepo.findAll().forEach(retVal::add);
		return retVal;
	}

}
