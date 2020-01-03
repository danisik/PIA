package danisik.pia.service.purser;

import danisik.pia.domain.Goods;
import danisik.pia.domain.Invoice;
import danisik.pia.exceptions.ObjectNotFoundException;

import java.text.ParseException;
import java.util.List;

public interface InvoiceManager {

	List<Invoice> getInvoices();

	Long addInvoice(String dateExposure, String dateDue, String dateFruitionPerform, Long symbolVariable,
					Long symbolConstant, Boolean cancelled, String accountingCase, String postingMDD) throws ParseException;

	Long addInvoice(Invoice invoiceValues) throws ParseException, ObjectNotFoundException;

	Invoice findInvoiceByID(Long Id) throws ObjectNotFoundException;

	void updateInvoice(Long Id, Invoice invoiceValues) throws ParseException, ObjectNotFoundException;

	void updateInvoice(Long Id, String invoiceTypeCode, String dateExposure, String dateDue, String dateFruitionPerform,
					   Long symbolVariable, Long symbolConstant, String accountingCase, String postingMDD,
					   Long supplierID, Long customerID, List<Goods> wares) throws ParseException, ObjectNotFoundException;

	void updateInvoiceCancelled(Long Id) throws ObjectNotFoundException;

}
