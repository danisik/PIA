package danisik.pia.service;

import danisik.pia.Constants;
import danisik.pia.InitConstants;
import danisik.pia.dao.ContactRepository;
import danisik.pia.dao.InvoiceRepository;
import danisik.pia.dao.InvoiceTypeRepository;
import danisik.pia.domain.Contact;
import danisik.pia.domain.Invoice;
import danisik.pia.domain.InvoiceType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class InvoiceManagerImpl implements InvoiceManager {

	private final InvoiceRepository invoiceRepo;
	private final ContactRepository contactRepo;
	private final InvoiceTypeRepository invoiceTypeRepo;

	@Autowired
	public InvoiceManagerImpl(InvoiceRepository invoiceRepo, ContactRepository contactRepo, InvoiceTypeRepository invoiceTypeRepo) {
		this.invoiceRepo = invoiceRepo;
		this.contactRepo = contactRepo;
		this.invoiceTypeRepo = invoiceTypeRepo;
	}

	@EventListener(classes = {
			ContextRefreshedEvent.class
	})
	@Order(2)
	public void setup() throws ParseException {
		if (this.invoiceRepo.count() == 0) {
			log.info("No invoices presented, creating 2 invoices.");

			Contact supplier = this.contactRepo.findByIdentificationNumber(InitConstants.DEFAULT_SUPPLIER_IDENTIFICATION_NUMBER);
			Contact customer = this.contactRepo.findByIdentificationNumber(InitConstants.DEFAULT_CUSTOMER_IDENTIFICATION_NUMBER);

			InvoiceType typeFAV = invoiceTypeRepo.findByCode(InitConstants.DEFAULT_INVOICE_TYPE_FAV_CODE);
			InvoiceType typeFAP = invoiceTypeRepo.findByCode(InitConstants.DEFAULT_INVOICE_TYPE_FAP_CODE);

			Long documentSerialNumber = InitConstants.DEFAULT_INVOICE1_ID;

			this.addInvoice(InitConstants.DEFAULT_INVOICE1_DATE_EXPOSURE,
					InitConstants.DEFAULT_INVOICE1_DATE_DUE,
					InitConstants.DEFAULT_INVOICE1_DATE_FRUITION_PERFORM,
					InitConstants.DEFAULT_INVOICE1_SYMBOL_VARIABLE,
					InitConstants.DEFAULT_INVOICE1_SYMBOL_CONSTANT,
					InitConstants.DEFAULT_INVOICE1_CANCELLED,
					InitConstants.DEFAULT_INVOICE1_ACOUNTING_CASE,
					InitConstants.DEFAULT_INVOICE1_POSTING_MD_D);

			Invoice invoice1 = this.invoiceRepo.findByDocumentSerialNumber(documentSerialNumber);

			invoice1.setSupplier(supplier);
			invoice1.setCustomer(customer);
			invoice1.setInvoiceType(typeFAP);
			this.invoiceRepo.save(invoice1);

			documentSerialNumber = InitConstants.DEFAULT_INVOICE2_ID;

			this.addInvoice(InitConstants.DEFAULT_INVOICE2_DATE_EXPOSURE,
					InitConstants.DEFAULT_INVOICE2_DATE_DUE,
					InitConstants.DEFAULT_INVOICE2_DATE_FRUITION_PERFORM,
					InitConstants.DEFAULT_INVOICE2_SYMBOL_VARIABLE,
					InitConstants.DEFAULT_INVOICE2_SYMBOL_CONSTANT,
					InitConstants.DEFAULT_INVOICE2_CANCELLED,
					InitConstants.DEFAULT_INVOICE2_ACOUNTING_CASE,
					InitConstants.DEFAULT_INVOICE2_POSTING_MD_D);

			Invoice invoice2 = this.invoiceRepo.findByDocumentSerialNumber(documentSerialNumber);

			invoice2.setCustomer(supplier);
			invoice2.setSupplier(customer);
			invoice2.setInvoiceType(typeFAV);
			this.invoiceRepo.save(invoice2);
		}


	}

	@Override
	public Long addInvoice(Invoice invoiceValues) throws ParseException {
		Long Id = addInvoice(invoiceValues.getDateExposure(), invoiceValues.getDateDue(), invoiceValues.getDateFruitionPerform(),
				invoiceValues.getSymbolVariable(), invoiceValues.getSymbolConstant(), false,
				invoiceValues.getAccountingCase(), invoiceValues.getPostingMDD());
		Invoice invoice = findInvoiceByID(Id);
		invoice.setInvoiceType(invoiceTypeRepo.findByCode(invoiceValues.getInvoiceType().getCode()));
		invoice.setSupplier(contactRepo.getById(invoiceValues.getSupplier().getId()));
		invoice.setCustomer(contactRepo.getById(invoiceValues.getCustomer().getId()));
		this.invoiceRepo.save(invoice);
		return Id;
	}

	public Long addInvoice(String dateExposure, String dateDue, String dateFruitionPerform, Long symbolVariable,
						   Long symbolConstant, Boolean cancelled, String accountingCase, String postingMDD) throws ParseException {
		Invoice invoice = new Invoice((long)getInvoices().size(), dateExposure, dateDue, dateFruitionPerform,
				symbolVariable, symbolConstant, cancelled, accountingCase, postingMDD);
		this.invoiceRepo.save(invoice);
		return invoice.getId();
	}

	@Override
	public List<Invoice> getInvoices() {
		List<Invoice> retVal = new LinkedList<>();
		this.invoiceRepo.findAll().forEach(retVal::add);
		return retVal;
	}

	@Override
	public Invoice findInvoiceByID(Long Id) {
		return invoiceRepo.getById(Id);
	}

	@Override
	public void updateInvoice(Long Id, Invoice invoiceValues) throws ParseException {
		updateInvoice(Id, invoiceValues.getInvoiceType().getCode(), invoiceValues.getDateExposure(),
				invoiceValues.getDateDue(), invoiceValues.getDateFruitionPerform(),
				invoiceValues.getSymbolVariable(), invoiceValues.getSymbolConstant(),
				invoiceValues.getAccountingCase(), invoiceValues.getPostingMDD(),
				invoiceValues.getSupplier().getId(), invoiceValues.getCustomer().getId());
	}

	@Override
	public void updateInvoice(Long Id, String invoiceTypeCode, String dateExposure, String dateDue, String dateFruitionPerform,
					   Long symbolVariable, Long symbolConstant, String accountingCase, String postingMDD,
			           Long supplierID, Long customerID)
						throws ParseException {
		Invoice invoice = findInvoiceByID(Id);

		InvoiceType invoiceType = invoiceTypeRepo.findByCode(invoiceTypeCode);
		Contact supplier = contactRepo.getById(supplierID);
		Contact customer = contactRepo.getById(customerID);

		invoice.setInvoiceType(invoiceType);
		invoice.setDateExposure(dateExposure);
		invoice.setDateDue(dateDue);
		invoice.setDateFruitionPerform(dateFruitionPerform);
		invoice.setSymbolVariable(symbolVariable);
		invoice.setSymbolConstant(symbolConstant);
		invoice.setAccountingCase(accountingCase);
		invoice.setPostingMDD(postingMDD);
		invoice.setCustomer(customer);
		invoice.setSupplier(supplier);
		invoiceRepo.save(invoice);
	}

	@Override
	public void updateInvoiceCancelled(Long Id) {
		Invoice invoice = findInvoiceByID(Id);
		invoice.setCancelled(!invoice.getCancelled());
		invoiceRepo.save(invoice);
	}

}
