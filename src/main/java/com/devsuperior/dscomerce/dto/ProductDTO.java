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

	public Long id() {
		return id;
	}

	public String name() {
		return name;
	}

	public String description() {
		return description;
	}

	public Double price() {
		return price;
	}

	public String imgUrl() {
		return imgUrl;
	}
	
	
}
