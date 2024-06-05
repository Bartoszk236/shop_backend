package com.shop.shop;

import com.shop.shop.entity.Product;
import com.shop.shop.repository.ProductRepository;
import com.shop.shop.repository.UserInformationRepository;
import com.shop.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

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

//	@Bean
//	public CommandLineRunner loadSampleData(ProductRepository productRepository) {
//		return args -> {
//			Product product1 = new Product();
//			product1.setName("Product 1");
//			product1.setDescription("Description for Product 1");
//			product1.setPrice(new BigDecimal("10.00"));
//
//			Product product2 = new Product();
//			product2.setName("Product 2");
//			product2.setDescription("Description for Product 2");
//			product2.setPrice(new BigDecimal("20.00"));
//
//			Product product3 = new Product();
//			product3.setName("Product 3");
//			product3.setDescription("Description for Product 3");
//			product3.setPrice(new BigDecimal("30.00"));
//
//			productRepository.save(product1);
//			productRepository.save(product2);
//			productRepository.save(product3);
//		};
//	}

}
