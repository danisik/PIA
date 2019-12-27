package danisik.pia.service;

import java.util.LinkedList;
import java.util.List;

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
			log.info("No contacts present, one contact");
			this.addContact("ZF Engineering Pilsen s.r.o", "Univerzitní 1159/53, 301 00 Plzeň 3",
					"26343398", "CZ26343398",
					"373 736 311", "info-plzen@zf.com");
		}
	}

	public void addContact(String name, String residence, String identificationNumber, String taxIdentificationNumber,
						   String phoneNumber, String email) {
		Contact contact = new Contact(name, residence, identificationNumber, taxIdentificationNumber, phoneNumber, email);
		this.contactRepo.save(contact);
	}

	@Override
	public List<Contact> getContacts() {
		List<Contact> retVal = new LinkedList<>();
		this.contactRepo.findAll().forEach(retVal::add);
		return retVal;
	}

}
