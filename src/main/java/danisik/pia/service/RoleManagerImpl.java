package danisik.pia.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import danisik.pia.dao.RoleRepository;
import danisik.pia.domain.Role;
import lombok.extern.slf4j.Slf4j;

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
	public void setup() {
		if (this.roleRepo.count() == 0) {
			log.info("No roles present, creating ADMIN and USER.");
			this.addRole("ADMIN", "Admins");
			this.addRole("USER", "Users");
			this.addRole("PURSER", "Pursers");
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
