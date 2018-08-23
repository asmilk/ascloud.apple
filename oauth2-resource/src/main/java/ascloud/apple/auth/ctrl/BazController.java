package ascloud.apple.auth.ctrl;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BazController {
	
	private static final Logger LOG = LoggerFactory.getLogger(BazController.class);
	
	@RequestMapping("/baz")
	public String baz(HttpServletRequest request) {
		String authType = request.getAuthType();
		Principal principal = request.getUserPrincipal();
		LOG.info("authType:{}", authType);
		LOG.info("principal:{}", principal);
		return "qux";
	} 

}
