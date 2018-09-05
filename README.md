# ascloud.apple

1.add below ip and hostname into your hosts file at C:\Windows\System32\drivers\etc\hosts
127.0.0.1 eureka.server
127.0.0.1 oauth2.server
127.0.0.1 eureka.client
127.0.0.1 circuit.breaker
127.0.0.1 feign.client
127.0.0.1 oauth2.resource
127.0.0.1 zuul.proxy

2.run below command from cmd window at each project root dir follow the hostname order
mvn spring-boot:run

3.view http://eureka.server:8761/ check all service

4.view http://zuul.proxy:8080/ and login by below 2 account
user 123456
admin 123456