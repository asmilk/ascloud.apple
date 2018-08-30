package ascloud.apple.auth.config;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2LogoutHandler implements LogoutHandler {

	private static final Logger LOG = LoggerFactory.getLogger(OAuth2LogoutHandler.class);

	@Autowired
	ConsumerTokenServices tokenServices;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		Principal principal = request.getUserPrincipal();
		LOG.info("principal:{}", principal);
		LOG.info("authentication:{}", authentication);
		String referer = request.getHeader("Referer");
		LOG.info("referer:{}", referer);
//		try {
//			response.sendRedirect(referer);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}
