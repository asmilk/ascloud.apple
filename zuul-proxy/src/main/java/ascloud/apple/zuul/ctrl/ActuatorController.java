package ascloud.apple.zuul.ctrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actuator")
public class ActuatorController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ActuatorController.class);
	
	@RequestMapping("/test")
	public String test(@RequestParam("provider") String provider, @RequestParam("name") String name) {
		LOG.info("provider:{}", provider);
		LOG.info("name:{}", name);
		return "test";
	}

}
