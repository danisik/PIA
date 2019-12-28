package danisik.pia.service;

import danisik.pia.domain.Invoice;

import java.util.List;

public interface InvoiceManager {

	List<Invoice> getInvoices();

	void addInvoice(String dateExposure, String dateDue, String dateFruitionPerform, Long symbolVariable, Long symbolConstant);

}
