package danisik.pia.domain;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Domain entity representing User in application. User is used for loging into application.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class User extends EntityParent{

	@Column(unique = true)
	private String username;

	private String password;

	private String name;

	private String birthNumber;

	private String address;

	private String email;

	private String phoneNumber;

	private String cardNumber;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private Role role;

	/**
	 * Constructor for domain entity Contact.
	 * @param username Username of user.
	 * @param password Password of user.
	 * @param name Name of user.
	 * @param birthNumber Birth number of user.
	 * @param address Address of user.
	 * @param email Email of user.
	 * @param phoneNumber Phone number of user.
	 * @param cardNumber Card number of user.
	 */
	public User(String username, String password, String name, String birthNumber, String address,
				String email, String phoneNumber, String cardNumber) {
		this.setUsername(username);
		this.setPassword(password);
		this.setName(name);
		this.setBirthNumber(birthNumber);
		this.setAddress(address);
		this.setEmail(email);
		this.setPhoneNumber(phoneNumber);
		this.setCardNumber(cardNumber);
	}

}
