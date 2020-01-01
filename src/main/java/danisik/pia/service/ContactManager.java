package danisik.pia.service;

import java.util.List;

import danisik.pia.domain.Contact;

public interface ContactManager {

	List<Contact> getContacts();

	Long addContact(String name, String residence, String identificationNumber, String taxIdentificationNumber,
					String phoneNumber, String email, String bankAccount);

	Long addContact(Contact contactValues);

	Contact findContactByIdentificationNumber(String identificationNumber);

	Contact findContactByID(Long Id);

	void updateContactInfo(Long Id, Contact contactValues);

	void updateContactInfo(Long Id, String name, String residence, String identificationNumber, String taxIdentificationNumber,
							  String phoneNumber, String email, String bankAccount);

	boolean deleteContact(Long Id);

}
