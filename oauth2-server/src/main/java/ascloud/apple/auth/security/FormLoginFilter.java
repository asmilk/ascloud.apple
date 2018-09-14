package ascloud.apple.auth.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
		return super.attemptAuthentication(request, response);
	}

}
