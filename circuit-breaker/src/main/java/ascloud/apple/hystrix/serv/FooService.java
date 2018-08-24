package ascloud.apple.hystrix.serv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class FooService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${ascloud.apple.service.foo.url}")
	private String url;

	@HystrixCommand(fallbackMethod = "fooFallback")
	public String foo() {
		return this.restTemplate.getForObject(url, String.class);
	}

	public String fooFallback() {
		return "no service";
	}

}
