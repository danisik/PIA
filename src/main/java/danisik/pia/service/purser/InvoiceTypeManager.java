package danisik.pia.service.purser;

import danisik.pia.domain.InvoiceType;

import java.util.List;

public interface InvoiceTypeManager {

	List<InvoiceType> getInvoiceTypes();

	void addInvoiceType(String code, String name);

}
