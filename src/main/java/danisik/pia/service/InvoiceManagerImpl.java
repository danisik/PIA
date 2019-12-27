package danisik.pia.service;

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
	@Order(1)
	public void setup() {
		if (this.invoiceRepo.count() == 0) {
			log.info("No invoices present, creating 2 invoices.");

			Contact contact = this.contactRepo.findByIdentificationNumber("26343398");

			this.addInvoice("ZF Engineering Pilsen s.r.o", "Univerzitní 1159/53, 301 00 Plzeň 3",
					"CZ26343398", "1234", "Nothing",
					"Boots", "2017-04-03", "2017-05-03",
					"10000", "500", "2000", "100");

			Invoice invoice1 = this.invoiceRepo.findByDocumentSerialNumber("1234");
			invoice1.setContact(contact);
			this.invoiceRepo.save(invoice1);

			this.addInvoice("ZF Engineering Pilsen s.r.o", "Univerzitní 1159/53, 301 00 Plzeň 3",
					"CZ26343398", "5678", "Don't know",
					"Car", "2017-04-03", "2017-05-03",
					"3000", "1000", "1000", "500");

			Invoice invoice2 = this.invoiceRepo.findByDocumentSerialNumber("5678");
			invoice2.setContact(contact);
			this.invoiceRepo.save(invoice2);
		}


	}

	public void addInvoice(String company, String residence, String taxIdentificationNumber, String documentSerialNumber,
						   String taxablePerformanceScope, String taxablePerformanceSubject, String dateIssueDocument,
						   String dateTaxableTransaction, String costFinal, String taxRate, String dphBase, String dphQuantified) {
		Invoice invoice = new Invoice(company, residence, taxIdentificationNumber, documentSerialNumber,
				taxablePerformanceScope, taxablePerformanceSubject, dateIssueDocument, dateTaxableTransaction,
				costFinal, taxRate, dphBase, dphQuantified);
		this.invoiceRepo.save(invoice);
	}

	@Override
	public List<Invoice> getInvoices() {
		List<Invoice> retVal = new LinkedList<>();
		this.invoiceRepo.findAll().forEach(retVal::add);
		return retVal;
	}

}
