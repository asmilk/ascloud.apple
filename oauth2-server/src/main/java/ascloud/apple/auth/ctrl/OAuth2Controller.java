package ascloud.apple.auth.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import ascloud.apple.auth.entity.UserEntity;

@Controller
@RequestMapping("/oauth")
public class OAuth2Controller {

	@GetMapping("/login")
	public String login(@ModelAttribute("user") UserEntity user) {
		return "login";
	}

}
