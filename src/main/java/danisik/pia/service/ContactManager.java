package danisik.pia.service;

import java.util.List;

import danisik.pia.domain.Contact;

public interface ContactManager {

	List<Contact> getContacts();

	void addContact(String name, String residence, String identificationNumber, String taxIdentificationNumber,
					String phoneNumber, String email);

}
