package danisik.pia.dao;

import danisik.pia.domain.Goods;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends CrudRepository<Goods, Long> {

	Goods getById(Integer id);

}
