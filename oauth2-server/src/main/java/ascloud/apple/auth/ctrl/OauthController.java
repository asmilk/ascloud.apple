package ascloud.apple.auth.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/oauth")
public class OauthController {

	@RequestMapping("/revoke_token")
	public String revokeToken(HttpServletRequest request) {
		new SecurityContextLogoutHandler().logout(request, null, null);
		return "redirect:" + request.getHeader("referer");
	}

}
