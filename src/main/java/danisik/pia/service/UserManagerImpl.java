package danisik.pia.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;
import danisik.pia.InitConstants;

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
						String email, String phoneNumber, String cardNumber) {
		if (this.userRepo.findByUsername(username) != null) {
			throw new IllegalArgumentException("User already exists!");
		}

		String hashed = this.encoder.encode(password);
		User user = new User(username, hashed, name, birthNumber, address, email, phoneNumber, cardNumber);
		this.userRepo.save(user);
	}

	@Override
	public User updateUserInfo(String username, User userValues) {
		return updateUserInfo(username, userValues.getName(), userValues.getBirthNumber(), userValues.getAddress(),
				userValues.getEmail(), userValues.getPhoneNumber(), userValues.getCardNumber());
	}

	@Override
	public User updateUserInfo(String username, String name, String birthNumber, String address,
						   String email, String phoneNumber, String cardNumber) {
		User user = findUserByUsername(username);
		user.setName(name);
		user.setBirthNumber(birthNumber);
		user.setAddress(address);
		user.setEmail(email);
		user.setPhoneNumber(phoneNumber);
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
	@Order(3)
	@Transactional
	public void setup() {
		if (this.userRepo.count() == 0) {
			log.info("No user present, creating admin and users.");
			Role role_admin = this.roleRepo.findByCode(InitConstants.DEFAULT_ROLE_ADMIN_CODE);
			Role role_user = this.roleRepo.findByCode(InitConstants.DEFAULT_ROLE_USER_CODE);
			Role role_purser = this.roleRepo.findByCode(InitConstants.DEFAULT_ROLE_PURSER_CODE);

			Contact supplier = this.contactRepo.findByIdentificationNumber(InitConstants.DEFAULT_SUPPLIER_IDENTIFICATION_NUMBER);
			Contact recipient = this.contactRepo.findByIdentificationNumber(InitConstants.DEFAULT_RECIPIENT_IDENTIFICATION_NUMBER);

			Invoice invoice1 = this.invoiceRepo.findByDocumentSerialNumber(InitConstants.DEFAULT_INVOICE1_ID);
			Invoice invoice2 = this.invoiceRepo.findByDocumentSerialNumber(InitConstants.DEFAULT_INVOICE2_ID);

			this.addUser(InitConstants.DEFAULT_ADMIN1_USERNAME, InitConstants.DEFAULT_ADMIN1_PASSWORD, InitConstants.DEFAULT_ADMIN1_NAME,
					InitConstants.DEFAULT_ADMIN1_BIRTH_NUMBER, InitConstants.DEFAULT_ADMIN1_ADDRESS, InitConstants.DEFAULT_ADMIN1_EMAIL,
					InitConstants.DEFAULT_ADMIN1_PHONE_NUMBER, InitConstants.DEFAULT_ADMIN1_CARD_NUMBER);
			User admin1 = this.userRepo.findByUsername(InitConstants.DEFAULT_ADMIN1_USERNAME);
			admin1.getRoles().add(role_admin);
			this.userRepo.save(admin1);

			this.addUser(InitConstants.DEFAULT_USER1_USERNAME, InitConstants.DEFAULT_USER1_PASSWORD, InitConstants.DEFAULT_USER1_NAME,
					InitConstants.DEFAULT_USER1_BIRTH_NUMBER, InitConstants.DEFAULT_USER1_ADDRESS, InitConstants.DEFAULT_USER1_EMAIL,
					InitConstants.DEFAULT_USER1_PHONE_NUMBER, InitConstants.DEFAULT_USER1_CARD_NUMBER);
			User user1 = this.userRepo.findByUsername(InitConstants.DEFAULT_USER1_USERNAME);
			user1.getRoles().add(role_user);
			this.userRepo.save(user1);

			this.addUser(InitConstants.DEFAULT_USER2_USERNAME, InitConstants.DEFAULT_USER2_PASSWORD, InitConstants.DEFAULT_USER2_NAME,
					InitConstants.DEFAULT_USER2_BIRTH_NUMBER, InitConstants.DEFAULT_USER2_ADDRESS, InitConstants.DEFAULT_USER2_EMAIL,
					InitConstants.DEFAULT_USER2_PHONE_NUMBER, InitConstants.DEFAULT_USER2_CARD_NUMBER);
			User user2 = this.userRepo.findByUsername(InitConstants.DEFAULT_USER2_USERNAME);
			user2.getRoles().add(role_user);
			user2.getRoles().add(role_purser);

			user2.getInvoices().add(invoice1);
			user2.getInvoices().add(invoice2);
			user2.getContacts().add(supplier);
			user2.getContacts().add(recipient);
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
