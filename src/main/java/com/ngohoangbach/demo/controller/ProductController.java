package com.ngohoangbach.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ngohoangbach.demo.entity.Customer;
import com.ngohoangbach.demo.entity.Order;
import com.ngohoangbach.demo.entity.Product;
import com.ngohoangbach.demo.helper.ProductAmount;
import com.ngohoangbach.demo.service.ProductService;

/**
 * Controller that handle request from the user
 * @author Bach
 *
 */

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping("/list")
	public String loadData(Model theModel) {

		List<Product> theProducts = productService.findAllProducts();
		theModel.addAttribute("products",theProducts);

		return "main-page";
	}

	@GetMapping("/showVerifyingForm/{orderId}")
	public String verifyPurchase(Model theModel, @PathVariable int orderId) {

		Order theOrder = productService.findOrderById(orderId);
		List<Product> theProducts = theOrder.getProducts();

		
		List<ProductAmount> productAmount = productService.findTheAmountOfEachProdct(theOrder);
		int total = productService.totalCost(theOrder);

		Customer theCustomer = new Customer();
		theModel.addAttribute("order", theOrder);
		theModel.addAttribute("theProducts", productAmount);
		theModel.addAttribute("customer", theCustomer);
		theModel.addAttribute("total", total);

		return "verify-purchase";

	}

	@PostMapping("/processForm")
	public String processPurchase(@RequestParam("orderId") int orderId, @ModelAttribute("customer") Customer theCustomer) {
		Order theOrder = productService.findOrderById(orderId);

		theOrder.setTheCustomer(theCustomer);
		theOrder.setStatus(productService.delivering());
		theOrder.setAddDate(LocalDate.now());

		productService.saveOrder(theOrder);
		return "redirect:/products/list";
	}

}
