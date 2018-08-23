package ascloud.apple.eureka.client.resc;

import org.springframework.web.bind.annotation.RequestMapping;

public interface FooResource {

	@RequestMapping("/foo")
	String foo();

}
