package danisik.pia.dao;

import danisik.pia.domain.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

	Contact findByIdentificationNumber(String identificationNumber);

	Contact getById(Long Id);
}
