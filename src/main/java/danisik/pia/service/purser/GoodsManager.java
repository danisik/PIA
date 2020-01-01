package danisik.pia.service.purser;

import danisik.pia.model.Goods;

import java.util.List;

public interface GoodsManager {

	List<Goods> getGoods();

	void addGoods(String name, Long quantity, Float pricePerOne, Float discount, Float taxRate);

	Goods findGoodsByID(Long Id);

}
