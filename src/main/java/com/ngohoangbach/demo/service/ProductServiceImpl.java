package com.ngohoangbach.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.ngohoangbach.demo.helper.ProductAmount;


/**
 * implementation for product service
 * @author Bach
 *
 */

@Service
public class ProductServiceImpl implements ProductService{

	ProductRepo productRepo;
	OrderRepo orderRepo;
	StatusRepo statusRepo;
	CustomerRepo customerRepo;
	StatisticRepo statisticRepo;
	
	@Autowired
	public ProductServiceImpl(ProductRepo productRepo, OrderRepo orderRepo, StatusRepo statusRepo,
			CustomerRepo customerRepo, StatisticRepo statisticRepo) {
		this.productRepo = productRepo;
		this.orderRepo = orderRepo;
		this.statusRepo = statusRepo;
		this.customerRepo = customerRepo;
		this.statisticRepo = statisticRepo;
	}

	@Override
	public List<Product> findAllProducts() {
		return productRepo.findAll();
	}

	@Override
	public Product findProductById(int theId) {
		// TODO Auto-generated method stub
		Optional<Product> tempProduct = productRepo.findById(theId);
		Product theProduct = null;
		if(tempProduct.isPresent()) {
			theProduct = tempProduct.get();
		}
		
		return theProduct; 
	}

	@Override
	public void saveProduct(Product theProduct) {
		productRepo.save(theProduct);
	}

	@Override
	public void deleteProductById(int theId) {
		productRepo.deleteById(theId);
	}
	
	/**
	 * Get all the product of a order
	 */
	@Override
	public List<Product> findAllProductsOfAOrder(int orderId) {
		Order tempOrder = findOrderById(orderId);
		
		return tempOrder.getProducts();
	}

//	---------------------------------- ORDERS -----------------------------------------------//
	
	@Override
	public List<Order> findAllOrders() {
		return orderRepo.findAll();
	}
	
	@Override
	public void saveOrder(Order theOrder) {
		// TODO Auto-generated method stub
		orderRepo.save(theOrder);
	}

	@Override
	public void deleteOrderById(int theId) {
		orderRepo.deleteById(theId);
		
	}

	/**
	 * Add a product to the order
	 * 
	 */
	@Override
	public Order addProductToOrder(int orderId, int productId) {
		Optional<Order> tempOrder = orderRepo.findById(orderId);
		Optional<Product> tempProduct = productRepo.findById(productId);
		
		Order theOrder = null;
		Product theProduct = null;
		if(tempOrder.isPresent()) {
			theOrder = tempOrder.get();
		}else {
			throw new RuntimeException("Cannot find order at Id - "+ orderId);
		}
		if(tempProduct.isPresent()) {
			theProduct = tempProduct.get();
		}else {
			throw new RuntimeException("Cannot find product at Id - "+ productId);
		}
		
		theOrder.addProduct(theProduct);
		orderRepo.save(theOrder);
		return theOrder;
	}

	@Override
	public Order findOrderById(int theId) {
		Optional<Order> tempOrder = orderRepo.findById(theId);
		Order theOrder = null;
		if(tempOrder.isPresent()) {
			theOrder = tempOrder.get();
		}
		return theOrder;
	}

	/**
	 * clear all product of a order
	 */
	@Override
	public Order clearProduct(int orderId) {
		
		Optional<Order> getOrder = orderRepo.findById(orderId);
		Order theOrder = null;
		if(getOrder.isPresent()) {
			theOrder = getOrder.get();
		}else {
			throw new RuntimeException("Cannot find order at Id - "+ orderId);
		}
		theOrder.setProducts(new ArrayList<>());
		
		return theOrder;
	}

	@Override
	public void clearAllOrders() {
		orderRepo.deleteAll();
	}

	/**
	 * get the total cost of a order
	 */
	@Override
	public int totalCost(Order theOrder) {
		List<Product> theProducts = theOrder.getProducts();
		int total = 0;
		for(Product prod: theProducts) {
			total += prod.getPrice();
		}
		return total;
	}
	
	/**
	 * find the amount of product in a order
	 */
	@Override
	public List<ProductAmount> findTheAmountOfEachProdct(Order theOrder) {
		List<Product> theProducts = theOrder.getProducts();
		List<ProductAmount> productAmount = new ArrayList<>();
		
		Map<Product, Integer> map = new HashMap<>();
		
		for(Product product: theProducts) {
			if(map.containsKey(product)) {
				map.put(product, map.get(product) + 1);
			}else {
				map.put(product, 1);
			}
		}
		
		
		for(Map.Entry<Product, Integer> set: map.entrySet()) {
			productAmount.add(new ProductAmount(set.getKey(), set.getValue()));
		}
		
		return productAmount;
	}

	
//	------------------------------------- STATUS -----------------------------------------------//
	
	/**
	 * Three Status: Verifying, Delivering, Delivered
	 */
	@Override
	public Status verifying() {
		int verifyingId = 1;
		Optional<Status> tempStatus = statusRepo.findById(verifyingId);
		if(tempStatus.isPresent()) {
			return tempStatus.get();
		}else {
			generateStatus();
			return statusRepo.findById(verifyingId).get();
		}
	}
	@Override
	public Status delivering() {
		int deliveringId = 2;
		Optional<Status> tempStatus = statusRepo.findById(deliveringId);
		if(tempStatus.isPresent()) {
			return tempStatus.get();
		}else {
			generateStatus();
			return statusRepo.findById(deliveringId).get();
		}
	}
	@Override
	public Status delivered() {
		int deliveredId = 3;
		Optional<Status> tempStatus = statusRepo.findById(deliveredId);
		if(tempStatus.isPresent()) {
			return tempStatus.get();
		}else {
			generateStatus();
			return statusRepo.findById(deliveredId).get();
		}
	}
	
	@Override
	public List<Status> allStatus() {
		// TODO Auto-generated method stub
		return statusRepo.findAll();
	}
	
	public void generateStatus() {
		Status verifying = new Status(1, "Verifying");
		Status delivering = new Status(2, "Delivering");
		Status delivered = new Status(3, "Delivered");
		
		statusRepo.save(verifying);
		statusRepo.save(delivering);
		statusRepo.save(delivered);
	}
	
//	-------------------------------------------------- CUSTOMER -----------------------------------------//
	@Override
	public void saveCustomer(Customer customer) {
		customerRepo.save(customer);
	}
	
	@Override
	public List<Customer> findAllCustomer(){
		return customerRepo.findAll();
	}

//  --------------------------------------------------- Statistic -----------------------------------------//
	@Override
	public List<Statistic> findAllStatistic() {
		// TODO Auto-generated method stub
		return statisticRepo.findAll();
	}

	
	/**
	 * update the statistic of a day
	 */
	@Override
	public Statistic updateStatistic(Order theOrder) {
		
		int profit = totalCost(theOrder);
		Optional<Statistic> todayStatistic = statisticRepo.findById(LocalDate.now());
		Statistic statistic = new Statistic();
		if(todayStatistic.isPresent()) {
			statistic = todayStatistic.get();
			statistic.setNumOrders(statistic.getNumOrders() + 1);
			statistic.setProfit(statistic.getProfit() + profit);
		}else {
			statistic.setDate(LocalDate.now());
			statistic.setNumOrders(1);
			statistic.setProfit(profit);
		}
		statisticRepo.save(statistic);
		return statistic;
	}

	@Override
	public boolean demoTest(String comment) {
		if(comment.contains("shit")) {
			throw new RuntimeException("Comment contains unacceptable language");
		}
		return false;
	}

	
}
