package com.shop.shop;

import com.shop.shop.repository.UserInformationRepository;
import com.shop.shop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Bean
	public CommandLineRunner clearDataBase(UserInformationRepository userInformationRepository){
		return args -> {
			userInformationRepository.deleteAll();
		};
	}

}
