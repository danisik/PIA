package danisik.pia.dao;

import danisik.pia.model.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends CrudRepository<Goods, Long> {

	Goods getById(Long id);

	List<Goods> findFirst3ByOrderById();
}
