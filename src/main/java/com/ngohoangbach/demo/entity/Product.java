package com.ngohoangbach.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Product entity
 * @author Bach
 *
 */
@Entity
@Table(name="product")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="price")
	private float price;
	
	@Column(name="image_url")
	private String imageUrl;
	
	@ManyToMany(mappedBy = "products")
	@JsonIgnore
	private List<Order> orders;
	
	public Product() {}
	
	public Product(String title, float price, String imageUrl) {
		this.title = title;
		this.price = price;
		this.imageUrl = imageUrl;
	}
	
	public Product(int id, String title, float price, String imageUrl) {
		this.id = id;
		this.title = title;
		this.price = price;
		this.imageUrl = imageUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", title=" + title + ", price=" + price + ", imageUrl=" + imageUrl + "]";
	}
	
}	
