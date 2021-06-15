package com.ngohoangbach.demo.helper;

import com.ngohoangbach.demo.entity.Product;
/**
 * Helper class to handle the total amount of a product in a order
 * @author Bach
 *
 */
public class ProductAmount {
	
	private Product product;
	
	private int amount;

	public Product getProduct() {
		return product;
	}

	public ProductAmount() {
	}

	public ProductAmount(Product product, int amount) {
		this.product = product;
		this.amount = amount;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}
