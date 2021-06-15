package com.ngohoangbach.demo.service;

import java.util.List;

import com.ngohoangbach.demo.entity.Customer;
import com.ngohoangbach.demo.entity.Order;
import com.ngohoangbach.demo.entity.Product;
import com.ngohoangbach.demo.entity.Statistic;
import com.ngohoangbach.demo.entity.Status;
import com.ngohoangbach.demo.helper.ProductAmount;
/**
 * Central interface for all services
 * it was named ProductService, however it handles request for all entity
 * not just product
 * @author Bach
 *
 */

public interface ProductService {
	
	// for products
	public List<Product> findAllProducts();
	public Product findProductById(int theId);
	public void saveProduct(Product theProduct);
	public void deleteProductById(int theId);
	public List<Product> findAllProductsOfAOrder(int orderId);
	
	
	// for orders
	public List<Order> findAllOrders();
	public Order findOrderById(int theId);
	public Order addProductToOrder(int orderId, int productId);
	public void saveOrder(Order theOrder);
	public void deleteOrderById(int theId);
	public Order clearProduct(int orderId);
	public void clearAllOrders();
	
	
	// for order's status
	public Status verifying();
	public Status delivering();
	public Status delivered();
	public List<Status> allStatus();
	public int totalCost(Order theOrder);
	public List<ProductAmount> findTheAmountOfEachProdct(Order theOrders);
	
	// for customer
	public void saveCustomer(Customer customer);
	public List<Customer> findAllCustomer();
	
	// for statistic
	public List<Statistic> findAllStatistic();
	public Statistic updateStatistic(Order theOrder);
	
	public boolean demoTest(String comment);
	
}
