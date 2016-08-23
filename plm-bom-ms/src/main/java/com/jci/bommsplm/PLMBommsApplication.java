/**
 * 
 */
package com.jci.bommsplm;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microsoft.azure.storage.StorageException;

@RestController
@Configuration
@ComponentScan
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableAutoConfiguration
public class PLMBommsApplication {

	private static final Logger LOG = LoggerFactory.getLogger(PLMBommsApplication.class);

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping("/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody StringBuilder bom() throws URISyntaxException {
		StringBuilder response = new StringBuilder();
		String result = null;
		LOG.info("Starting to send to Part Microservice==============>>>");
		LOG.info("Starting call====>");
		List<ServiceInstance> serviceInstance = discoveryClient.getInstances("plm-part-ms");
		ServiceInstance partInstance = serviceInstance.get(0);
		try {
			MultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
			mvm.add("abc", "Hello Part");
			String urlString = "http://" + partInstance.getHost() + ":" + Integer.toString(partInstance.getPort())
					+ "/partPost";
			LOG.info("URL String: " + urlString);
			result = restTemplate.postForObject(urlString, mvm, String.class);
			response.append("successPost = " + result);
			response.append(System.getProperty("line.separator"));
		} catch (Exception e) {
			response.append(System.getProperty("line.separator"));
			response.append("failPost = " + printException(e));
			response.append(System.getProperty("line.separator"));
		}
		try {
			result = restTemplate.getForObject(partInstance.getUri(), String.class);
			response.append("successGet = " + result);
			response.append(System.getProperty("line.separator"));
		} catch (Exception e) {
			response.append(System.getProperty("line.separator"));
			response.append("failGet = " + printException(e));
			response.append(System.getProperty("line.separator"));
		}
		return response;
	}

	public static String printException(Throwable t) {

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		t.printStackTrace(printWriter);
		if (t instanceof StorageException) {
			if (((StorageException) t).getExtendedErrorInformation() != null) {
				System.out.println(String.format("\nError: %s",
						((StorageException) t).getExtendedErrorInformation().getErrorMessage()));
			}
		}
		return (String.format("Exception details:\n%s", stringWriter.toString()));
	}

	@RequestMapping(value = "/bomPost", method = { RequestMethod.POST })
	public String bomPost(@RequestParam(value = "abc") String param1) {
		LOG.info("in plm-bom-ms post method: " + param1);
		return "return response from plm-bom-ms post method";
	}

	@RequestMapping(value = "/bomGet", method = { RequestMethod.GET })
	public String bomGet() {
		LOG.info("in plm-bom-ms get method");
		return "return response from plm-bom-ms get method";
	}

	public static void main(String[] args) {

		SpringApplication.run(PLMBommsApplication.class, args);

	}
}
