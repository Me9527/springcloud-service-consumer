package com.example.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DcController {

	@Autowired
	LoadBalancerClient loadBalancerClient;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	ConsumeService consumeService;
	
	
	@GetMapping("/consumer")
	public String dc() {
		ServiceInstance serviceInstance = loadBalancerClient.choose("service-provide-01");
		String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/user/getUser/2";
		System.out.println(url);
		return restTemplate.getForObject(url, String.class);
	}

	@GetMapping("/consumer2")
	public String consumer2() throws InterruptedException {
		String s = consumeService.consumer2();
		return  s;
	}
	
	
}