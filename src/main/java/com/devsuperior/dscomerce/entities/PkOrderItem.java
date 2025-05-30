package com.devsuperior.dscomerce.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class PkOrderItem {

	private Long orderId;
	private Long productId;

	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}


}
