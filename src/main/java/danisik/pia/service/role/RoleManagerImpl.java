package danisik.pia.service.role;

import java.util.LinkedList;
import java.util.List;

import danisik.pia.InitConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import danisik.pia.dao.RoleRepository;
import danisik.pia.domain.Role;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

/**
 * Implementation for Role manager.
 */
@Service
@Slf4j
public class RoleManagerImpl implements RoleManager {

	@Autowired
	private RoleRepository roleRepo;

	/**
	 * Initialization setup for role manager. Check if role repository contains records and if not, initialize default values.
	 */
	@EventListener(classes = {
			ContextRefreshedEvent.class
	})
	@Order(1)
	@Transactional
	public void setup() {
		if (this.roleRepo.count() == 0) {
			log.info("No roles presented, creating ADMIN and PURSER.");
			this.addRole(InitConstants.DEFAULT_ROLE_ADMIN_CODE, InitConstants.DEFAULT_ROLE_ADMIN_NAME);
			this.addRole(InitConstants.DEFAULT_ROLE_USER_CODE, InitConstants.DEFAULT_ROLE_USER_NAME);
			this.addRole(InitConstants.DEFAULT_ROLE_PURSER_CODE, InitConstants.DEFAULT_ROLE_PURSER_NAME);
		}
	}

	/**
	 * Add newly created role into database.
	 * @param code Code of role.
	 * @param name Name of role.
	 */
	public void addRole(String code, String name) {
		Role role = new Role(code, name);
		this.roleRepo.save(role);
	}

	/**
	 * Get all roles from database.
	 * @return List of roles.
	 */
	@Override
	public List<Role> getRoles() {
		List<Role> retVal = new LinkedList<>();
		this.roleRepo.findAll().forEach(retVal::add);
		return retVal;
	}

}
