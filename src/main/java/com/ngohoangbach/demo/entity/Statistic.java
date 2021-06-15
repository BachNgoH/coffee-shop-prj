package com.ngohoangbach.demo.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Statistic entity of the shop
 * show the shop total orders and profit in a day
 * @author Bach
 *
 */
@Entity
@Table(name="statistic")
public class Statistic {
	
	@Id
	@Column(name="date")
	private LocalDate date;
	
	@Column(name="total_order")
	private int numOrders;
	
	@Column(name="profit")
	private int profit;

	public Statistic(LocalDate date, int numOrders, int profit) {
		this.date = date;
		this.numOrders = numOrders;
		this.profit = profit;
	}
	
	public Statistic() {}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getNumOrders() {
		return numOrders;
	}

	public void setNumOrders(int numOrders) {
		this.numOrders = numOrders;
	}

	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	};
	
	
	
}
