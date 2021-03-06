package danisik.pia.configuration;

import danisik.pia.InitConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Class providing application configuration (security, paths to templates, ...).
 */
@Configuration
@ComponentScan
@EnableWebMvc
@EnableWebSecurity
@EnableAutoConfiguration
public class AppConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Set prefix and sufix for all templates.
	 * @return Template resolver with setted prefix and sufix.
	 */
    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.getApplicationContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // Template cache is true by default. Set to false if you want
        // templates to be automatically updated when modified.
        templateResolver.setCacheable(false);
        return templateResolver;
    }

	/**
	 * Set engine for thymeleaf templates.
	 * @return Template engine.
	 */
	@Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.addDialect("sec", new SpringSecurityDialect());
        return templateEngine;
    }

	/**
	 * Set template engine into resolver.
	 * @return Thymeleaf resolver for templates.
	 */
	@Bean
    public ThymeleafViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;
    }

	/**
	 * Create password encoder.
	 * @return Instance of BCryptPasswordEncoder.
	 */
	@Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }

	/**
	 * Security configuration and login and logout security.
	 * @param http HttpSecurity instance for web.
	 * @throws Exception exception.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.mvcMatchers("/login").permitAll()
			.regexMatchers(HttpMethod.GET, "^/css/.*", "^/webfonts/.*").permitAll()
				.antMatchers("/").permitAll()
				.antMatchers("/services").permitAll()
				.antMatchers("/info").permitAll()
				.antMatchers("/user/**").authenticated()
				.antMatchers("/invoices/info").authenticated()
				.antMatchers("/invoices/invoice/info").authenticated()
				.antMatchers("/invoices/**").hasAuthority(InitConstants.DEFAULT_ROLE_PURSER_CODE)
				.antMatchers("/admin/**").hasAuthority(InitConstants.DEFAULT_ROLE_ADMIN_CODE)
				.antMatchers("/addressbook/**").hasAuthority(InitConstants.DEFAULT_ROLE_PURSER_CODE)
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/login")
			.permitAll()
			.defaultSuccessUrl("/")
			.and()
		.logout()
				.logoutSuccessUrl("/")
			.logoutRequestMatcher(new RegexRequestMatcher("/logout", "GET"))
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.permitAll();
	}

	/**
	 * Set user details service and password encoder for authentication web manager.
	 * @param auth Authentication builder.
	 * @throws Exception exception.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(this.userDetailsService)
		.passwordEncoder(passwordEncoder());
	}

	/**
	 * Add resource handlers for css, webfonts and js.
	 * @param registry Resource handler registers.
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/css/**")
		.addResourceLocations("/css/");
		registry
		.addResourceHandler("/webfonts/**")
		.addResourceLocations("/webfonts/");
		registry
				.addResourceHandler("/js/**")
				.addResourceLocations("/js/");
	}

}
