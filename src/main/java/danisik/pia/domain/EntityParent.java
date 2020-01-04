package danisik.pia.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

/**
 * Class which is extended for all domain entities containing common attributes.
 */
@MappedSuperclass
@Data
public class EntityParent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
}
