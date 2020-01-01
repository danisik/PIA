package danisik.pia.service;

import java.util.LinkedList;
import java.util.List;

import danisik.pia.InitConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import danisik.pia.dao.RoleRepository;
import danisik.pia.model.Role;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Service
@Slf4j
public class RoleManagerImpl implements RoleManager {

	private final RoleRepository roleRepo;

	@Autowired
	public RoleManagerImpl(RoleRepository roleRepo) {
		this.roleRepo = roleRepo;
	}

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

	public void addRole(String code, String name) {
		Role role = new Role(code, name);
		this.roleRepo.save(role);
	}

	@Override
	public List<Role> getRoles() {
		List<Role> retVal = new LinkedList<>();
		this.roleRepo.findAll().forEach(retVal::add);
		return retVal;
	}

}
