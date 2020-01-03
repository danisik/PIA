package danisik.pia.service.purser;

import danisik.pia.Constants;
import danisik.pia.InitConstants;
import danisik.pia.dao.ContactRepository;
import danisik.pia.dao.GoodsRepository;
import danisik.pia.dao.InvoiceRepository;
import danisik.pia.dao.InvoiceTypeRepository;
import danisik.pia.domain.Contact;
import danisik.pia.domain.Goods;
import danisik.pia.domain.Invoice;
import danisik.pia.domain.InvoiceType;
import danisik.pia.exceptions.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class InvoiceManagerImpl implements InvoiceManager {

	@Autowired
	private InvoiceRepository invoiceRepo;

	@Autowired
	private ContactRepository contactRepo;

	@Autowired
	private InvoiceTypeRepository invoiceTypeRepo;

	@Autowired
	private GoodsRepository goodsRepo;

	@EventListener(classes = {
			ContextRefreshedEvent.class
	})
	@Order(2)
	@Transactional
	public void setup() throws ParseException {
		if (this.invoiceRepo.count() == 0) {
			log.info("No invoices presented, creating 2 invoices.");

			Contact supplier = this.contactRepo.findByIdentificationNumber(InitConstants.DEFAULT_SUPPLIER_IDENTIFICATION_NUMBER);
			Contact customer = this.contactRepo.findByIdentificationNumber(InitConstants.DEFAULT_CUSTOMER_IDENTIFICATION_NUMBER);

			List<Goods> wares = goodsRepo.findFirst3ByOrderById();
			Goods[] waresArray = new Goods[Constants.INVOICE_DEFAULT_INIT_COUNT_GOODS];

			int i = 0;
			for (Goods goodsIt : wares) {
				waresArray[i] = goodsIt;
				i++;
				if (i >= Constants.INVOICE_DEFAULT_INIT_COUNT_GOODS) break;
			}

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
	public Long addInvoice(Invoice invoiceValues) throws ParseException, ObjectNotFoundException {
		Long Id = addInvoice(invoiceValues.getDateExposure(), invoiceValues.getDateDue(), invoiceValues.getDateFruitionPerform(),
				invoiceValues.getSymbolVariable(), invoiceValues.getSymbolConstant(), false,
				invoiceValues.getAccountingCase(), invoiceValues.getPostingMDD());
		Invoice invoice = findInvoiceByID(Id);
		invoice.setInvoiceType(invoiceTypeRepo.findByCode(invoiceValues.getInvoiceType().getCode()));
		invoice.setSupplier(contactRepo.getById(invoiceValues.getSupplier().getId()));
		invoice.setCustomer(contactRepo.getById(invoiceValues.getCustomer().getId()));

		manageGoods(invoice, invoiceValues.getWares());

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
	public Invoice findInvoiceByID(Long Id) throws ObjectNotFoundException {
		Invoice invoice = invoiceRepo.getById(Id);
		if (invoice == null) {
			throw new ObjectNotFoundException();
		}
		return invoice;
	}

	@Override
	public void updateInvoice(Long Id, Invoice invoiceValues) throws ParseException, ObjectNotFoundException {
		updateInvoice(Id, invoiceValues.getInvoiceType().getCode(), invoiceValues.getDateExposure(),
				invoiceValues.getDateDue(), invoiceValues.getDateFruitionPerform(),
				invoiceValues.getSymbolVariable(), invoiceValues.getSymbolConstant(),
				invoiceValues.getAccountingCase(), invoiceValues.getPostingMDD(),
				invoiceValues.getSupplier().getId(), invoiceValues.getCustomer().getId(), invoiceValues.getWares());
	}

	@Override
	public void updateInvoice(Long Id, String invoiceTypeCode, String dateExposure, String dateDue, String dateFruitionPerform,
					   Long symbolVariable, Long symbolConstant, String accountingCase, String postingMDD,
			           Long supplierID, Long customerID, List<Goods> wares)
			throws ParseException, ObjectNotFoundException {
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

		manageGoods(invoice, wares);

		invoiceRepo.save(invoice);
	}

	private void manageGoods(Invoice invoice, List<Goods> wares) {

		List<Goods> newWares = new ArrayList<Goods>();
		if (wares != null) {
			for (Goods goods : wares) {
				if (goods.getId() != null) {
					goodsRepo.delete(goodsRepo.getById(goods.getId()));
				}
				if (goods.getName() != null && goods.getQuantity() != null && goods.getPricePerOne() != null &&
						goods.getDiscount() != null && goods.getTaxRate() != null) {
					Goods newGoods = new Goods(goods.getName(), goods.getQuantity(), goods.getPricePerOne(), goods.getDiscount(), goods.getTaxRate());
					newGoods.setInvoice(invoice);
					goodsRepo.save(newGoods);
				}
			}
		}
		invoice.setWares(newWares);
	}

	@Override
	public void updateInvoiceCancelled(Long Id) throws ObjectNotFoundException {
		Invoice invoice = findInvoiceByID(Id);
		invoice.setCancelled(!invoice.getCancelled());
		invoiceRepo.save(invoice);
	}

}
