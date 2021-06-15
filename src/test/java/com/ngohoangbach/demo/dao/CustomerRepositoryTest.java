package com.ngohoangbach.demo.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.ngohoangbach.demo.dao.CustomerRepo;
import com.ngohoangbach.demo.entity.Customer;
import com.ngohoangbach.demo.entity.Order;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CustomerRepositoryTest {
	
	MySQLContainer mySqlContainer = new MySQLContainer("mysql:latest")
				.withDatabaseName("coffee-shop-ver2")
				.withUsername("springstudent")
				.withPassword("springpassword");
	
	@Autowired
	private CustomerRepo customerRepository;
	
	@Test
	public void shouldSaveCustomer() {
		Customer expectedCustomer = new Customer(132, new Order(), "NHBach", "123ASVS");
		Customer actualCustomer = customerRepository.save(expectedCustomer);
		assertThat(actualCustomer).usingRecursiveComparison()
			.ignoringFields("id").isEqualTo(expectedCustomer);
	}
				
}
