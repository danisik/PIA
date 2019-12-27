package danisik.pia.domain;

import java.util.LinkedList;
import java.util.List;

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

	private String bankAccount;

	private String cardNumber;


	@ManyToMany
	@JoinTable(
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private List<Role> roles = new LinkedList<>();

	@ManyToMany
	@JoinTable(
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "contact_id")
	)
	private List<Contact> contacts = new LinkedList<>();

	@ManyToMany
	@JoinTable(
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "invoice_id")
	)
	private List<Invoice> invoices = new LinkedList<>();

	public User(String username, String password, String name, String birthNumber, String address,
				String email, String phoneNumber, String bankAccount, String cardNumber) {
		this.setUsername(username);
		this.setPassword(password);
		this.setName(name);
		this.setBirthNumber(birthNumber);
		this.setAddress(address);
		this.setEmail(email);
		this.setPhoneNumber(phoneNumber);
		this.setBankAccount(bankAccount);
		this.setCardNumber(cardNumber);
	}

}
