package ascloud.apple.netflix.resc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import ascloud.apple.netflix.modl.FooModel;

public interface FooResource {

	@RequestMapping("/foo")
	ResponseEntity<FooModel> foo();

}
