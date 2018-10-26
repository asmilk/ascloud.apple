package ascloud.apple.auth.ctrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import ascloud.apple.auth.entity.UserEntity;

@Controller
@RequestMapping("/oauth")
public class OAuth2Controller {

	private static final Logger LOG = LoggerFactory.getLogger(OAuth2Controller.class);

	@GetMapping("/login")
	public String login(@ModelAttribute("user") UserEntity user,
			@SessionAttribute("SPRING_SECURITY_SAVED_REQUEST") DefaultSavedRequest savedRequest) {
		String view = "login";
		LOG.info("savedRequest:{}", savedRequest);
		String[] redirectUrl = savedRequest.getParameterValues("redirect_uri");
		LOG.info("redirectUrl:{}", redirectUrl[0]);
		if("http://zuul.proxy:8080/login".equalsIgnoreCase(redirectUrl[0])) {
			view = "login/zuul.proxy";
		} else if ("http://oauth2.com/login/oauth2/code/uaa".equalsIgnoreCase(redirectUrl[0])) {
			view = "login/oauth2.com";
		}
		return view;
	}

}
