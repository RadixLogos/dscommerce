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
	private PkOrderItem id;
	private Integer quantiy;
	private Double price;

	public OrderItem() {
	}

	public OrderItem(Long orderId, Long ProductId, Integer quantiy, Double price) {
		id.setOrderId(orderId);
		id.setProductId(ProductId);
		this.quantiy = quantiy;
		this.price = price;
	}

	public PkOrderItem getId() {
		return id;
	}

	public void setId(PkOrderItem id) {
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
