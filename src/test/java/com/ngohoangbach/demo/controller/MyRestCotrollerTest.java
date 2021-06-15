package com.ngohoangbach.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ngohoangbach.demo.entity.Customer;
import com.ngohoangbach.demo.entity.Order;
import com.ngohoangbach.demo.entity.Product;
import com.ngohoangbach.demo.entity.Status;
import com.ngohoangbach.demo.service.ProductServiceImpl;


@WebMvcTest(controllers = MyRestController.class)
public class MyRestCotrollerTest {
	
	@MockBean
	private ProductServiceImpl productService;
	
	@Autowired
	private MockMvc mockMvc;
	
	final int PRODUCT_ID = 1;
	Product tempProduct;
	
	Order tempOrder1;
	@BeforeEach
	void setup() {
		tempProduct = new Product(PRODUCT_ID, "Test Product", 20, "image URL test");
		tempOrder1 = new Order(1, new ArrayList<Product>(), new Customer(), new Status(),
											LocalDate.now(), LocalDate.now());
	}
	
	@Test
	@DisplayName("Should List All the Products")
	public void shouldReturnListOfProduct() throws Exception{
		
		Product tempProduct1 = new Product(1, "Americano", 20, "imageUrlTest");
		Product tempProduct2 = new Product(2, "Black Coffee", 25, "imageUrlTest");
		
		Mockito.when(productService.findAllProducts()).thenReturn(Arrays.asList(tempProduct1, tempProduct2));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
			.andExpect(MockMvcResultMatchers.status().is(200))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Americano")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].price", Matchers.is(20.0)))
			.andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
			.andExpect(MockMvcResultMatchers.jsonPath("$[1].title", Matchers.is("Black Coffee")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[1].price", Matchers.is(25.0)));
	}
	
	
	@Test
	public void shouldReturnSingleProduct() throws Exception{
		
		Mockito.when(productService.findProductById(PRODUCT_ID)).thenReturn(tempProduct);
		
		mockMvc.perform(get("/api/products/{productId}", PRODUCT_ID))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", Matchers.is(PRODUCT_ID)))
			.andExpect(jsonPath("$.title", Matchers.is("Test Product")))
			.andExpect(jsonPath("$.price", Matchers.is(20.0)))
			.andExpect(jsonPath("$.imageUrl", Matchers.is("image URL test")));
		
	}
	
	@Test
	public void shouldReturnAllOrders() throws Exception{
		
		Order tempOrder1 = new Order(1, new ArrayList<Product>(), new Customer(), new Status(), LocalDate.now(), LocalDate.now());
		Order tempOrder2 = new Order(2, new ArrayList<Product>(), new Customer(), new Status(), LocalDate.now(), LocalDate.now());
		Mockito.when(productService.findAllOrders()).thenReturn(Arrays.asList( tempOrder1, tempOrder2 ));
		
		mockMvc.perform(get("/api/orders"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.size()", Matchers.is(2)))
			.andExpect(jsonPath("$[0].id", Matchers.is(1)))
			.andExpect(jsonPath("$[1].id", Matchers.is(2)));
			
	}
	
	@Test
	public void shouldReturnAOrder() throws Exception{
		
		
		Mockito.when(productService.findOrderById(1)).thenReturn(tempOrder1);
		
		mockMvc.perform(get("/api/orders/{orderId}",1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", Matchers.is(1)));
		
	}
	
	
	@Test
	public void shouldCreateAnewOrder() throws Exception{
		Mockito.when(productService.findProductById(PRODUCT_ID)).thenReturn(tempProduct);
		Mockito.when(productService.findOrderById(1)).thenReturn(tempOrder1);
		
		mockMvc.perform(put("/api/orders/{orderId}/products/{productId}", 1, PRODUCT_ID))
			.andExpect(status().isOk());
		
	}
	
}
