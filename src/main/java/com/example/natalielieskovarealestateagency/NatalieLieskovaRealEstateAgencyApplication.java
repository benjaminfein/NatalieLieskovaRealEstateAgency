package com.example.natalielieskovarealestateagency;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.natalielieskovarealestateagency.repository")
@EntityScan(basePackages = "com.example.natalielieskovarealestateagency.model")
public class NatalieLieskovaRealEstateAgencyApplication {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
//                .directory("/root/OdessaDomik/")
				.filename(".env")
				.ignoreIfMissing()
				.load();

		Map<String, Object> envProps = new HashMap<>();
		dotenv.entries().forEach(entry -> {
			envProps.put(entry.getKey(), entry.getValue());
			System.setProperty(entry.getKey(), entry.getValue()); // ← полезно для отладки
		});

		dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
		System.out.println("frontend_url from System: " + System.getProperty("frontend_url"));
		SpringApplication app = new SpringApplication(NatalieLieskovaRealEstateAgencyApplication.class);
		app.setDefaultProperties(envProps);
		app.run(args);
	}

}
