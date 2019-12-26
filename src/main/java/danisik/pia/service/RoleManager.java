package danisik.pia.service;

import java.util.List;

import danisik.pia.domain.Role;

public interface RoleManager {

	List<Role> getRoles();

	void addRole(String code, String name);

}
