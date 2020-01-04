package danisik.pia.dao;

import danisik.pia.domain.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for domain Contact.
 */
@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

	/**
	 * Find Contact by his identification number.
	 * @param identificationNumber contact idenfitication number.
	 * @return Contact if identification number is presented in database.
	 */
	Contact findByIdentificationNumber(String identificationNumber);

	/**
	 * Find Contact by his ID.
	 * @param Id contact Id.
	 * @return Contact if ID is presented in database.
	 */
	Contact getById(Long Id);
}
