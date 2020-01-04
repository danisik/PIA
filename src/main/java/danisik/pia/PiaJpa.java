package danisik.pia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import danisik.pia.configuration.AppConfig;
import danisik.pia.configuration.DbConfig;

/**
 * Main class.
 */
@SpringBootApplication
@Import({
	AppConfig.class,
	DbConfig.class
})
public class PiaJpa {

	/**
	 * Main method.
	 * @param args Arguments for app.
	 */
	public static void main(String[] args) {
		SpringApplication.run(PiaJpa.class, args);
	}

}
