package ascloud.apple.eureka.client.resc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import ascloud.apple.eureka.client.modl.FooModel;

public interface FooResource {

	@RequestMapping("/foo")
	ResponseEntity<FooModel> foo();

}
