package com.shop.shop;

import com.shop.shop.entity.Product;
import com.shop.shop.repository.ProductRepository;
import com.shop.shop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ShopApplicationTests {

	@Autowired
	private ProductService productService;

	@Test
	void addProduct() {
		Product product = new Product();
		product.setName("Szklanka");
		product.setDescription("Szklanka o pojemności 250ml. Kolor przezroczysty");
		product.setPrice(new BigDecimal("5.99"));
		productService.save(product);
	}

	@Test
	void getAllProduct(){
		List<Product> products = productService.findAll();

		products.forEach(product -> {
			System.out.println(product.getName());
			System.out.println(product.getDescription());
			System.out.println(product.getPrice());
		});
	}

	@Test
	void deleteProduct(){
		productService.deleteById(3L);
	}

	@Test
	void getById(){
		Optional<Product> productOptional = productService.findById(4L);

		if(productOptional.isPresent()) {
			Product product = productOptional.get();
			System.out.println(product.getName());
			System.out.println(product.getDescription());
			System.out.println(product.getPrice());
		} else {
			System.out.println("Produkt nie istnieje");
		}
	}

	@Test
	void registerUser(){

	}

}
