package danisik.pia.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Domain entity representing Role in application. Role is used in User for security.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Role extends EntityParent {

	@Column(unique = true)
	private String code;

	private String name;

	@OneToMany(mappedBy = "role")
	private List<User> users;

	/**
	 * Constructor for domain entity Role.
	 * @param code Code of role.
	 * @param name Name of role.
	 */
	public Role(String code, String name) {
		this.setCode(code);
		this.setName(name);
	}

}
