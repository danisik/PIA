package danisik.pia.service;

import java.util.List;

import danisik.pia.domain.Role;
import danisik.pia.domain.User;

public interface UserManager {

	List<User> getUsers();

	Long addUser(User userValues);

	Long addUser(String username, String password, String name, String birthNumber, String address,
				 String email, String phoneNumber, String cardNumber);

	void updateUserInfo(String username, String name, String birthNumber, String address,
				String email, String phoneNumber, String cardNumber);

	void updateUserInfo(String username, User userValues);

	void updateUserRole(String username, String roleCode);

	User findUserByUsername(String username);

	User findUserById(Long Id);

	void updatePassword(String username, String oldPassword,
						String newPassword, String newPasswordConfirmation);

	void updatePassword(String username, String newPassword);

	void deleteUser(Long Id);

}
