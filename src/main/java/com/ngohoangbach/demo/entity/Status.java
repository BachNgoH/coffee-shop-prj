package com.ngohoangbach.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Status of a Order: Verifying, Delivering, Delivered
 * @author Bach
 *
 */
@Entity
@Table(name="status")
public class Status {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy = "status", cascade = {CascadeType.DETACH, CascadeType.MERGE})
	private List<Order> orders;
	
	public Status() {}

	public Status(int id, List<Order> orders) {
		this.id = id;
		this.orders = orders;
	}
	
	public Status(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Status [id=" + id + ", orders=" + orders + "]";
	}
	
}
