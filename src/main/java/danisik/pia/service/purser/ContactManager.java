package danisik.pia.service.purser;

import java.util.List;

import danisik.pia.domain.Contact;
import danisik.pia.exceptions.ObjectNotFoundException;
import org.springframework.web.bind.MissingServletRequestParameterException;

public interface ContactManager {

	List<Contact> getContacts();

	Long addContact(String name, String residence, String identificationNumber, String taxIdentificationNumber,
					String phoneNumber, String email, String bankAccount);

	Long addContact(Contact contactValues);

	Contact findContactByIdentificationNumber(String identificationNumber);

	Contact findContactByID(Long Id) throws MissingServletRequestParameterException, ObjectNotFoundException;

	void updateContactInfo(Long Id, Contact contactValues) throws MissingServletRequestParameterException, ObjectNotFoundException;

	void updateContactInfo(Long Id, String name, String residence, String identificationNumber, String taxIdentificationNumber,
							  String phoneNumber, String email, String bankAccount) throws MissingServletRequestParameterException, ObjectNotFoundException;

	boolean deleteContact(Long Id) throws ObjectNotFoundException;

}
