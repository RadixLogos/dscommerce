package com.devsuperior.dscomerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscomerce.dto.ProductDTO;
import com.devsuperior.dscomerce.entities.Product;
import com.devsuperior.dscomerce.repositories.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	
	@Transactional(readOnly = true)
	public ProductDTO findProductById(Long id) {
		return ProductDTO.fromProduct(productRepository.findById(id).get()); 
	}

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllProducts(Pageable pageble){
		return productRepository.findAll(pageble).map(x -> ProductDTO.fromProduct(x));
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
		Product entity = productRepository.getReferenceById(id);
		copyDtoToEntity(dto,entity);
		entity = productRepository.save(entity);
		return ProductDTO.fromProduct(entity);
	}
	
	@Transactional
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
	
	private void copyDtoToEntity( ProductDTO dto, Product entity) {
		entity.setName(dto.name());
		entity.setDescription(dto.description());
		entity.setPrice(dto.price());
		entity.setImgUrl(dto.imgUrl());
	}
	
}
