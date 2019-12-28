package danisik.pia.service;

import danisik.pia.domain.Goods;

import java.util.List;

public interface GoodsManager {

	List<Goods> getGoods();

	void addGoods(String name, Long quantity, Float pricePerOne, Float discount, Float taxRate);

}
