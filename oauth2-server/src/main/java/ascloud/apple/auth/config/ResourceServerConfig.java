package ascloud.apple.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private OAuth2LogoutHandler oAuth2LogoutHandler;

	@Autowired
	private OAuth2LogoutSuccessHandler oAuth2LogoutSuccessHandler;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http//
				.requestMatchers().antMatchers("/api/**")//
				.and().authorizeRequests().antMatchers("/api/**").authenticated()//
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
				.addLogoutHandler(this.oAuth2LogoutHandler).deleteCookies("JSESSIONID").invalidateHttpSession(true)
				.clearAuthentication(true).logoutSuccessHandler(this.oAuth2LogoutSuccessHandler);
	}

}
