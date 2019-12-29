package danisik.pia.service;

import danisik.pia.InitConstants;
import danisik.pia.dao.ContactRepository;
import danisik.pia.dao.InvoiceRepository;
import danisik.pia.domain.Contact;
import danisik.pia.domain.Invoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class InvoiceManagerImpl implements InvoiceManager {

	private final InvoiceRepository invoiceRepo;
	private final ContactRepository contactRepo;

	@Autowired
	public InvoiceManagerImpl(InvoiceRepository invoiceRepo, ContactRepository contactRepo) {
		this.invoiceRepo = invoiceRepo;
		this.contactRepo = contactRepo;
	}

	@EventListener(classes = {
			ContextRefreshedEvent.class
	})
	@Order(2)
	public void setup() {
		if (this.invoiceRepo.count() == 0) {
			log.info("No invoices present, creating 2 invoices.");

			Contact supplier = this.contactRepo.findByIdentificationNumber(InitConstants.DEFAULT_SUPPLIER_IDENTIFICATION_NUMBER);
			Contact customer = this.contactRepo.findByIdentificationNumber(InitConstants.DEFAULT_CUSTOMER_IDENTIFICATION_NUMBER);

			Long documentSerialNumber = InitConstants.DEFAULT_INVOICE1_ID;

			this.addInvoice(InitConstants.DEFAULT_INVOICE1_DATE_EXPOSURE,
					InitConstants.DEFAULT_INVOICE1_DATE_DUE,
					InitConstants.DEFAULT_INVOICE1_DATE_FRUITION_PERFORM,
					InitConstants.DEFAULT_INVOICE1_SYMBOL_VARIABLE,
					InitConstants.DEFAULT_INVOICE1_SYMBOL_CONSTANT,
					InitConstants.DEFAULT_INVOICE1_CANCELLED);

			Invoice invoice1 = this.invoiceRepo.findByDocumentSerialNumber(documentSerialNumber);

			invoice1.setSupplier(supplier);
			invoice1.setCustomer(customer);
			this.invoiceRepo.save(invoice1);

			documentSerialNumber = InitConstants.DEFAULT_INVOICE2_ID;

			this.addInvoice(InitConstants.DEFAULT_INVOICE2_DATE_EXPOSURE,
					InitConstants.DEFAULT_INVOICE2_DATE_DUE,
					InitConstants.DEFAULT_INVOICE2_DATE_FRUITION_PERFORM,
					InitConstants.DEFAULT_INVOICE2_SYMBOL_VARIABLE,
					InitConstants.DEFAULT_INVOICE2_SYMBOL_CONSTANT,
					InitConstants.DEFAULT_INVOICE2_CANCELLED);

			Invoice invoice2 = this.invoiceRepo.findByDocumentSerialNumber(documentSerialNumber);

			invoice2.setCustomer(supplier);
			invoice2.setSupplier(customer);
			this.invoiceRepo.save(invoice2);
		}


	}

	public void addInvoice(String dateExposure, String dateDue, String dateFruitionPerform, Long symbolVariable, Long symbolConstant, Boolean cancelled) {
		Invoice invoice = new Invoice((long)getInvoices().size(), dateExposure, dateDue, dateFruitionPerform, symbolVariable, symbolConstant, cancelled);
		this.invoiceRepo.save(invoice);
	}

	@Override
	public List<Invoice> getInvoices() {
		List<Invoice> retVal = new LinkedList<>();
		this.invoiceRepo.findAll().forEach(retVal::add);
		return retVal;
	}

}
