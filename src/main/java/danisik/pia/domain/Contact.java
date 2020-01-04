package danisik.pia.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Domain entity representing Contact in application. Contact is used in Invoice as Customer / Supplier.
 */
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

	/**
	 * Constructor for domain entity Contact.
	 * @param name Name of contact.
	 * @param residence Residence of contact
	 * @param identificationNumber Identification number of contact.
	 * @param taxIdentificationNumber Tax identification number of contact.
	 * @param phoneNumber Phone number used by contact
	 * @param email Email used by contact.
	 * @param bankAccount Bank account used by contact.
	 */
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
