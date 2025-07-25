package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductMinDTO;
import com.devsuperior.dscommerce.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Transactional(readOnly = true)
	public Page<ProductMinDTO> findAllProducts(Pageable pageable, String name){
		Page<Product> products = productRepository.findByName(name,pageable);
		return products.map(ProductMinDTO::fromProduct);
	}

	@Transactional(readOnly = true)
	public ProductDTO findProductById(Long id) {
		var dto =ProductDTO.fromProduct(productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado!"))); 
		return dto;
	}

	@Transactional
	public ProductDTO insertProduct(ProductDTO dto) {
		var entity = new Product();
		copyDtoToEntity(dto,entity);
		entity = productRepository.save(entity);
		return ProductDTO.fromProduct(entity);
		
	}
	
	@Transactional
	public ProductDTO updateProduct(Long id, ProductDTO dto) {
		try {
		Product entity = productRepository.getReferenceById(id);
		copyDtoToEntity(dto,entity);
		entity = productRepository.save(entity);
		return ProductDTO.fromProduct(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void deleteProduct(Long id) {
		if(!productRepository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
			productRepository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		};
	}
	
	private void copyDtoToEntity( ProductDTO dto, Product entity) {
		entity.setName(dto.name());
		entity.setDescription(dto.description());
		entity.setPrice(dto.price());
		entity.setImgUrl(dto.imgUrl());
		entity.getCategories().clear();
		dto.categories().forEach(catDto ->{
			var category = new Category();
			category.setId(catDto.id());
			entity.getCategories().add(category);
		});
	}

}
