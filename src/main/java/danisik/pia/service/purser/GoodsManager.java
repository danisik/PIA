package danisik.pia.service.purser;

import danisik.pia.domain.Goods;
import danisik.pia.exceptions.ObjectNotFoundException;

import java.util.List;

/**
 * Interface for Goods manager.
 */
public interface GoodsManager {

	/**
	 * Get wares from database.
	 * @return List of wares.
	 */
	List<Goods> getGoods();

	/**
	 * Add new goods into database.
	 * @param name Name of goods.
	 * @param quantity Quantity of goods.
	 * @param pricePerOne Price of goods per one.
	 * @param discount Discount of goods.
	 * @param taxRate Tax rate of goods.
	 */
	void addGoods(String name, Long quantity, Float pricePerOne, Float discount, Float taxRate);

	/**
	 * Fint goods by his ID.
	 * @param Id ID of goods.
	 * @return Goods if ID is presented in database.
	 * @throws ObjectNotFoundException If sent ID is not presented in database.
	 */
	Goods findGoodsByID(Long Id) throws ObjectNotFoundException;

}
