package danisik.pia.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import danisik.pia.dao.RoleRepository;
import danisik.pia.dao.UserRepository;
import danisik.pia.domain.Role;
import danisik.pia.domain.User;
import danisik.pia.model.WebCredentials;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserManagerImpl implements UserManager, UserDetailsService {

	private static final String DEFAULT_ADMIN1 = "Admin001";
	private static final String DEFAULT_ADMIN1_PASSWORD = "1234";

	private static final String DEFAULT_USER1 = "User0001";
	private static final String DEFAULT_USER1_PASSWORD = "0001";

	private static final String DEFAULT_USER2 = "User0002";
	private static final String DEFAULT_USER2_PASSWORD = "0002";

	private final PasswordEncoder encoder;

	private final UserRepository userRepo;
	private final RoleRepository roleRepo;

	@Autowired
	public UserManagerImpl(PasswordEncoder encoder, UserRepository userRepo, RoleRepository roleRepo) {
		this.encoder = encoder;
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
	}

	@Override
	public List<User> getUsers() {
		List<User> retVal = new LinkedList<>();
		for (User user : this.userRepo.findAll()) {
			retVal.add(user);
		}
		return Collections.unmodifiableList(retVal);
	}

	@Override
	public void addUser(String username, String password) {
		if (this.userRepo.findByUsername(username) != null) {
			throw new IllegalArgumentException("User already exists!");
		}

		String hashed = this.encoder.encode(password);
		User user = new User(username, hashed);
		this.userRepo.save(user);
	}

	@EventListener(classes = {
			ContextRefreshedEvent.class
	})
	@Order(2)
	@Transactional
	public void setup() {
		if (this.userRepo.count() == 0) {
			log.info("No user present, creating admin and users.");
			Role role_admin = this.roleRepo.findByCode("ADMIN");
			Role role_user = this.roleRepo.findByCode("USER");
			Role role_purser = this.roleRepo.findByCode("PURSER");

			this.addUser(DEFAULT_ADMIN1, DEFAULT_ADMIN1_PASSWORD);
			User admin1 = this.userRepo.findByUsername(DEFAULT_ADMIN1);
			admin1.getRoles().add(role_admin);
			this.userRepo.save(admin1);

			this.addUser(DEFAULT_USER1, DEFAULT_USER1_PASSWORD);
			User user1 = this.userRepo.findByUsername(DEFAULT_USER1);
			user1.getRoles().add(role_user);
			this.userRepo.save(user1);

			this.addUser(DEFAULT_USER2, DEFAULT_USER2_PASSWORD);
			User user2 = this.userRepo.findByUsername(DEFAULT_USER2);
			user2.getRoles().add(role_user);
			user2.getRoles().add(role_purser);
			this.userRepo.save(user2);
		}
	}

	private String toSpringRole(Role role) {
		return "ROLE_" + role.getCode();
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username!");
		}

		WebCredentials creds = new WebCredentials(user.getUsername(), user.getPassword());

		user.getRoles()
		.stream()
		.map(this::toSpringRole)
		.forEach(creds::addRole);

		return creds;
	}

}
