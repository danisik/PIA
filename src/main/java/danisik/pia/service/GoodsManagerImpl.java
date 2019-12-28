package danisik.pia.service;

import danisik.pia.InitConstants;
import danisik.pia.dao.ContactRepository;
import danisik.pia.dao.GoodsRepository;
import danisik.pia.dao.InvoiceRepository;
import danisik.pia.domain.Contact;
import danisik.pia.domain.Goods;
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
public class GoodsManagerImpl implements GoodsManager {

	private final GoodsRepository goodsRepo;
	private final InvoiceRepository invoiceRepo;

	@Autowired
	public GoodsManagerImpl(GoodsRepository goodsRepo, InvoiceRepository invoiceRepo) {
		this.goodsRepo = goodsRepo;
		this.invoiceRepo = invoiceRepo;
	}

	@EventListener(classes = {
			ContextRefreshedEvent.class
	})
	@Order(3)
	public void setup() {
		if (this.goodsRepo.count() == 0) {
			log.info("No goods presented, creating 3 goods.");
			Invoice invoice1 = this.invoiceRepo.findByDocumentSerialNumber(InitConstants.DEFAULT_INVOICE1_ID);
			Invoice invoice2 = this.invoiceRepo.findByDocumentSerialNumber(InitConstants.DEFAULT_INVOICE2_ID);

			this.addGoods(InitConstants.DEFAULT_GOODS1_NAME,
					InitConstants.DEFAULT_GOODS1_QUANTITY,
					InitConstants.DEFAULT_GOODS1_PRICE_PER_ONE,
					InitConstants.DEFAULT_GOODS1_DISCOUNT,
					InitConstants.DEFAULT_GOODS1_TAX_RATE);

			this.addGoods(InitConstants.DEFAULT_GOODS2_NAME,
					InitConstants.DEFAULT_GOODS2_QUANTITY,
					InitConstants.DEFAULT_GOODS2_PRICE_PER_ONE,
					InitConstants.DEFAULT_GOODS2_DISCOUNT,
					InitConstants.DEFAULT_GOODS2_TAX_RATE);

			this.addGoods(InitConstants.DEFAULT_GOODS3_NAME,
					InitConstants.DEFAULT_GOODS3_QUANTITY,
					InitConstants.DEFAULT_GOODS3_PRICE_PER_ONE,
					InitConstants.DEFAULT_GOODS3_DISCOUNT,
					InitConstants.DEFAULT_GOODS3_TAX_RATE);

			Goods goods1 = getGoods().get(0);
			goods1.setInvoice(invoice1);

			Goods goods2 = getGoods().get(1);
			goods2.setInvoice(invoice2);

			Goods goods3 = getGoods().get(2);
			goods3.setInvoice(invoice2);

			this.goodsRepo.save(goods1);
			this.goodsRepo.save(goods2);
			this.goodsRepo.save(goods3);
		}
	}

	public void addGoods(String name, Long quantity, Float pricePerOne, Float discount, Float taxRate) {
		Goods goods = new Goods(name, quantity, pricePerOne, discount, taxRate);
		this.goodsRepo.save(goods);
	}

	@Override
	public List<Goods> getGoods() {
		List<Goods> retVal = new LinkedList<>();
		this.goodsRepo.findAll().forEach(retVal::add);
		return retVal;
	}

}
