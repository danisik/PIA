package danisik.pia.service;

import java.util.List;

import danisik.pia.domain.Contact;

public interface ContactManager {

	List<Contact> getContacts();

	void addContact(String name, String residence, String identificationNumber, String taxIdentificationNumber,
					String phoneNumber, String email, String bankAccount);

	Contact findContactByIdentificationNumber(String identificationNumber);

	Contact findContactByID(Long Id);

	Contact updateContactInfo(Long Id, Contact contactValues);

	Contact updateContactInfo(Long Id, String name, String residence, String identificationNumber, String taxIdentificationNumber,
							  String phoneNumber, String email, String bankAccount);

}
