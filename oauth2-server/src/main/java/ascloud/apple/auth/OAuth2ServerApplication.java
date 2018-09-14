package ascloud.apple.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;

@SpringBootApplication
@EnableEurekaClient
@EnableResourceServer
public class OAuth2ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2ServerApplication.class, args);
	}

	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
		springTemplateEngine.addDialect(new SpringSecurityDialect());
		return springTemplateEngine;
	}

	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_STAFF and ROLE_STAFF > ROLE_USER");
		return roleHierarchy;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
		userDetailsService.createUser(User.withUsername("admin").password("{noop}123456").roles("ADMIN").build());
		userDetailsService.createUser(User.withUsername("staff").password("{noop}123456").roles("STAFF").build());
		userDetailsService.createUser(User.withUsername("user").password("{noop}123456").roles("USER").build());
		userDetailsService.createUser(User.withUsername("guest").password("{noop}123456").roles("GUEST").build());
		userDetailsService.createUser(
				User.withUsername("hacker").password("{noop}123456").roles("HACKER").disabled(true).build());
		return userDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Configuration
	@EnableAuthorizationServer
	static class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;

		@Autowired
		private UserDetailsService userDetailsService;

		@Autowired
		private PasswordEncoder passwordEncoder;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints//
					.authenticationManager(this.authenticationManager).userDetailsService(this.userDetailsService);
		}

		@Override
		public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
			security//
					.passwordEncoder(this.passwordEncoder)//
					.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients//
					.inMemory().withClient("uaa").secret("{noop}s3cr3t").scopes("openid")
					.authorizedGrantTypes("authorization_code", "password", "refresh_token").autoApprove(true)
					.accessTokenValiditySeconds(300).refreshTokenValiditySeconds(600);
		}

	}
}
