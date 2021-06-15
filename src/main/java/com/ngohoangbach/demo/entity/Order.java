package com.ngohoangbach.demo.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Order entity
 * @author Bach
 *
 */
@Entity
@Table(name="orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, 
			CascadeType.REFRESH,CascadeType.REFRESH})
	@JoinTable(
				name="products_orders",
				joinColumns = @JoinColumn(name="order_id"),
				inverseJoinColumns = @JoinColumn(name="product_id")
			)
	private List<Product> products = new ArrayList<Product>();

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="customer_id")
	private Customer theCustomer;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="status_id")
	private Status status;

	@Column(name="add_date")
	private LocalDate addDate;
	
	@Column(name="deliver_date")
	private LocalDate deliverDate;
	
	public Order(int id, List<Product> products) {
		this.id = id;
		this.products = products;
	}
	
	public Order(int id, List<Product> products, Customer theCustomer, Status status, LocalDate addDate,
			LocalDate deliverDate) {
		this.id = id;
		this.products = products;
		this.theCustomer = theCustomer;
		this.status = status;
		this.addDate = addDate;
		this.deliverDate = deliverDate;
	}



	public Order() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public void addProduct(Product product) {
		this.products.add(product);
	}

	public Customer getTheCustomer() {
		return theCustomer;
	}

	public void setTheCustomer(Customer theCustomer) {
		this.theCustomer = theCustomer;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDate getAddDate() {
		return addDate;
	}

	public void setAddDate(LocalDate addDate) {
		this.addDate = addDate;
	}

	public LocalDate getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(LocalDate deliverDate) {
		this.deliverDate = deliverDate;
	}

	
	
	
}
