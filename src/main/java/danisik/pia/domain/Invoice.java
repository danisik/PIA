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
public class Invoice extends EntityParent{

	String company;

	String residence;

	String taxIdentificationNumber;

	@Column(unique = true)
	String documentSerialNumber;

	String taxablePerformanceScope;

	String taxablePerformanceSubject;

	String dateIssueDocument;

	String dateTaxableTransaction;

	String costFinal;

	String taxRate;

	String dphBase;

	String dphQuantified;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="contact_id")
	private Contact contact;

	@ManyToMany(mappedBy = "invoices")
	private List<User> users;

	public Invoice(String company, String residence, String taxIdentificationNumber, String documentSerialNumber,
				   String taxablePerformanceScope, String taxablePerformanceSubject, String dateIssueDocument,
				   String dateTaxableTransaction, String costFinal, String taxRate, String dphBase, String dphQuantified) {
		this.setCompany(company);
		this.setResidence(residence);
		this.setTaxIdentificationNumber(taxIdentificationNumber);
		this.setDocumentSerialNumber(documentSerialNumber);
		this.setTaxablePerformanceScope(taxablePerformanceScope);
		this.setTaxablePerformanceSubject(taxablePerformanceSubject);
		this.setDateIssueDocument(dateIssueDocument);
		this.setDateTaxableTransaction(dateTaxableTransaction);
		this.setCostFinal(costFinal);
		this.setTaxRate(taxRate);
		this.setDphBase(dphBase);
		this.setDphQuantified(dphQuantified);
	}

}
