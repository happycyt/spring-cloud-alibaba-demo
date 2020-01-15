package com.tomato.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class NacosClientDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosClientDemoApplication.class, args);
    }

    @RestController
    static class TestController {

        @Autowired
        LoadBalancerClient loadBalancerClient;

        @Autowired
        RestTemplate restTemplate;

        @Autowired
        private WebClient.Builder webClientBuilder;

        @Autowired
        Client client;

        @GetMapping("/test")
        public String test() {
            // 通过spring cloud common中的负载均衡接口选取服务提供节点实现接口调用
            ServiceInstance serviceInstance = loadBalancerClient.choose("alibaba-nacos-discovery-server");
            String url = serviceInstance.getUri() + "/test?name=" + "tomato";
            RestTemplate restTemplate1 = new RestTemplate();
            String result = restTemplate1.getForObject(url, String.class);
            return "Invoke : " + url + ", return : " + result;
        }

        @GetMapping("/test2")
        public String test2() {
            String result = restTemplate.getForObject("http://alibaba-nacos-discovery-server/test?name=tomato", String.class);
            return "Return : " + result;
        }

        @GetMapping("/test3")
        public Mono<String> test3() {
            Mono<String> result = webClientBuilder.build()
                    .get()
                    .uri("http://alibaba-nacos-discovery-server/test?name=tomato")
                    .retrieve()
                    .bodyToMono(String.class);
            return result;
        }

        @Bean
        @LoadBalanced
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }

        @Bean
        @LoadBalanced
        public WebClient.Builder loadBalancedWebClientBuilder() {
            return WebClient.builder();
        }

        @FeignClient("alibaba-nacos-discovery-server")
        interface Client {

            @GetMapping("/test")
            String hello(@RequestParam(name = "name") String name);

        }

        @GetMapping("/test4")
        public String test4() {
            String result = client.hello("tomato4");
            return "Return : " + result;
        }

    }
}
