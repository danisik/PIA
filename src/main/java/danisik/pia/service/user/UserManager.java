package danisik.pia.service.user;

import java.util.List;

import danisik.pia.domain.User;
import danisik.pia.exceptions.ObjectNotFoundException;

public interface UserManager {

	List<User> getUsers();

	Long addUser(User userValues) throws ObjectNotFoundException;

	Long addUser(String username, String password, String name, String birthNumber, String address,
				 String email, String phoneNumber, String cardNumber);

	void updateUserInfo(String username, String name, String birthNumber, String address,
				String email, String phoneNumber, String cardNumber) throws ObjectNotFoundException;

	void updateUserInfo(String username, User userValues) throws ObjectNotFoundException;

	boolean updateUserRole(String username, String roleCode) throws ObjectNotFoundException;

	User findUserByUsername(String username) throws ObjectNotFoundException;

	User findUserById(Long Id) throws ObjectNotFoundException;

	void updatePassword(String username, String oldPassword,
						String newPassword, String newPasswordConfirmation) throws ObjectNotFoundException;

	void updatePassword(String username, String newPassword) throws ObjectNotFoundException;

	void deleteUser(Long Id) throws ObjectNotFoundException;

}
