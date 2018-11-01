package ascloud.apple.hystrix.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ascloud.apple.hystrix.serv.FooService;
import ascloud.apple.netflix.modl.FooModel;

@RestController
public class TestController {

	@Autowired
	private FooService fooService;

	@RequestMapping("/test")
	public ResponseEntity<FooModel> test() {
		return this.fooService.foo();
	}

}
