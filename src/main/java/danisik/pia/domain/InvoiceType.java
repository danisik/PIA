package danisik.pia.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Domain entity representing Invoice type in application. InvoiceType is used in Invoice.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class InvoiceType extends EntityParent{

	@Column(unique = true)
	private String code;

	private String name;

	@OneToMany(mappedBy = "invoiceType")
	private List<Invoice> invoices;

	/**
	 * Constructor for domain entity InvoiceType.
	 * @param code Code of invoice type.
	 * @param name Name of invoice type.
	 */
	public InvoiceType(String code, String name) {
		this.setCode(code);
		this.setName(name);
	}

}
