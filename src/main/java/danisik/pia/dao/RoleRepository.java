package danisik.pia.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import danisik.pia.domain.Role;

/**
 * Repository for domain Role.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	/**
	 * Find Role by his code.
	 * @param code Code of role.
	 * @return Role if code is presented in database.
	 */
	Role findByCode(String code);

}
