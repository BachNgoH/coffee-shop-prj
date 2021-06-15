package com.ngohoangbach.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ngohoangbach.demo.entity.Order;

/**
 * Customer Entity 
 * @author Bach
 *
 */
@Entity
@Table(name="customer")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@OneToOne(mappedBy = "theCustomer")
	private Order order;
	
	@Column(name="cname")
	private String name;
	
	@Column(name="address")
	public String address;
	
	@Column(name="number")
	private String phoneNumber;
	
	public Customer() {}

	public Customer(int id, Order order, String name, String address) {
		this.id = id;
		this.order = order;
		this.name = name;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", order=" + order + ", name=" + name + ", address=" + address + "]";
	}
	
	
}
