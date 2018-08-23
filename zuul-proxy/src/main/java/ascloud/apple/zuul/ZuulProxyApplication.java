package ascloud.apple.zuul;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ZuulProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulProxyApplication.class, args);
	}

	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
		SpringSecurityDialect springSecurityDialect = new SpringSecurityDialect();
		Set<IDialect> additionalDialects = new HashSet<>();
		additionalDialects.add(springSecurityDialect);
		springTemplateEngine.setAdditionalDialects(additionalDialects);
		return springTemplateEngine;
	}

	@Configuration
	@EnableOAuth2Sso
	static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http//
					.antMatcher("/**").authorizeRequests()//
					.antMatchers("/", "/login**").permitAll()//
					.anyRequest().authenticated().and()//
					.logout().deleteCookies("JSESSIONID").invalidateHttpSession(true)
					.logoutSuccessUrl("http://oauth2.server:8084/oauth/revoke_token");
		}

		@Bean
		public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
				OAuth2ProtectedResourceDetails details) {
			return new OAuth2RestTemplate(details, oauth2ClientContext);
		}

	}

}
