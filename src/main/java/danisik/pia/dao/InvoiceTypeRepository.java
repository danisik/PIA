package danisik.pia.dao;

import danisik.pia.domain.InvoiceType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for domain InvoiceType.
 */
@Repository
public interface InvoiceTypeRepository extends CrudRepository<InvoiceType, Long> {

	/**
	 * Find InvoiceType by his code.
	 * @param code Code of invoice type.
	 * @return InvoiceType if code is presented in database.
	 */
	InvoiceType findByCode(String code);

}
