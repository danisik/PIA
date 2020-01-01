package danisik.pia.model;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
