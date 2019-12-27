package danisik.pia.service;

import java.util.List;

import danisik.pia.domain.User;

public interface UserManager {

	List<User> getUsers();

	void addUser(String username, String password, String name, String birthNumber, String address,
				 String email, String phoneNumber, String bankAccount, String cardNumber);

}
