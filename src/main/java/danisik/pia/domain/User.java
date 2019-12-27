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

	private String birth_number;

	private String address;

	private String email;

	private String phone_number;

	private String bank_account;

	private String card_number;


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

	public User(String username, String password, String name, String birth_number, String address,
				String email, String phone_number, String bank_account, String card_number) {
		this.setUsername(username);
		this.setPassword(password);
		this.setName(name);
		this.setBirth_number(birth_number);
		this.setAddress(address);
		this.setEmail(email);
		this.setPhone_number(phone_number);
		this.setBank_account(bank_account);
		this.setCard_number(card_number);
	}

}
