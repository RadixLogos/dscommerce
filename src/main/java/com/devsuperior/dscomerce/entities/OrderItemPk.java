package com.devsuperior.dscomerce.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class OrderItemPk {

	@ManyToOne
	@JoinColumn(name = "order_id")
	Order order;
	@ManyToOne
	@JoinColumn(name = "product_id")
	Product product;
	
	public OrderItemPk() {
		
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	

}
