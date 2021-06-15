package com.ngohoangbach.demo.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.ngohoangbach.demo.Demo2Application;
import com.ngohoangbach.demo.dao.ProductRepo;
import com.ngohoangbach.demo.entity.Product;

@DataJpaTest

@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryTest {
	
	@Container
	MySQLContainer mySqlContainer = new MySQLContainer("mysql:latest")
	.withDatabaseName("coffee-shop-ver2")
	.withUsername("springstudent")
	.withPassword("springpassword");
	
	@Autowired
	private ProductRepo productRepository;
	
	@Test
	public void shouldSaveProduct() {
		Product expectedProduct = new Product( "Test Product", 25, "image/url");
		Product actualProduct = productRepository.save(expectedProduct);
		assertThat(actualProduct).usingRecursiveComparison()
				.ignoringFields("id").isEqualTo(expectedProduct);
	}
}
