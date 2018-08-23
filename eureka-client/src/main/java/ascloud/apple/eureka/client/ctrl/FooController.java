package ascloud.apple.eureka.client.ctrl;

import org.springframework.web.bind.annotation.RestController;

import ascloud.apple.eureka.client.resc.FooResource;

@RestController
public class FooController implements FooResource {

	@Override
	public String foo() {
		return "bar";
	}

}
