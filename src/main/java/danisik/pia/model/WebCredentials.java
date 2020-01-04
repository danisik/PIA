package danisik.pia.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Model used in spring security representing user credentials saved in session.
 */
public class WebCredentials implements UserDetails {

	private static final long serialVersionUID = 26298569253774237L;

	private final String username;
	private final String password;
	private Collection<String> roles;

	/**
	 * Constructor.
	 * @param username Username of user.
	 * @param password Password of user.
	 */
	public WebCredentials(String username, String password) {
		this.username = username;
		this.password = password;
		this.roles = new LinkedList<>();
	}

	/**
	 * Get authorities for logged User.
	 * @return List of roles.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles
		.stream()
		.map(SimpleGrantedAuthority::new)
		.collect(Collectors.toList());
	}

	/**
	 * Get logged user password.
	 * @return User password.
	 */
	@Override
	public String getPassword() {
		return this.password;
	}

	/**
	 * Get logged user username.
	 * @return User username.
	 */
	@Override
	public String getUsername() {
		return this.username;
	}

	/**
	 * Check if account is non expired.
	 * @return True.
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * Check if account is non locked.
	 * @return True.
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * Check if Credentials non expired.
	 * @return True.
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Check if user is enabled.
	 * @return True.
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * Add role for user in webcredentials object.
	 * @param role new role for user.
	 */
	public void addRole(String role) {
		this.roles.add(role);
	}

}
