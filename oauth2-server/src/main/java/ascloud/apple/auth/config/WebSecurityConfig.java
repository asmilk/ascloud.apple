package ascloud.apple.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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
		http//
				.antMatcher("/oauth/**").authorizeRequests().anyRequest().authenticated().and()//
				.formLogin().loginPage("/oauth/login").permitAll().and()//
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/oauth/logout"))
				.logoutSuccessHandler(this.oAuth2LogoutSuccessHandler);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth//
				.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(this.roleHierarchy);
		web.expressionHandler(expressionHandler).ignoring().antMatchers("/actuator/**");
	}

}
