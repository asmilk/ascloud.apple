package ascloud.apple.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private OAuth2LogoutSuccessHandler oAuth2LogoutSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http//
				.requestMatchers().antMatchers("/oauth/**", "/login/**", "/logout/**")//
				.and().authorizeRequests().anyRequest().authenticated()//
				.and().formLogin()//
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessHandler(this.oAuth2LogoutSuccessHandler);

	}

}
