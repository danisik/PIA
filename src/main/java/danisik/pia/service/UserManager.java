package danisik.pia.service;

import java.util.List;

import danisik.pia.domain.User;

public interface UserManager {

	List<User> getUsers();

	void addUser(String username, String password);

}
