package danisik.pia.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import danisik.pia.Constants;
import danisik.pia.InitConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Invoice extends EntityParent{

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="supplier_contact_id")
	private Contact supplier;

	@Column(unique = true)
	private Long documentSerialNumber;

	private String dateExposure;

	private String dateDue;

	private String dateFruitionPerform;

	private Long symbolVariable;

	private Long symbolConstant;

	private Boolean cancelled;

	private String accountingCase;

	private String postingMDD;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="customer_contact_id")
	private Contact customer;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="invoice_type_id")
	private InvoiceType invoiceType;

	@OneToMany(mappedBy = "invoice")
	private List<Goods> wares;

	public Invoice(Long documentSerialNumber, String dateExposure, String dateDue, String dateFruitionPerform,
				   Long symbolVariable, Long symbolConstant, Boolean cancelled,
				   String accountingCase, String postingMDD) throws ParseException {

		this.setDocumentSerialNumber(documentSerialNumber);
		this.setDateExposure(dateExposure);
		this.setDateDue(dateDue);
		this.setDateFruitionPerform(dateFruitionPerform);
		this.setSymbolVariable(symbolVariable);
		this.setSymbolConstant(symbolConstant);
		this.setCancelled(cancelled);
		this.setAccountingCase(accountingCase);
		this.setPostingMDD(postingMDD);
	}
}
