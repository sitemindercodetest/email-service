package com.siteminder.emailservice;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmailServiceApplication {
    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(
            AmazonSQSAsync amazonSqsAsync) {
        return new QueueMessagingTemplate(amazonSqsAsync);
    }

    public static void main(String[] args) {
        SpringApplication.run(EmailServiceApplication.class, args);
    }
}
