package ascloud.apple.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private OAuth2LogoutHandler oAuth2LogoutHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.requestMatchers().antMatchers("/oauth/**", "/login/**", "/logout/**").and().authorizeRequests()
//				.antMatchers("/oauth/**").authenticated().and().formLogin().permitAll();

		http//
				.requestMatchers().antMatchers("/oauth/**", "/login/**", "/logout/**")//
				.and().authorizeRequests().antMatchers("/oauth/**").authenticated()//
				.and().formLogin().permitAll()/*.and().logout().deleteCookies("JSESSIONID").invalidateHttpSession(true)
				.clearAuthentication(true).addLogoutHandler(this.oAuth2LogoutHandler)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))*/;
	}

}
