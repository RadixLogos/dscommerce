package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Product;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

public record ProductDTO(
		Long id,
		@NotBlank(message = "Recurso obrigatório") 
		@Size(min = 3, max = 80, message = "Nome precisa ter entre 3 a 80 caracteres")
		String name,
		@NotBlank(message = "Recurso obrigatório")
		@Size(min = 10, message = "Descrição deve ter no mínimo 10 caracteres")
		String description,
		@NotNull(message = "Recurso obrigatório")
		@Positive(message = "O preço deve ser um número positivo")
		Double price,
		String imgUrl,
		@NotEmpty(message = "O produto deve ter ao menos uma categoria")
		List<CategoryDTO> categories
) {
	public static ProductDTO fromProduct(Product p) {
		List<CategoryDTO> categories = new ArrayList<>();
		p.getCategories().forEach(c -> {
			categories.add(CategoryDTO.fromCategory(c));
		});
		return new ProductDTO(
				p.getId(),
				p.getName(),
				p.getDescription(),
				p.getPrice(),
				p.getImgUrl(),
				categories
				);
	}

}
