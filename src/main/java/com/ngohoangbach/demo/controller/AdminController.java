package com.ngohoangbach.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ngohoangbach.demo.entity.Customer;
import com.ngohoangbach.demo.entity.Order;
import com.ngohoangbach.demo.entity.Product;
import com.ngohoangbach.demo.entity.Statistic;
import com.ngohoangbach.demo.entity.Status;
import com.ngohoangbach.demo.helper.ProductAmount;
import com.ngohoangbach.demo.service.ProductService;

/**
 * 
 * Admin controller for managing the coffee shop
 * 
 * @author Bach
 * 
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	ProductService productService;
	
	
	/**
	 * return the list of orders that the shop receive 
	 * 
	 * @param theModel to display the list
	 * @return the main admin page
	 */
	@GetMapping("/orderList")
	public String showOrderList(Model theModel) {
		List<Order> orderList = productService.findAllOrders();
		List<Customer> customerList = new ArrayList<>();
		List<Status> allStatus = productService.allStatus();
		
		// get customers list
		for(Order order: orderList) {
			if(order.getTheCustomer() != null)
				customerList.add(order.getTheCustomer());
			
		}
		theModel.addAttribute("orders", orderList);
		theModel.addAttribute("customer", customerList);
		theModel.addAttribute(allStatus);
		return "admin/admin-page";
	}
	
	/**
	 * view the details of a order, including : id, customer, list of products, order status
	 * 
	 * @param theModel
	 * @param orderId
	 * @return
	 */
	@GetMapping("/viewDetails")
	public String viewOrderDetails(Model theModel, @RequestParam("orderId") int orderId) {
		Order theOrder = productService.findOrderById(orderId);
		
		Customer theCustomer = theOrder.getTheCustomer();
		Status status = theOrder.getStatus();
		
		List<ProductAmount> productAmount = productService.findTheAmountOfEachProdct(theOrder);
		int total = productService.totalCost(theOrder);
		
		theModel.addAttribute("order",theOrder);
		theModel.addAttribute("theProducts",productAmount);
		theModel.addAttribute("status",status);
		theModel.addAttribute("customer",theCustomer);
		theModel.addAttribute("total", total);
		
		return "admin/order-details";
	}
	
	
	/**
	 * deliver the order: change the status of the order from delivering -> delivered
	 * 
	 * @param orderId
	 * @return
	 */
	@GetMapping("/deliverOrder")
	public String deliverOrder(@RequestParam("orderId") int orderId) {
		Order theOrder = productService.findOrderById(orderId);
		
		theOrder.setStatus(productService.delivered());
		theOrder.setDeliverDate(LocalDate.now());
		productService.saveOrder(theOrder);
		productService.updateStatistic(theOrder);
		return "redirect:/admin/orderList";
	}
	
	/*
	 * clear all the orders 
	 */
	@GetMapping("/clearAllOrders")
	public String deleteAllOrder() {
		productService.clearAllOrders();
		return "redirect:/products/list";
	}
	
	
	/*
	 * Show the statistics of the shop
	 * including : total number of orders in a day, total profit in a day
	 */
	@GetMapping("/showStatistics")
	public String showStatistic(Model theModel) {
		
		List<Statistic> listOfStatistic = productService.findAllStatistic();
		theModel.addAttribute("statistics",listOfStatistic);
		
		return "admin/statistics-page";
	}
}
