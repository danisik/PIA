package danisik.pia.service.purser;

import danisik.pia.InitConstants;
import danisik.pia.dao.InvoiceTypeRepository;
import danisik.pia.model.InvoiceType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class InvoiceTypeManagerImpl implements InvoiceTypeManager {

	private final InvoiceTypeRepository invoiceTypeRepo;

	@Autowired
	public InvoiceTypeManagerImpl(InvoiceTypeRepository invoiceTypeRepo) {
		this.invoiceTypeRepo = invoiceTypeRepo;
	}

	@EventListener(classes = {
			ContextRefreshedEvent.class
	})
	@Order(1)
	@Transactional
	public void setup() {
		if (this.invoiceTypeRepo.count() == 0) {
			log.info("No invoice types presented, creating FAP and FAV");
			this.addInvoiceType(InitConstants.DEFAULT_INVOICE_TYPE_FAP_CODE, InitConstants.DEFAULT_INVOICE_TYPE_FAP_NAME);
			this.addInvoiceType(InitConstants.DEFAULT_INVOICE_TYPE_FAV_CODE, InitConstants.DEFAULT_INVOICE_TYPE_FAV_NAME);
		}
	}

	public void addInvoiceType(String code, String name) {
		InvoiceType invoiceType = new InvoiceType(code, name);
		this.invoiceTypeRepo.save(invoiceType);
	}

	@Override
	public List<InvoiceType> getInvoiceTypes() {
		List<InvoiceType> retVal = new LinkedList<>();
		this.invoiceTypeRepo.findAll().forEach(retVal::add);
		return retVal;
	}

}
