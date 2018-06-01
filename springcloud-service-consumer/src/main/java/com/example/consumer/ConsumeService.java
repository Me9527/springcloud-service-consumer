package com.example.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ConsumeService {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	LoadBalancerClient loadBalancerClient;
	
	@HystrixCommand(fallbackMethod = "fallback")
	public String consumer2() throws InterruptedException {
		Thread.sleep(6000L);
		ServiceInstance serviceInstance = loadBalancerClient.choose("service-provide-01");
		String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/user/getUser/2";
		System.out.println(url);
		return restTemplate.getForObject(url, String.class);
	}

	public String fallback() {
		return "fallbck123";
	}
}
