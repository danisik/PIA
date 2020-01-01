package danisik.pia.dao;

import danisik.pia.model.InvoiceType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceTypeRepository extends CrudRepository<InvoiceType, Long> {

	InvoiceType findByCode(String code);

}
