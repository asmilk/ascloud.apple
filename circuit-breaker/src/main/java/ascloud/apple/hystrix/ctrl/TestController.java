package ascloud.apple.hystrix.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ascloud.apple.eureka.client.modl.FooModel;
import ascloud.apple.hystrix.serv.FooService;

@RestController
public class TestController {

	@Autowired
	private FooService fooService;

	@RequestMapping("/test")
	public ResponseEntity<FooModel> test() {
		return this.fooService.foo();
	}

}
