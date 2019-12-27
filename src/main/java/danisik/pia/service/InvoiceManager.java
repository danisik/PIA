package danisik.pia.service;

import danisik.pia.domain.Invoice;

import java.util.List;

public interface InvoiceManager {

	List<Invoice> getInvoices();

	void addInvoice(String company, String residence, String taxIdentificationNumber, String documentSerialNumber,
				 String taxablePerformanceScope, String taxablePerformanceSubject, String dateIssueDocument,
				 String dateTaxableTransaction, String costFinal, String taxRate, String dphBase, String dphQuantified);

}
