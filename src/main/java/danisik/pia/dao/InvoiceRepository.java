package danisik.pia.dao;

import danisik.pia.domain.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

	Invoice findByDocumentSerialNumber(String documentSerialNumber);

}
