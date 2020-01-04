package danisik.pia.service.purser;

import danisik.pia.domain.InvoiceType;

import java.util.List;

/**
 * Interface for Invoice type manager.
 */
public interface InvoiceTypeManager {

	/**
	 * Get all invoice types from database.
	 * @return List of invoice types.
	 */
	List<InvoiceType> getInvoiceTypes();

	/**
	 * Add newly created invoice type into database.
	 * @param code Code of invoice type.
	 * @param name Name of invoice type.
	 */
	void addInvoiceType(String code, String name);

}
