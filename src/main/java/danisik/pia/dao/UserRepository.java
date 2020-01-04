package danisik.pia.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import danisik.pia.domain.User;

/**
 * Repository for domain User.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 * Find User by his username.
	 * @param username user's username.
	 * @return User if username is presented in database.
	 */
	User findByUsername(String username);

	/**
	 * Find User by his ID.
	 * @param Id User Id.
	 * @return User if ID is presented in database.
	 */
	User getById(Long Id);
}
