package danisik.pia.service;

import danisik.pia.domain.Contact;
import danisik.pia.domain.Invoice;
import danisik.pia.domain.InvoiceType;

import java.text.ParseException;
import java.util.List;

public interface InvoiceManager {

	List<Invoice> getInvoices();

	Long addInvoice(String dateExposure, String dateDue, String dateFruitionPerform, Long symbolVariable,
					Long symbolConstant, Boolean cancelled, String accountingCase, String postingMDD) throws ParseException;

	Long addInvoice(Invoice invoiceValues) throws ParseException;

	Invoice findInvoiceByID(Long Id);

	void updateInvoice(Long Id, Invoice invoiceValues) throws ParseException;

	void updateInvoice(Long Id, String invoiceTypeCode, String dateExposure, String dateDue, String dateFruitionPerform,
					   Long symbolVariable, Long symbolConstant, String accountingCase, String postingMDD,
					   Long supplierID, Long customerID) throws ParseException;

	void updateInvoiceCancelled(Long Id);

}
