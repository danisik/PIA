package danisik.pia.dao;

import danisik.pia.domain.Goods;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for domain Goods.
 */
@Repository
public interface GoodsRepository extends CrudRepository<Goods, Long> {

	/**
	 * Find Goods by his ID.
	 * @param id goods Id.
	 * @return Goods if ID is presented in database.
	 */
	Goods getById(Long id);


	/**
	 * Find first 3 records in database ordered by ID.
	 * @return Return list of 3 goods record.
	 */
	List<Goods> findFirst3ByOrderById();
}
