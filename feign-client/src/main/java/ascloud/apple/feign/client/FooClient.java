package ascloud.apple.feign.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import ascloud.apple.eureka.client.modl.FooModel;
import ascloud.apple.eureka.client.resc.FooResource;
import ascloud.apple.feign.client.FooClient.FooClientFallbackFactory;
import feign.hystrix.FallbackFactory;

@FeignClient(name = "eureka-client", fallbackFactory = FooClientFallbackFactory.class)
public interface FooClient extends FooResource {

	@Component
	static class FooClientFallbackFactory implements FallbackFactory<FooClient> {

		@Override
		public FooClient create(Throwable cause) {

			return new FooClient() {

				@Override
				public ResponseEntity<FooModel> foo() {

					return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).header("msg", cause.getMessage()).build();  //new FooModel(0L, "message:" + cause.getMessage());
				}
			};
		}

	}

}