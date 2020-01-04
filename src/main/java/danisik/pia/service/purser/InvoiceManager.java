package danisik.pia.service.purser;

import danisik.pia.domain.Goods;
import danisik.pia.domain.Invoice;
import danisik.pia.exceptions.ObjectNotFoundException;

import java.text.ParseException;
import java.util.List;

/**
 * Interface for Invoice manager.
 */
public interface InvoiceManager {

	/**
	 * Get all invoices from database.
	 * @return List of invoices.
	 */
	List<Invoice> getInvoices();

	/**
	 * Add new invoice into database.
	 * @param dateExposure Date exposure of invoice.
	 * @param dateDue Date due of invoice.
	 * @param dateFruitionPerform Date fruition perform of invoice.
	 * @param symbolVariable Variable symbol  of invoice.
	 * @param symbolConstant Constant symbol of invoice.
	 * @param cancelled Represents if invoice is cancelled or not.
	 * @param accountingCase Accounting case  of invoice.
	 * @param postingMDD Posting MD / D  of invoice.
	 * @return ID of newly created invoice.
	 * @throws ParseException Parse exception.
	 */
	Long addInvoice(String dateExposure, String dateDue, String dateFruitionPerform, Long symbolVariable,
					Long symbolConstant, Boolean cancelled, String accountingCase, String postingMDD) throws ParseException;

	/**
	 * Add newly created invoice into database.
	 * @param invoiceValues Invoice object containing invoice values getted from template.
	 * @return ID of newly created invoice.
	 * @throws ParseException Parse exception.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	Long addInvoice(Invoice invoiceValues) throws ParseException, ObjectNotFoundException;

	/**
	 * Find Invoice by ID.
	 * @param Id ID of invoice.
	 * @return Invoice if ID is presented in database.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	Invoice findInvoiceByID(Long Id) throws ObjectNotFoundException;

	/**
	 * Update invoice,
	 * @param Id ID of invoice.
	 * @param invoiceValues Invoice object containing invoice values getted from template.
	 * @throws ParseException Parse exception.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	void updateInvoice(Long Id, Invoice invoiceValues) throws ParseException, ObjectNotFoundException;

	/**
	 * Update invoice.
	 * @param Id Invoice ID.
	 * @param invoiceTypeCode Invoice type code.
	 * @param dateExposure Date exposure of invoice.
	 * @param dateDue Date due of invoice.
	 * @param dateFruitionPerform Date fruition perform of invoice.
	 * @param symbolVariable Variable symbol  of invoice.
	 * @param symbolConstant Constant symbol of invoice.
	 * @param accountingCase Accounting case  of invoice.
	 * @param postingMDD Posting MD / D  of invoice.
	 * @param supplierID ID of supplier.
	 * @param customerID ID of customer.
	 * @param wares Wares.
	 * @throws ParseException Parse exception.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	void updateInvoice(Long Id, String invoiceTypeCode, String dateExposure, String dateDue, String dateFruitionPerform,
					   Long symbolVariable, Long symbolConstant, String accountingCase, String postingMDD,
					   Long supplierID, Long customerID, List<Goods> wares) throws ParseException, ObjectNotFoundException;

	/**
	 * Set opposite value of cancelled of invoice.
	 * @param Id Invoice ID.
	 * @throws ParseException Parse exception.
	 */
	void updateInvoiceCancelled(Long Id) throws ObjectNotFoundException;

}
