/**
 * 
 */
package com.jci.partmsplm;

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
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
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
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class PLMPartmsApplication {

	private static final Logger LOG = LoggerFactory.getLogger(PLMPartmsApplication.class);

	@Autowired
	private DiscoveryClient discoveryClient;

	/*
	 * @Bean
	 * 
	 * @LoadBalanced RestTemplate restTemplate() { return new RestTemplate(); }
	 */

	RestTemplate restTemplate = new RestTemplate();

	/*
	 * @Autowired private RestTemplate restTemplate;
	 */

	@RequestMapping("/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}

	@RequestMapping(value = "/post1")
	public @ResponseBody StringBuilder post1() throws URISyntaxException {
		StringBuilder response = new StringBuilder();
		String result = null;
		LOG.info("Starting to send to BOM Microservice from post1==============>>>");
		LOG.info("Starting call 1====>");
		List<ServiceInstance> serviceInstance = discoveryClient.getInstances("plm-bom-ms");
		ServiceInstance bomInstance = serviceInstance.get(0);
		String urlString = "http://" + bomInstance.getHost() + ":" + Integer.toString(bomInstance.getPort())
				+ "/bomPost";
		try {
			MultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
			LOG.info("URL String: " + urlString);
			mvm.add("abc", "Hello BOM 1");
			result = restTemplate.postForObject(urlString, mvm, String.class);
			response.append("successPost 2 = " + result);
			response.append(System.getProperty("line.separator"));
		} catch (Exception e) {
			LOG.info("In exception for post1 where urlstring is: " + urlString + e);
			response.append(System.getProperty("line.separator"));
			response.append("failPost1 = " + printException(e));
			response.append(System.getProperty("line.separator"));
		}
		return response;
	}

	@RequestMapping(value = "/post2")
	public @ResponseBody StringBuilder post2() throws URISyntaxException {
		StringBuilder response = new StringBuilder();
		String result = null;
		LOG.info("Starting to send to BOM Microservice from post2==============>>>");
		LOG.info("Starting call 2====>");
		// List<ServiceInstance> serviceInstance =
		// discoveryClient.getInstances("plm-bom-ms");
		// ServiceInstance bomInstance = serviceInstance.get(0);
		String urlString = "http://plm-bom-ms/bomPost";
		try {
			MultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
			LOG.info("URL String: " + urlString);
			mvm.add("abc", "Hello BOM 2");
			result = restTemplate.postForObject(urlString, mvm, String.class);
			response.append("successPost 2 = " + result);
			response.append(System.getProperty("line.separator"));
		} catch (Exception e) {
			LOG.info("In exception for post2 where urlstring is: " + urlString + e);
			response.append(System.getProperty("line.separator"));
			response.append("failPost2 = " + printException(e));
			response.append(System.getProperty("line.separator"));
		}
		return response;
	}

	@RequestMapping(value = "/post3")
	public @ResponseBody StringBuilder post3() throws URISyntaxException {
		StringBuilder response = new StringBuilder();
		String result = null;
		LOG.info("Starting to send to BOM Microservice from post3==============>>>");
		LOG.info("Starting call 3====>");
		// List<ServiceInstance> serviceInstance =
		// discoveryClient.getInstances("plm-bom-ms");
		// ServiceInstance bomInstance = serviceInstance.get(0);
		String urlString = "http://PLM-BOM-MS/bomPost";
		try {
			MultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
			LOG.info("URL String: " + urlString);
			mvm.add("abc", "Hello BOM 3");
			result = restTemplate.postForObject(urlString, mvm, String.class);
			response.append("successPost 3 = " + result);
			response.append(System.getProperty("line.separator"));
		} catch (Exception e) {
			LOG.info("In exception for post3 where urlstring is: " + urlString + e);
			response.append(System.getProperty("line.separator"));
			response.append("failPost3 = " + printException(e));
			response.append(System.getProperty("line.separator"));
		}
		return response;
	}

	@RequestMapping(value = "/post4")
	public @ResponseBody StringBuilder post4() throws URISyntaxException {
		StringBuilder response = new StringBuilder();
		String result = null;
		LOG.info("Starting to send to BOM Microservice from post4==============>>>");
		LOG.info("Starting call 4====>");
		// List<ServiceInstance> serviceInstance =
		// discoveryClient.getInstances("plm-bom-ms");
		// ServiceInstance bomInstance = serviceInstance.get(0);
		String urlString = "http://bommscontrols.azurewebsites.net/bomPost";
		try {
			MultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
			LOG.info("URL String: " + urlString);
			mvm.add("abc", "Hello BOM 4");
			result = restTemplate.postForObject(urlString, mvm, String.class);
			response.append("successPost 4 = " + result);
			response.append(System.getProperty("line.separator"));
		} catch (Exception e) {
			LOG.info("In exception for post4 where urlstring is: " + urlString + e);
			response.append(System.getProperty("line.separator"));
			response.append("failPost4 = " + printException(e));
			response.append(System.getProperty("line.separator"));
		}
		return response;
	}

	@RequestMapping(value = "/get1")
	public @ResponseBody StringBuilder get1() throws URISyntaxException {
		StringBuilder response = new StringBuilder();
		String result = null;
		LOG.info("Starting to send to BOM Microservice from get1==============>>>");
		LOG.info("Starting call====>");
		List<ServiceInstance> serviceInstance = discoveryClient.getInstances("plm-bom-ms");
		ServiceInstance bomInstance = serviceInstance.get(0);
		String urlString = bomInstance.getUri() + "/bomGet";
		try {
			LOG.info("URL String: " + urlString);
			result = restTemplate.getForObject(urlString, String.class);
			response.append("successGet = " + result);
			response.append(System.getProperty("line.separator"));
		} catch (Exception e) {
			LOG.info("In exception for get1 where urlstring is: " + urlString + e);
			response.append(System.getProperty("line.separator"));
			response.append("failGet1 = " + printException(e));
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

	@RequestMapping(value = "/partPost", method = { RequestMethod.POST })
	public String partPost(@RequestParam(value = "abc") String param1) {
		LOG.info("in plm-part-ms post method: " + param1);
		return "in plm-part-ms post method";
	}

	@RequestMapping(value = "/partGet", method = { RequestMethod.GET })
	public String partGet() {
		LOG.info("in plm-part-ms get method ");
		return "in plm-part-ms get method";
	}

	public static void main(String[] args) {

		SpringApplication.run(PLMPartmsApplication.class, args);

	}
}
