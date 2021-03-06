package danisik.pia.service.user;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import danisik.pia.Constants;
import danisik.pia.InitConstants;

import danisik.pia.exceptions.ObjectNotFoundException;
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

/**
 * Implementation for User manager.
 */
@Service
@Slf4j
public class UserManagerImpl implements UserManager, UserDetailsService {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	/**
	 * Get all users from database.
	 * @return List of users.
	 */
	@Override
	public List<User> getUsers() {
		List<User> retVal = new LinkedList<>();
		for (User user : this.userRepo.findAll()) {
			retVal.add(user);
		}
		return Collections.unmodifiableList(retVal);
	}

	/**
	 * Add new user into database.
	 * @param userValues User object containing user values getted from template.
	 * @return ID of newly created user.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@Override
	public Long addUser(User userValues) throws ObjectNotFoundException {
		if (this.userRepo.findByUsername(userValues.getUsername()) != null) {
			throw new IllegalArgumentException("User already exists!");
		}

		String generatedUsername = generateRandomString(Constants.USERNAME_LENGTH, Constants.USERNAME_SYMBOLS);
		String generatedPassword = generateRandomString(Constants.PASSWORD_LENGTH, Constants.PASSWORD_SYMBOLS);

		Long Id = addUser(generatedUsername, generatedPassword, userValues.getName(), userValues.getBirthNumber(),
				userValues.getAddress(), userValues.getEmail(), userValues.getPhoneNumber(),userValues.getCardNumber());

		User user = findUserById(Id);
		user.setRole(roleRepo.findByCode(userValues.getRole().getCode()));
		userRepo.save(user);

		return Id;
	}

	/**
	 * Add new user into database.
	 * @param username Username of user.
	 * @param password Password of user.
	 * @param name Name of user.
	 * @param birthNumber Birth number of user.
	 * @param address Address of user.
	 * @param email Email of user.
	 * @param phoneNumber Phone number of user.
	 * @param cardNumber Card number of user.
	 * @return ID of newly created user.
	 */
	@Override
	public Long addUser(String username, String password, String name, String birthNumber, String address,
						String email, String phoneNumber, String cardNumber) {


		String hashed = this.encoder.encode(password);
		User user = new User(username, hashed, name, birthNumber, address, email, phoneNumber, cardNumber);
		this.userRepo.save(user);
		return user.getId();
	}

	/**
	 * Update user info.
	 * @param username Username of user.
	 * @param userValues User object containing user values getted from template.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@Override
	public void updateUserInfo(String username, User userValues) throws ObjectNotFoundException {
		updateUserInfo(username, userValues.getName(), userValues.getBirthNumber(), userValues.getAddress(),
				userValues.getEmail(), userValues.getPhoneNumber(), userValues.getCardNumber());
	}

	/**
	 * Update user info.
	 * @param username Username of user.
	 * @param name Name of user.
	 * @param birthNumber Birth number of user.
	 * @param address Address of user.
	 * @param email Email of user.
	 * @param phoneNumber Phone number of user.
	 * @param cardNumber Card number of user.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@Override
	public void updateUserInfo(String username, String name, String birthNumber, String address,
						   String email, String phoneNumber, String cardNumber) throws ObjectNotFoundException {
		User user = findUserByUsername(username);
		user.setName(name);
		user.setBirthNumber(birthNumber);
		user.setAddress(address);
		user.setEmail(email);
		user.setPhoneNumber(phoneNumber);
		user.setCardNumber(cardNumber);
		this.userRepo.save(user);
	}

	/**
	 * Find user by username in database.
	 * @param username Username of user.
	 * @return User if username is presented in database.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@Override
	public User findUserByUsername(String username) throws ObjectNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new ObjectNotFoundException();
		}
		return user;
	}

	/**
	 * Find user by ID in database.
	 * @param Id ID of user.
	 * @return User if ID is presented in database.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@Override
	public User findUserById(Long Id) throws ObjectNotFoundException {
		User user = userRepo.getById(Id);
		if (user == null) {
			throw new ObjectNotFoundException();
		}
		return user;
	}

	/**
	 * Initialization setup for user manager. Check if user repository contains records and if not, initialize default values.
	 */
	@EventListener(classes = {
			ContextRefreshedEvent.class
	})
	@Order(3)
	@Transactional
	public void setup() {
		if (this.userRepo.count() == 0) {
			log.info("No user presented, creating admin and user and purser.");
			Role roleUser = this.roleRepo.findByCode(InitConstants.DEFAULT_ROLE_USER_CODE);
			Role roleAdmin = this.roleRepo.findByCode(InitConstants.DEFAULT_ROLE_ADMIN_CODE);
			Role rolePurser = this.roleRepo.findByCode(InitConstants.DEFAULT_ROLE_PURSER_CODE);

			this.addUser(InitConstants.DEFAULT_ADMIN1_USERNAME, InitConstants.DEFAULT_ADMIN1_PASSWORD, InitConstants.DEFAULT_ADMIN1_NAME,
					InitConstants.DEFAULT_ADMIN1_BIRTH_NUMBER, InitConstants.DEFAULT_ADMIN1_ADDRESS, InitConstants.DEFAULT_ADMIN1_EMAIL,
					InitConstants.DEFAULT_ADMIN1_PHONE_NUMBER, InitConstants.DEFAULT_ADMIN1_CARD_NUMBER);
			User admin1 = this.userRepo.findByUsername(InitConstants.DEFAULT_ADMIN1_USERNAME);
			admin1.setRole(roleAdmin);
			this.userRepo.save(admin1);

			this.addUser(InitConstants.DEFAULT_USER1_USERNAME, InitConstants.DEFAULT_USER1_PASSWORD, InitConstants.DEFAULT_USER1_NAME,
					InitConstants.DEFAULT_USER1_BIRTH_NUMBER, InitConstants.DEFAULT_USER1_ADDRESS, InitConstants.DEFAULT_USER1_EMAIL,
					InitConstants.DEFAULT_USER1_PHONE_NUMBER, InitConstants.DEFAULT_USER1_CARD_NUMBER);
			User user1 = this.userRepo.findByUsername(InitConstants.DEFAULT_USER1_USERNAME);
			user1.setRole(roleUser);
			this.userRepo.save(user1);

			this.addUser(InitConstants.DEFAULT_USER2_USERNAME, InitConstants.DEFAULT_USER2_PASSWORD, InitConstants.DEFAULT_USER2_NAME,
					InitConstants.DEFAULT_USER2_BIRTH_NUMBER, InitConstants.DEFAULT_USER2_ADDRESS, InitConstants.DEFAULT_USER2_EMAIL,
					InitConstants.DEFAULT_USER2_PHONE_NUMBER, InitConstants.DEFAULT_USER2_CARD_NUMBER);
			User user2 = this.userRepo.findByUsername(InitConstants.DEFAULT_USER2_USERNAME);
			user2.setRole(rolePurser);
			this.userRepo.save(user2);
		}
	}

	/**
	 * Return spring role code in string.
	 * @param role role.
	 * @return String containing spring role code.
	 */
	private String toSpringRole(Role role) {
		return role.getCode();
	}

	/**
	 * Load user details by his username.
	 * @param username Username of user.
	 * @return UserDetails of user.
	 * @throws UsernameNotFoundException Exception.
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username!");
		}

		WebCredentials creds = new WebCredentials(user.getUsername(), user.getPassword());
		creds.addRole(user.getRole().getCode());

		return creds;
	}

	/**
	 * Update user password.
	 * @param username Username of user.
	 * @param newPassword New password.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@Override
	public void updatePassword(String username, String newPassword) throws ObjectNotFoundException {

		User user = findUserByUsername(username);

		user.setPassword(encoder.encode(newPassword));
		userRepo.save(user);
	}

	/**
	 * Delete user.
	 * @param Id ID of user.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@Override
	public void deleteUser(Long Id) throws ObjectNotFoundException {
		User user = findUserById(Id);
		Role role = roleRepo.findByCode(user.getRole().getCode());
		role.getUsers().remove(user);
		userRepo.delete(user);
	}

	/**
	 * Update user role.
	 * @param username Username of user.
	 * @param roleCode Code of role.
	 * @return
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@Override
	public boolean updateUserRole(String username, String roleCode) throws ObjectNotFoundException {
		User user = findUserByUsername(username);

		if (!user.getRole().getCode().equals(roleCode) && user.getRole().getCode().equals(InitConstants.DEFAULT_ROLE_ADMIN_CODE)) {
			return false;
		}

		user.setRole(roleRepo.findByCode(roleCode));
		userRepo.save(user);

		return true;
	}

	/**
	 * Generate random string.
	 * @param length Length of string.
	 * @param symbols Symbols to be used in string.
	 * @return Generated random string.
	 */
	private String generateRandomString(int length, final String symbols) {
		SecureRandom rnd = new SecureRandom();

		StringBuilder sb = new StringBuilder(length);
		for(int i = 0; i < length; i++) {
			sb.append(symbols.charAt(rnd.nextInt(symbols.length())));
		}
		return sb.toString();
	}

}
