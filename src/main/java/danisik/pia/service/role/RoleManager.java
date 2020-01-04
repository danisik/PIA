package danisik.pia.service.role;

import java.util.List;

import danisik.pia.domain.Role;

/**
 * Interface for Role manager.
 */
public interface RoleManager {

	/**
	 * Get all roles from database.
	 * @return List of roles.
	 */
	List<Role> getRoles();

	/**
	 * Add newly created role into database.
	 * @param code Code of role.
	 * @param name Name of role.
	 */
	void addRole(String code, String name);

}
