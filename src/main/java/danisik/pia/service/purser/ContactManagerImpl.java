package danisik.pia.service.purser;

import java.util.LinkedList;
import java.util.List;

import danisik.pia.InitConstants;
import danisik.pia.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Service;

import danisik.pia.dao.ContactRepository;
import danisik.pia.domain.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.transaction.Transactional;

/**
 * Implementation for Contact manager.
 */
@Service
@Slf4j
public class ContactManagerImpl implements ContactManager {

	@Autowired
	private ContactRepository contactRepo;

	/**
	 * Initialization setup for contact manager. Check if contact repository contains records and if not, initialize default values.
	 */
	@EventListener(classes = {
			ContextRefreshedEvent.class
	})
	@Order(1)
	@Transactional
	public void setup() {
		if (this.contactRepo.count() == 0) {
			log.info("No contacts presented, creating 2 contacts");

			// Supplier
			this.addContact(InitConstants.DEFAULT_CUSTOMER_NAME,
					InitConstants.DEFAULT_CUSTOMER_RESIDENCE,
					InitConstants.DEFAULT_CUSTOMER_IDENTIFICATION_NUMBER,
					InitConstants.DEFAULT_CUSTOMER_TAX_IDENTIFICATION_NUMBER,
					InitConstants.DEFAULT_CUSTOMER_PHONE_NUMBER,
					InitConstants.DEFAULT_CUSTOMER_EMAIL,
					InitConstants.DEFAULT_CUSTOMER_BANK_ACCOUNT);

			// Recipient.
			this.addContact(InitConstants.DEFAULT_SUPPLIER_NAME,
					InitConstants.DEFAULT_SUPPLIER_RESIDENCE,
					InitConstants.DEFAULT_SUPPLIER_IDENTIFICATION_NUMBER,
					InitConstants.DEFAULT_SUPPLIER_TAX_IDENTIFICATION_NUMBER,
					InitConstants.DEFAULT_SUPPLIER_PHONE_NUMBER,
					InitConstants.DEFAULT_SUPPLIER_EMAIL,
					InitConstants.DEFAULT_SUPPLIER_BANK_ACCOUNT);

			Contact supplier = this.contactRepo.findByIdentificationNumber(InitConstants.DEFAULT_SUPPLIER_IDENTIFICATION_NUMBER);
			Contact customer = this.contactRepo.findByIdentificationNumber(InitConstants.DEFAULT_CUSTOMER_IDENTIFICATION_NUMBER);

			this.contactRepo.save(supplier);
			this.contactRepo.save(customer);
		}
	}

	/**
	 * Add new contact into database.
	 * @param contactValue Contact object containing contact values getted from template.
	 * @return Id of newly created contact.
	 */
	@Override
	public Long addContact(Contact contactValue) {
		return addContact(contactValue.getName(), contactValue.getResidence(), contactValue.getIdentificationNumber(),
				contactValue.getTaxIdentificationNumber(), contactValue.getPhoneNumber(),
				contactValue.getEmail(), contactValue.getBankAccount());
	}


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
	@Override
	public Long addContact(String name, String residence, String identificationNumber, String taxIdentificationNumber,
						   String phoneNumber, String email, String bankAccount) {
		Contact contact = new Contact(name, residence, identificationNumber, taxIdentificationNumber, phoneNumber, email, bankAccount);
		this.contactRepo.save(contact);
		return contact.getId();
	}

	/**
	 * Get all contacts from database.
	 * @return List of contacts.
	 */
	@Override
	public List<Contact> getContacts() {
		List<Contact> retVal = new LinkedList<>();
		this.contactRepo.findAll().forEach(retVal::add);
		return retVal;
	}

	/**
	 * Find contact by his identification number.
	 * @param identificationNumber Identification number of contact.
	 * @return Contact if identification number is presented in database.
	 */
	@Override
	public Contact findContactByIdentificationNumber(String identificationNumber) {
		return contactRepo.findByIdentificationNumber(identificationNumber);
	}

	/**
	 * Find contact by his ID
	 * @param Id ID of contact.
	 * @return Contact if ID is presented in database.
	 * @throws MissingServletRequestParameterException If required parameter Id is not presented in URL.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@Override
	public Contact findContactByID(Long Id) throws ObjectNotFoundException {
		Contact contact = contactRepo.getById(Id);
		if (contact == null) {
			throw new ObjectNotFoundException();
		}
		return contact;
	}

	/**
	 * Update contact info.
	 * @param Id ID of contact.
	 * @param contactValues Contact object containing contact values getted from template.
	 * @throws MissingServletRequestParameterException If required parameter Id is not presented in URL.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@Override
	public void updateContactInfo(Long Id, Contact contactValues) throws ObjectNotFoundException {
		updateContactInfo(Id, contactValues.getName(), contactValues.getResidence(), contactValues.getIdentificationNumber(),
				contactValues.getTaxIdentificationNumber(), contactValues.getPhoneNumber(), contactValues.getEmail(),
				contactValues.getBankAccount());
	}

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
	@Override
	public void updateContactInfo(Long Id, String name, String residence, String identificationNumber, String taxIdentificationNumber,
									 String phoneNumber, String email, String bankAccount) throws ObjectNotFoundException {
		Contact contact = findContactByID(Id);
		contact.setName(name);
		contact.setResidence(residence);
		contact.setIdentificationNumber(identificationNumber);
		contact.setTaxIdentificationNumber(taxIdentificationNumber);
		contact.setPhoneNumber(phoneNumber);
		contact.setEmail(email);
		contact.setBankAccount(bankAccount);

		this.contactRepo.save(contact);
	}

	/**
	 * Delete contact from database.
	 * @param Id ID of contact.
	 * @return True if contact is deleted, false if not.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@Override
	public boolean deleteContact(Long Id) throws ObjectNotFoundException {
		Contact contact = findContactByID(Id);

		if (contact.getInvoicesCustomer().size() != 0 || contact.getInvoicesSupplier().size() != 0) {
			return false;
		}

		this.contactRepo.delete(contact);
		return true;
	}

}
