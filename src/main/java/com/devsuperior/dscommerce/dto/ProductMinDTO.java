package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductMinDTO(
		Long id,
		String name,
		Double price,
		String imgUrl) {
	public static ProductMinDTO fromProduct(Product p) {
		return new ProductMinDTO(
				p.getId(),
				p.getName(),
				p.getPrice(),
				p.getImgUrl());
	}
}
