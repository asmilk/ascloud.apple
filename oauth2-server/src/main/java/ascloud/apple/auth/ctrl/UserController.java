package ascloud.apple.auth.ctrl;

import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	ConsumerTokenServices consumerTokenServices;

	@RequestMapping("/revoke_token")
	public boolean revokeToken(Principal principal) throws ServletException {
		boolean result = false;
		if (principal instanceof OAuth2Authentication) {
			OAuth2Authentication authentication = (OAuth2Authentication) principal;
			Object details = authentication.getDetails();
			if (details instanceof OAuth2AuthenticationDetails) {
				OAuth2AuthenticationDetails authenticationDetails = (OAuth2AuthenticationDetails) details;
				String tokenValue = authenticationDetails.getTokenValue();
				LOG.info("tokenValue:{}", tokenValue);
				result = this.consumerTokenServices.revokeToken(tokenValue);
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
		return false;
	}

}
