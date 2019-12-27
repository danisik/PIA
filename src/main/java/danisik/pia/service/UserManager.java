package danisik.pia.service;

import java.util.List;

import danisik.pia.domain.User;

public interface UserManager {

	List<User> getUsers();

	void addUser(String username, String password, String name, String birth_number, String address,
				 String email, String phone_number, String bank_account, String card_number);

}
