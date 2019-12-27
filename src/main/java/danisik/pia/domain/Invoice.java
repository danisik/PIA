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

	String tax_identification_number;

	String document_serial_number;

	String taxable_performance_scope;

	String taxable_performance_subject;

	String date_issue_document;

	String date_taxable_transaction;

	String cost_final;

	String tax_rate;

	String dph_base;

	String dph_quantified;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="contact_id")
	private Contact contact;

	@ManyToMany(mappedBy = "invoices")
	private List<User> users;

	public Invoice(String company, String residence, String tax_identification_number, String document_serial_number,
				   String taxable_performance_scope, String taxable_performance_subject, String date_issue_document,
				   String date_taxable_transaction, String cost_final, String tax_rate, String dph_base, String dph_quantified) {
		this.setCompany(company);
		this.setResidence(residence);
		this.setTax_identification_number(tax_identification_number);
		this.setDocument_serial_number(document_serial_number);
		this.setTaxable_performance_scope(taxable_performance_scope);
		this.setTaxable_performance_subject(taxable_performance_subject);
		this.setDate_issue_document(date_issue_document);
		this.setDate_taxable_transaction(date_taxable_transaction);
		this.setCost_final(cost_final);
		this.setTax_rate(tax_rate);
		this.setDph_base(dph_base);
		this.setDph_quantified(dph_quantified);
	}

}
