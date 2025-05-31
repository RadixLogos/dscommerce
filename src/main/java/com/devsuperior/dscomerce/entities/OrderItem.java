package com.devsuperior.dscomerce.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_order_item")
public class OrderItem implements Serializable{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OrderItemPk id = new OrderItemPk();
	private Integer quantiy;
	private Double price;

	public OrderItem() {
	}

	public OrderItem(Order order, Product product, Integer quantiy, Double price) {
		id.setOrder(order);
		id.setProduct(product);
		this.quantiy = quantiy;
		this.price = price;
	}

	public OrderItemPk getId() {
		return id;
	}

	public void setId(OrderItemPk id) {
		this.id = id;
	}

	public Integer getQuantiy() {
		return quantiy;
	}

	public void setQuantiy(Integer quantiy) {
		this.quantiy = quantiy;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Order getOrder() {
		return id.getOrder();
	}
	
	public void setOrder(Order order) {
		id.setOrder(order);
	}
	
	public Product getProduct() {
		return id.getProduct();
	}
	
	public void setProduct(Product product) {
		id.setProduct(product);
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		OrderItem other = (OrderItem) obj;
		return Objects.equals(id, other.id);
	}


}
