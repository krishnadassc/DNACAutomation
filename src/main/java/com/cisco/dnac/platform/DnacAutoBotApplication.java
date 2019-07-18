package com.cisco.dnac.platform;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.common.base.Predicates;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAutoConfiguration(
		exclude = { org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class , DataSourceAutoConfiguration.class })
@ComponentScan(basePackages = "com")
@PropertySource("${app.config.location}")
@EnableWebMvc
@EnableScheduling
@EnableSwagger2
public class DnacAutoBotApplication {

	@Value("${db.mongo.host}")
	protected String mongoHost;

	@Value("${db.mongo.dbName}")
	protected String mongoDBName;

	@Value("${db.mongo.port}")
	protected String port;

	@Value("${db.mongo.password}")
	protected String password;

	@Value("${db.mongo.username}")
	protected String username;

	@Value("${db.mongo.authMechanism}")
	protected String authMechanism;


	@Autowired
	protected Environment env;
	Logger logger = Logger.getLogger(DnacAutoBotApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(DnacAutoBotApplication.class, args);
	}

	@Bean
	public Docket swaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(Predicates.or(PathSelectors.ant("/rest/*/*/*")

						, PathSelectors.regex("/rest/*")))
				.build().apiInfo(getSwaggerApiInfo());
	}
	
	private ApiInfo getSwaggerApiInfo() {
		return new ApiInfo("Mediagateway RESTful API Documentation", null, "1.0", null,
				new Contact(null, null, null), null, null);
	}
	
	public MongoTemplate mongoTemplate() {
		logger.info("app.config.location: " + env.getProperty("app.config.location"));
		logger.info("db.mongo.port: " + env.getProperty("db.mongo.port"));
		logger.info("authMechanism: " + authMechanism);
		logger.info("Mongo Connection Details: " + mongoHost + port + mongoDBName + username + password);
		List<ServerAddress> seeds = new ArrayList();
		String[] hostAddress = mongoHost.split(",");
		if (hostAddress.length > 1) {
			for (String host : hostAddress) {
				String[] hostDetail = host.split(":");
				seeds.add(new ServerAddress(hostDetail[0], Integer.parseInt(hostDetail[1])));
			}
		} else {
			seeds.add(new ServerAddress(mongoHost, Integer.parseInt(port)));
		}
		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		MongoClient mongo = null;
		if (username == null || username.isEmpty()) {
			mongo = new MongoClient(seeds);
		} else {
			if ("CR".equalsIgnoreCase(authMechanism)) {
				credentials.add(MongoCredential.createMongoCRCredential(username, mongoDBName,
						password.toCharArray()));
			} else {
				credentials.add(MongoCredential.createScramSha1Credential(username, mongoDBName,
						password.toCharArray()));
			}
			mongo = new MongoClient(seeds, credentials);
		}
		MongoTemplate mt = new MongoTemplate(mongo, mongoDBName);
		return mt;
	}
}
