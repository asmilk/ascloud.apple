package ascloud.apple.auth.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class FormLoginFilter extends UsernamePasswordAuthenticationFilter {

	private static final Logger LOG = LoggerFactory.getLogger(FormLoginFilter.class);

	public FormLoginFilter(String filterProcessesUrl) {
		super();
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(filterProcessesUrl, "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		LOG.info("====FormLoginFilter.attemptAuthentication====");
		String captcha = request.getParameter("captcha");
		LOG.info("captcha:{}", captcha);

		String gateway = request.getParameter("gateway");
		LOG.info("gateway:{}", gateway);
		if ("oauth2.com".equalsIgnoreCase(gateway)) {
			AuthenticationSuccessHandler successHandler = this.getSuccessHandler();
			LOG.info("successHandler:{}", successHandler);
			
			if (successHandler instanceof SavedRequestAwareAuthenticationSuccessHandler) {
				String state = request.getParameter("state");
				LOG.info("state:{}", state);
				
				DefaultSavedRequest savedRequest = new DefaultSavedRequest.Builder().setScheme("http")
						.setServerName("oauth2.server").setServerPort(8822).setRequestURI("/oauth/authorize")
						.setQueryString("response_type=code&client_id=uaa&scope=read&state=" + state
								+ "&redirect_uri=http://oauth2.com/login/oauth2/code/uaa")
						.build();
				String redirectUrl = savedRequest.getRedirectUrl();
				LOG.info("redirectUrl:{}", redirectUrl);
				request.getSession().setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequest);
			}
		}

		return super.attemptAuthentication(request, response);
	}

}
