/**
 * 
 */
package com.jci.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * The Main Spring Boot Application class that starts the Eureka discovery
 * server since the application is annotated with {@link EnableEurekaServer}.
 * <br>
 * <br>
 * 
 * Note that all these annotations work in conjunction with properties defined
 * in the external configuration files specified by the config server.
 * 
 * @author jci
 */

@EnableEurekaServer
@SpringBootApplication
public class Application {
	
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		LOG.info("### Starting registry.Application.main ####");
		SpringApplication.run(Application.class, args);
		LOG.info("### Ending registry.Application.main ####");
	}
}
