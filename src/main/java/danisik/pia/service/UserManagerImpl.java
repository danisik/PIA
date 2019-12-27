package danisik.pia.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import danisik.pia.dao.ContactRepository;
import danisik.pia.dao.InvoiceRepository;
import danisik.pia.domain.Contact;
import danisik.pia.domain.Invoice;
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
	private final ContactRepository contactRepo;
	private final InvoiceRepository invoiceRepo;

	@Autowired
	public UserManagerImpl(PasswordEncoder encoder, UserRepository userRepo, RoleRepository roleRepo,
						   ContactRepository contactRepo, InvoiceRepository invoiceRepo) {
		this.encoder = encoder;
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.contactRepo = contactRepo;
		this.invoiceRepo = invoiceRepo;
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
	public void addUser(String username, String password, String name, String birthNumber, String address,
						String email, String phoneNumber, String bankAccount, String cardNumber) {
		if (this.userRepo.findByUsername(username) != null) {
			throw new IllegalArgumentException("User already exists!");
		}

		String hashed = this.encoder.encode(password);
		User user = new User(username, hashed, name, birthNumber, address, email, phoneNumber, bankAccount, cardNumber);
		this.userRepo.save(user);
	}

	@Override
	public User updateUserInfo(String username, User userValues) {
		return updateUserInfo(username, userValues.getName(), userValues.getBirthNumber(), userValues.getAddress(),
				userValues.getEmail(), userValues.getPhoneNumber(), userValues.getBankAccount(), userValues.getCardNumber());
	}

	@Override
	public User updateUserInfo(String username, String name, String birthNumber, String address,
						   String email, String phoneNumber, String bankAccount, String cardNumber) {
		User user = this.userRepo.findByUsername(username);
		user.setName(name);
		user.setBirthNumber(birthNumber);
		user.setAddress(address);
		user.setEmail(email);
		user.setPhoneNumber(phoneNumber);
		user.setBankAccount(bankAccount);
		user.setCardNumber(cardNumber);
		this.userRepo.save(user);
		return user;
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepo.findByUsername(username);
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
			Role role_purser = this.roleRepo.findByCode("PURSER");

			Contact contact = this.contactRepo.findByIdentificationNumber("26343398");
			Invoice invoice1 = this.invoiceRepo.findByDocumentSerialNumber("1234");
			Invoice invoice2 = this.invoiceRepo.findByDocumentSerialNumber("5678");

			this.addUser(DEFAULT_ADMIN1, DEFAULT_ADMIN1_PASSWORD, "Josef Kulihrášek", "123456/7890",
					"Sedláčkova 209/16, 301 00 Plzeň-Vnitřní Město", "kulihrasek@seznam.cz",
					"123456789", "1234567901/0600", "4339992979647585");
			User admin1 = this.userRepo.findByUsername(DEFAULT_ADMIN1);
			admin1.getRoles().add(role_admin);
			this.userRepo.save(admin1);

			this.addUser(DEFAULT_USER1, DEFAULT_USER1_PASSWORD, "František Seno", "456789/0123",
					"Sedláčkova 220/16, 301 00 Plzeň-Vnitřní Město", "seno@seznam.cz",
					"987654321", "1234567928/0600", "4599919667156954");
			User user1 = this.userRepo.findByUsername(DEFAULT_USER1);
			user1.getRoles().add(role_purser);
			this.userRepo.save(user1);

			this.addUser(DEFAULT_USER2, DEFAULT_USER2_PASSWORD, "Vojtěch Gorgon", "012345/6789",
					"Sedláčkova 230/16, 301 00 Plzeň-Vnitřní Město", "gorgon@seznam.cz",
					"456789123", "1234567936/0600", "4665212535774631");
			User user2 = this.userRepo.findByUsername(DEFAULT_USER2);
			user2.getRoles().add(role_purser);
			user2.getInvoices().add(invoice1);
			user2.getInvoices().add(invoice2);
			user2.getContacts().add(contact);
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
