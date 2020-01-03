package danisik.pia.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Contact extends EntityParent{

	private String name;

	private String residence;

	private String identificationNumber;

	private String taxIdentificationNumber;

	private String phoneNumber;

	private String email;

	private String bankAccount;

	@OneToMany(mappedBy = "customer")
	private List<Invoice> invoicesCustomer;

	@OneToMany(mappedBy = "supplier")
	private List<Invoice> invoicesSupplier;

	public Contact(String name, String residence, String identificationNumber, String taxIdentificationNumber,
				   String phoneNumber, String email, String bankAccount) {
		this.setName(name);
		this.setResidence(residence);
		this.setIdentificationNumber(identificationNumber);
		this.setTaxIdentificationNumber(taxIdentificationNumber);
		this.setPhoneNumber(phoneNumber);
		this.setEmail(email);
		this.setBankAccount(bankAccount);
	}

}