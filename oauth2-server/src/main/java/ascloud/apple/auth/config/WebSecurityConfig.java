package ascloud.apple.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import ascloud.apple.auth.security.FormLoginFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String REMEMBER_ME_KEY = "ascloudAppleOauth2Server";

	@Autowired
	private OAuth2LogoutSuccessHandler oAuth2LogoutSuccessHandler;

	@Autowired
	private RoleHierarchy roleHierarchy;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices(REMEMBER_ME_KEY,
				this.userDetailsService);
		rememberMeServices.setTokenValiditySeconds(300);
		
		FormLoginFilter formLoginFilter = new FormLoginFilter("/oauth/login");
		formLoginFilter.setAuthenticationManager(super.authenticationManagerBean());
		formLoginFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/oauth/login"));
		formLoginFilter.setRememberMeServices(rememberMeServices);

		http//				
				.antMatcher("/oauth/**").authorizeRequests().anyRequest().authenticated().and()//
				.formLogin().loginPage("/oauth/login").permitAll().and()//
				.addFilterBefore(formLoginFilter, UsernamePasswordAuthenticationFilter.class)//
				.csrf().disable()
				.rememberMe().rememberMeServices(rememberMeServices).and()//
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/oauth/logout"))
				.logoutSuccessHandler(this.oAuth2LogoutSuccessHandler);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth//
				.authenticationProvider(new RememberMeAuthenticationProvider(REMEMBER_ME_KEY)).eraseCredentials(true)
				.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(this.roleHierarchy);
		web.expressionHandler(expressionHandler).ignoring().antMatchers("/actuator/**");
	}

}
