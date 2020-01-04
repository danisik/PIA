package danisik.pia.configuration;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Class providing database connection.
 */
@Configuration
@EnableTransactionManagement
public class DbConfig {

	/**
	 * Create Datasourcebuilder from configuration properties.
	 * @return Datasource for database.
	 */
	@Bean
	@ConfigurationProperties(prefix = "pia.data")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

}
