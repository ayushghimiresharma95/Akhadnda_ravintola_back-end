package fi.ShoppingOnline.Identityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class IdentityServiceApplications {
 public static void main(String[] args) {
    SpringApplication.run(IdentityServiceApplications.class, args) ;
 }
}
