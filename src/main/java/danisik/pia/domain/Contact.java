package danisik.pia.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Contact extends EntityParent{

	private String name;

	private String residence;

	//25596641
	private String identification_number;

	private String tax_identification_number;

	private String phone_number;

	private String email;

	@ManyToMany(mappedBy = "contacts")
	private List<User> users;

	@OneToMany(mappedBy = "contact")
	private List<Invoice> invoices;

	public Contact(String name, String residence, String identification_number, String tax_identification_number,
				   String phone_number, String email) {
		this.setName(name);
		this.setResidence(residence);
		this.setIdentification_number(identification_number);
		this.setTax_identification_number(tax_identification_number);
		this.setPhone_number(phone_number);
		this.setEmail(email);
	}

}
