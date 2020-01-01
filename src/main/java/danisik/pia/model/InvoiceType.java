package danisik.pia.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

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

	public InvoiceType(String code, String name) {
		this.setCode(code);
		this.setName(name);
	}

}
