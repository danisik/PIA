package danisik.pia.service.user;

import java.util.List;

import danisik.pia.domain.User;
import danisik.pia.exceptions.ObjectNotFoundException;

/**
 * Interface for User manager.
 */
public interface UserManager {

	/**
	 * Get all users from database.
	 * @return List of users.
	 */
	List<User> getUsers();

	/**
	 * Add new user into database.
	 * @param userValues User object containing user values getted from template.
	 * @return ID of newly created user.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	Long addUser(User userValues) throws ObjectNotFoundException;

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
	Long addUser(String username, String password, String name, String birthNumber, String address,
				 String email, String phoneNumber, String cardNumber);

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
	void updateUserInfo(String username, String name, String birthNumber, String address,
				String email, String phoneNumber, String cardNumber) throws ObjectNotFoundException;

	/**
	 * Update user info.
	 * @param username Username of user.
	 * @param userValues User object containing user values getted from template.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	void updateUserInfo(String username, User userValues) throws ObjectNotFoundException;

	/**
	 * Update user role.
	 * @param username Username of user.
	 * @param roleCode Code of role.
	 * @return
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	boolean updateUserRole(String username, String roleCode) throws ObjectNotFoundException;

	/**
	 * Find user by username in database.
	 * @param username Username of user.
	 * @return User if username is presented in database.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	User findUserByUsername(String username) throws ObjectNotFoundException;

	/**
	 * Find user by ID in database.
	 * @param Id ID of user.
	 * @return User if ID is presented in database.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	User findUserById(Long Id) throws ObjectNotFoundException;

	/**
	 * Update user password.
	 * @param username Username of user.
	 * @param newPassword New password.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	void updatePassword(String username, String newPassword) throws ObjectNotFoundException;

	/**
	 * Delete user.
	 * @param Id ID of user.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	void deleteUser(Long Id) throws ObjectNotFoundException;

}
