package ascloud.apple.zuul.ctrl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private OAuth2RestTemplate oAuth2RestTemplate;
	
	@Value("${security.oauth2.resource.user-info-uri}")
	private String userInfoUrl;
	
	@RequestMapping("/user_info")
	public String userInfo() {
		String result = this.oAuth2RestTemplate.getForObject(userInfoUrl, String.class);
		LOG.info("result:{}", result);
		return "index";
	}

	@RequestMapping({ "/", "/index" })
	public String index() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication) {
			String name = authentication.getName();
			LOG.info("name:{}", name);
			Object principal = authentication.getPrincipal();
			LOG.info("principal:{}", principal);
			Object details = authentication.getDetails();
			if (details instanceof OAuth2AuthenticationDetails) {
				OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) details;

				Object decodedDetails = oAuth2AuthenticationDetails.getDecodedDetails();
				String remoteAddress = oAuth2AuthenticationDetails.getRemoteAddress();
				String sessionId = oAuth2AuthenticationDetails.getSessionId();
				String tokenType = oAuth2AuthenticationDetails.getTokenType();
				String tokenValue = oAuth2AuthenticationDetails.getTokenValue();

				LOG.info("decodedDetails:{}", decodedDetails);
				LOG.info("remoteAddress:{}", remoteAddress);
				LOG.info("sessionId:{}", sessionId);
				LOG.info("tokenType:{}", tokenType);
				LOG.info("tokenValue:{}", tokenValue);
			}
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			for (GrantedAuthority grantedAuthority : authorities) {
				LOG.info("authority:{}", grantedAuthority.getAuthority());
			}
			Object credentials = authentication.getCredentials();
			LOG.info("credentials:{}", credentials);

		}
		return "index";
	}

}
