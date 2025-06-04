package com.devsuperior.dscomerce.dto;

import com.devsuperior.dscomerce.entities.Product;

public record ProductDTO(Long id,String name,String description,Double price,String imgUrl) {
	public static ProductDTO fromProduct(Product p) {
		return new ProductDTO(
				p.getId(),
				p.getName(),
				p.getDescription(),
				p.getPrice(),
				p.getImgUrl());
	}
}
