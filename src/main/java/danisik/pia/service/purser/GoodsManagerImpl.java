package danisik.pia.service.purser;

import danisik.pia.InitConstants;
import danisik.pia.dao.GoodsRepository;
import danisik.pia.dao.InvoiceRepository;
import danisik.pia.domain.Goods;
import danisik.pia.domain.Invoice;
import danisik.pia.exceptions.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation for Goods manager.
 */
@Service
@Slf4j
public class GoodsManagerImpl implements GoodsManager {

	@Autowired
	private GoodsRepository goodsRepo;

	@Autowired
	private InvoiceRepository invoiceRepo;

	/**
	 * Initialization setup for goods manager. Check if goods repository contains records and if not, initialize default values.
	 */
	@EventListener(classes = {
			ContextRefreshedEvent.class
	})
	@Order(3)
	@Transactional
	public void setup() {
		if (this.goodsRepo.count() == 0) {
			log.info("No goods presented, creating 3 goods.");

			Invoice invoice1 = invoiceRepo.findByDocumentSerialNumber(InitConstants.DEFAULT_INVOICE1_ID);
			Invoice invoice2 = invoiceRepo.findByDocumentSerialNumber(InitConstants.DEFAULT_INVOICE2_ID);

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

			List<Goods> wares = goodsRepo.findFirst3ByOrderById();

			wares.get(0).setInvoice(invoice1);
			wares.get(1).setInvoice(invoice2);
			wares.get(2).setInvoice(invoice2);
		}
	}

	/**
	 * Add new goods into database.
	 * @param name Name of goods.
	 * @param quantity Quantity of goods.
	 * @param pricePerOne Price of goods per one.
	 * @param discount Discount of goods.
	 * @param taxRate Tax rate of goods.
	 */
	@Override
	public void addGoods(String name, Long quantity, Float pricePerOne, Float discount, Float taxRate) {
		Goods goods = new Goods(name, quantity, pricePerOne, discount, taxRate);
		this.goodsRepo.save(goods);
	}

	/**
	 * Get wares from database.
	 * @return List of wares.
	 */
	@Override
	public List<Goods> getGoods() {
		List<Goods> retVal = new LinkedList<>();
		this.goodsRepo.findAll().forEach(retVal::add);
		return retVal;
	}

	/**
	 * Fint goods by his ID.
	 * @param Id ID of goods.
	 * @return Goods if ID is presented in database.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	@Override
	public Goods findGoodsByID(Long Id) throws ObjectNotFoundException {
		Goods goods = goodsRepo.getById(Id);
		if (goods == null) {
			throw new ObjectNotFoundException();
		}
		return goods;
	}

}
