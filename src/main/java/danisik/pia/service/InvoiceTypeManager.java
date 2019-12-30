package danisik.pia.service;

import danisik.pia.domain.InvoiceType;
import danisik.pia.domain.Role;

import java.util.List;

public interface InvoiceTypeManager {

	List<InvoiceType> getInvoiceTypes();

	void addInvoiceType(String code, String name);

}
