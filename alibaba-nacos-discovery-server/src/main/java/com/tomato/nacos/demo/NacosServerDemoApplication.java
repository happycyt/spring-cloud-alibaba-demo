package com.tomato.nacos.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosServerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosServerDemoApplication.class, args);
	}

	@RestController
	static class TestController {
		@GetMapping("/test")
		public String hello(@RequestParam String name) {
			return "hello " + name;
		}
	}
}
