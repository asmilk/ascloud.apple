package ascloud.apple.auth.ctrl;

import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	ConsumerTokenServices tokenServices;

	@RequestMapping("/revoke_token")
	public boolean revokeToken(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		boolean result = false;
		Principal principal = request.getUserPrincipal();
		if (principal instanceof OAuth2Authentication) {
			OAuth2Authentication authentication = (OAuth2Authentication) principal;
			Object details = authentication.getDetails();
			if (details instanceof OAuth2AuthenticationDetails) {
				OAuth2AuthenticationDetails authenticationDetails = (OAuth2AuthenticationDetails) details;
				String tokenValue = authenticationDetails.getTokenValue();
				LOG.info("tokenValue:{}", tokenValue);
				result = this.tokenServices.revokeToken(tokenValue);
				new CookieClearingLogoutHandler("JSESSIONID").logout(request, response, null);
				new SecurityContextLogoutHandler().logout(request, response, null);
				request.logout();
			}
		}
		
		return result;
	}
	
	@RequestMapping("/user_info")
	public Principal userInfo(Principal principal) {
		return principal;
	}
	
	@RequestMapping("/hello")
	public boolean hello(HttpServletRequest request) throws ServletException {
		Principal principal = request.getUserPrincipal();
		request.logout();
		LOG.info("principal:{}", principal);
		return false;//"hello:" + principal.getName();
	}

}
