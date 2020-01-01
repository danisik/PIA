package danisik.pia.dao;

import danisik.pia.model.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

	Invoice findByDocumentSerialNumber(Long documentSerialNumber);

	Invoice getById(Long Id);

}
