package danisik.pia.service.purser;

import java.util.List;

import danisik.pia.domain.Contact;
import danisik.pia.exceptions.ObjectNotFoundException;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * Interface for Contact manager.
 */
public interface ContactManager {

	/**
	 * Get all contacts from database.
	 * @return List of contacts.
	 */
	List<Contact> getContacts();

	/**
	 * Add new contact into database.
	 * @param name Name of contact.
	 * @param residence Residence of contact.
	 * @param identificationNumber Identification number of contact.
	 * @param taxIdentificationNumber Tax identification number of contact.
	 * @param phoneNumber Phone number of contact.
	 * @param email Email of contact.
	 * @param bankAccount Bank account of Contact.
	 * @return Id of newly created contact.
	 */
	Long addContact(String name, String residence, String identificationNumber, String taxIdentificationNumber,
					String phoneNumber, String email, String bankAccount);

	/**
	 * Add new contact into database.
	 * @param contactValue Contact object containing contact values getted from template.
	 * @return Id of newly created contact.
	 */
	Long addContact(Contact contactValue);

	/**
	 * Find contact by his identification number.
	 * @param identificationNumber Identification number of contact.
	 * @return Contact if identification number is presented in database.
	 */
	Contact findContactByIdentificationNumber(String identificationNumber);

	/**
	 * Find contact by his ID
	 * @param Id ID of contact.
	 * @return Contact if ID is presented in database.
	 * @throws MissingServletRequestParameterException If required parameter Id is not presented in URL.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	Contact findContactByID(Long Id) throws MissingServletRequestParameterException, ObjectNotFoundException;

	/**
	 * Update contact info.
	 * @param Id ID of contact.
	 * @param contactValues Contact object containing contact values getted from template.
	 * @throws MissingServletRequestParameterException If required parameter Id is not presented in URL.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	void updateContactInfo(Long Id, Contact contactValues) throws MissingServletRequestParameterException, ObjectNotFoundException;

	/**
	 * Update contact info.
	 * Add new contact into database.
	 * @param name Name of contact.
	 * @param residence Residence of contact.
	 * @param identificationNumber Identification number of contact.
	 * @param taxIdentificationNumber Tax identification number of contact.
	 * @param phoneNumber Phone number of contact.
	 * @param email Email of contact.
	 * @param bankAccount Bank account of Contact.
	 * @throws MissingServletRequestParameterException If required parameter Id is not presented in URL.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	void updateContactInfo(Long Id, String name, String residence, String identificationNumber, String taxIdentificationNumber,
							  String phoneNumber, String email, String bankAccount) throws MissingServletRequestParameterException, ObjectNotFoundException;

	/**
	 * Delete contact from database.
	 * @param Id ID of contact.
	 * @return True if contact is deleted, false if not.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	boolean deleteContact(Long Id) throws ObjectNotFoundException;

}
