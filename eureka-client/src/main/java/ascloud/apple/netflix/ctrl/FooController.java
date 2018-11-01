package ascloud.apple.netflix.ctrl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ascloud.apple.netflix.modl.FooModel;
import ascloud.apple.netflix.resc.FooResource;

@RestController
public class FooController implements FooResource {

	@Override
	public ResponseEntity<FooModel> foo() {
		return ResponseEntity.ok(new FooModel(1L, "bar"));
	}

}
