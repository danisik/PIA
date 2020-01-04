package danisik.pia.dao;

import danisik.pia.domain.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for domain Invoice.
 */
@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

	/**
	 * Find Goods by document serial number
	 * @param documentSerialNumber Invoice's document serial number.
	 * @return Invoice if document serial number is presented in database.
	 */
	Invoice findByDocumentSerialNumber(Long documentSerialNumber);

	/**
	 * Find Invoice by his ID.
	 * @param Id goods Id.
	 * @return Invoice if ID is presented in database.
	 */
	Invoice getById(Long Id);

}
