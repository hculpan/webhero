package org.culpan.webhero;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebHeroApplication {
	@Value("${amazon.dynamodb.endpoint}")
	private String amazonDynamoDBEndpoint;

	@Value("${amazon.aws.accesskey}")
	private String amazonAWSAccessKey = "omit";

	@Value("${amazon.aws.secretkey}")
	private String amazonAWSSecretKey = "omit";

	public static void main(String[] args) {
		SpringApplication.run(WebHeroApplication.class, args);
	}

	@Bean
	public DB db() {
		return DBMaker.fileDB("heroes.db").transactionEnable().make();
	}
}
