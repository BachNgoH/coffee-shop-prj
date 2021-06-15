package com.ngohoangbach.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ngohoangbach.demo.dao.CustomerRepo;
import com.ngohoangbach.demo.dao.OrderRepo;
import com.ngohoangbach.demo.dao.ProductRepo;
import com.ngohoangbach.demo.dao.StatisticRepo;
import com.ngohoangbach.demo.dao.StatusRepo;
import com.ngohoangbach.demo.entity.Customer;
import com.ngohoangbach.demo.entity.Order;
import com.ngohoangbach.demo.entity.Product;
import com.ngohoangbach.demo.entity.Statistic;
import com.ngohoangbach.demo.entity.Status;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	
	@Mock
	private ProductRepo productRepo;
	
	@Mock
	private OrderRepo orderRepo; 
	
	@Mock
	private StatusRepo statusRepo;
	
	@Mock
	private CustomerRepo customerRepo;
	
	@Mock
	private StatisticRepo statisticRepo;
	
	@Captor
	private ArgumentCaptor<Product> productArgumentCapture;
	
	@Captor
	private ArgumentCaptor<Order> orderArgumentCapture;
	
	@Captor
	private ArgumentCaptor<Customer> customerArgumentCapture;
	
	ProductServiceImpl productService;
	
	@Captor
	private ArgumentCaptor<Statistic> statisticArgumentCapture;
	
	@BeforeEach
	public void setUp() {
		productService = new ProductServiceImpl(productRepo,orderRepo,statusRepo,customerRepo,statisticRepo);
	}
	
	@Test
	@DisplayName("Should Find Product By Id")
	void shouldFindProductById() {
		
		Product theProduct = new Product(1, "Americano", 20, "/images/product-1.jpg");
		Mockito.when(productRepo.findById(1)).thenReturn(Optional.of(theProduct));
		
		Product actualProductResponse = productService.findProductById(1);
		
		Assertions.assertThat(actualProductResponse.getId()).isEqualTo(theProduct.getId());
		Assertions.assertThat(actualProductResponse.getTitle()).isEqualTo(theProduct.getTitle());
		Assertions.assertThat(actualProductResponse.getPrice()).isEqualTo(theProduct.getPrice());
		Assertions.assertThat(actualProductResponse.getImageUrl()).isEqualTo(theProduct.getImageUrl());
		
	}
	
	@Test
	@DisplayName("Should save product")
	void shouldSaveProduct() {
		
		Product testProduct = new Product(123, "Test Product", 25, "/images/tests=ing");
		
		productService.saveProduct(testProduct);
		Mockito.verify(productRepo, Mockito.times(1)).save(productArgumentCapture.capture());
		
		Assertions.assertThat( productArgumentCapture.getValue().getId()).isEqualTo(123);
		Assertions.assertThat( productArgumentCapture.getValue().getTitle()).isEqualTo("Test Product");
	}
	
	@Test
	@DisplayName("Should delete product")
	void shouldDeleteProduct() {
		
		int productID = 1;
		productService.deleteProductById(productID);
		Mockito.verify(productRepo, Mockito.times(1)).deleteById(productID);
		
	}
	
	@Test
	void shouldReturnAllOrder() {
		Order tempOrder1 = new Order(1, new ArrayList<Product>(), new Customer(), 
									new Status(), LocalDate.now(), LocalDate.now());
		Order tempOrder2 = new Order(2, new ArrayList<Product>(), new Customer(), 
									new Status(), LocalDate.now(), LocalDate.now());
		List<Order> tempList = new ArrayList<>();
		tempList.add(tempOrder1);
		tempList.add(tempOrder2);
		Mockito.when(orderRepo.findAll()).thenReturn(tempList);
		
		List<Order> actualList = productService.findAllOrders();
		assertThat(actualList.get(0).getId()).isEqualTo(actualList.get(0).getId());
		assertThat(actualList.get(1).getId()).isEqualTo(actualList.get(1).getId());

	}
	
	@Test
	void shouldReturnAnyOrder() {
		Order tempOrder1 = new Order(1, new ArrayList<Product>(), new Customer(), 
				new Status(), LocalDate.now(), LocalDate.now());
		Mockito.when(orderRepo.findById(1)).thenReturn(Optional.of(tempOrder1));
		Order actualOrder = productService.findOrderById(1);
		
		assertThat(actualOrder.getId()).isEqualTo(tempOrder1.getId());
	}
	
	@Test
	void shouldSaveOrder() {
		Order tempOrder1 = new Order(1, new ArrayList<Product>(), new Customer(), 
				new Status(), LocalDate.now(), LocalDate.now());
		productService.saveOrder(tempOrder1);
		Mockito.verify(orderRepo, Mockito.times(1)).save(orderArgumentCapture.capture());
		
		assertThat(orderArgumentCapture.getValue().getId()).isEqualTo(tempOrder1.getId());
		
	}
	
	@Test
	void shouldDeleteAnyOrder() {
		int orderId = 1;
		productService.deleteOrderById(orderId);
		
		Mockito.verify(orderRepo, Mockito.times(1)).deleteById(orderId);
		
	}
	
	@Test
	void shouldReturnTotalCost() {
		Product product1 = new Product(1, "test1", 25, "url");
		Product product2 = new Product(2, "test1", 20, "url");
		Product product3 = new Product(3, "test1", 30, "url");
		Order tempOrder1 = new Order(1, new ArrayList<Product>(), new Customer(), 
				new Status(), LocalDate.now(), LocalDate.now());
		tempOrder1.setProducts(Arrays.asList(product1, product2, product3));
		
		int expectedTotal = 75;
		int actualTotal = productService.totalCost(tempOrder1);
		
		assertThat(actualTotal).isEqualTo(expectedTotal);
		
	}
	
	@Test
	void shouldReturnStatus() {
		Status verifying = new Status(1, "Verifying");
		Status delivering = new Status(2, "Delivering");
		Status delivered = new Status(3, "Delivered");
		
		Mockito.when(statusRepo.findById(1)).thenReturn(Optional.of( verifying ));
		Mockito.when(statusRepo.findById(2)).thenReturn(Optional.of( delivering));
		Mockito.when(statusRepo.findById(3)).thenReturn(Optional.of( delivered ));
		
		Status actualVerifying = productService.verifying();
		Status actualDelivering = productService.delivering();
		Status actualDelivered = productService.delivered();
		
		assertThat(actualVerifying).isEqualTo(verifying);
		assertThat(actualDelivering).isEqualTo(delivering);
		assertThat(actualDelivered).isEqualTo(delivered);
		
	}
	
	@Test
	void shouldSaveCustomer() {
		Customer theCustomer = new Customer(1, new Order(), "Test User", "123ABC VT VN");
		productService.saveCustomer(theCustomer);
		Mockito.verify(customerRepo, Mockito.times(1)).save(customerArgumentCapture.capture());
		assertThat(customerArgumentCapture.getValue().getId()).isEqualTo(1);
		assertThat(customerArgumentCapture.getValue().getName()).isEqualTo(theCustomer.getName());
		assertThat(customerArgumentCapture.getValue().getAddress()).isEqualTo(theCustomer.getAddress());
	}
	
	@Test
	void shouldUpdateStatistic() {
		Statistic expectedStatistic = new Statistic(LocalDate.now(), 2 , 40);
		Order tempOrder1 = new Order(1, new ArrayList<Product>(), new Customer(), 
				new Status(), LocalDate.now(), LocalDate.now());
		Product product1 = new Product(1, "test1", 25, "url");
		Product product2 = new Product(2, "test1", 20, "url");
		Product product3 = new Product(3, "test1", 30, "url");
		tempOrder1.setProducts(Arrays.asList(product1, product2, product3));
		
		Mockito.when(statisticRepo.findById(LocalDate.now())).thenReturn(Optional.of(expectedStatistic));
		productService.updateStatistic(tempOrder1);
		Mockito.verify(statisticRepo, Mockito.times(1)).save(statisticArgumentCapture.capture());
		
		assertThat(statisticArgumentCapture.getValue().getDate()).isEqualTo(expectedStatistic.getDate());
		assertThat(statisticArgumentCapture.getValue().getNumOrders()).isEqualTo(3);
		assertThat(statisticArgumentCapture.getValue().getProfit()).isEqualTo(115);
		
	}
}
