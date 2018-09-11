package ascloud.apple.hystrix.serv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import ascloud.apple.eureka.client.modl.FooModel;

@Service
public class FooService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${ascloud.apple.service.foo.url}")
	private String url;

	@HystrixCommand(fallbackMethod = "fooFallback")
	public ResponseEntity<FooModel> foo() {
		return this.restTemplate.getForEntity(this.url, FooModel.class);
	}

	public ResponseEntity<FooModel> fooFallback() {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).header("msg", "no service:" + this.url).build();
	}

}
