package danisik.pia.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import danisik.pia.domain.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByCode(String code);

}
