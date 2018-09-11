package ascloud.apple.feign.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ascloud.apple.eureka.client.modl.FooModel;
import ascloud.apple.feign.client.FooClient;

@RestController
public class TestController {

	@Autowired
	private FooClient fooClient;

	@RequestMapping("/test")
	public ResponseEntity<FooModel> test() {
		return this.fooClient.foo();
	}

}
